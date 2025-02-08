(ns heretic.core
  (:require 
   [std.core :as c]
   [ring.middleware.params :refer [wrap-params]]
   [ring.middleware.keyword-params :refer [wrap-keyword-params]]
   [jsonista.core :as json]
   [std.html :as html]))

(defn- response [status body]
  {:status status
   :body body})

;; TODO add all the response types
(def ok (partial response 200))
(def created (partial response 201))
(def unauthorized (partial response 401))
(def forbidden (partial response 403))
(def not-found (partial response 404))
(def server-error (partial response 500))
(defn redirect [url]
  {:status  302
   :body    ""
   :headers {"Location" url}})

;; TODO - experiment with rendering by convention
(defprotocol IRenderable
  (render [this]))

(extend-type clojure.lang.APersistentMap
  IRenderable
  (render [this]
    (update this :body json/write-value-as-string)))

(extend-type clojure.lang.APersistentVector
  IRenderable
  (render [this]
    {:status 200
     :body (html/page this)}))

(extend-type java.lang.Object
  IRenderable
  (render [this]
    {:status 200
     :body this}))

(defn match-route [route path]
  (let [[i j result] [(c/mut! 0) (c/mut! 0) (c/mut! {})]
        route-len (c/len route)
        path-len (c/len path)]
    (while (c/&& (< @i route-len) (< @j path-len))
      (let [route-ch (get route @i)
            path-ch (get path @j)]
        (cond
          ;; advance if chars match
          (= route-ch path-ch)
          (do (c/!++ i) (c/!++ j))

          ;; we've got a path name
          (= route-ch \:)
          (let [path-name (StringBuilder.)
                path-value (StringBuilder.)]
            (c/!++ i)

            ;; consume path name
            (while (c/&& (< @i route-len) (not= (get route @i) "/"))
              (.append path-name (str (get route (c/!++ i)))))

            ;; consume path value
            (while (c/&& (< @j path-len) (not= (get path @j) "/"))
              (.append path-value (str (get path (c/!++ j)))))

            ;; add them to the result
            (vswap! result assoc
                    (keyword (.toString path-name))
                    (.toString path-value)))
          ;; break
          :else (vreset! i route-len))))

          ;; if there was a full match return the result
    (when (c/&& (= @i route-len) (= @j path-len))
      @result)))

(defn- make-handler [query command]
  (fn [{:keys [uri params]}]
    (case uri
      "/query" (query params)
      "/command" (command params)
      {:status 200
       :body "Todo file requests and error pages"})))


(defn- wrap-query-remapper [handler routes]
  (fn [{:keys [uri request-method] :as req}]
    (if-let [match (and (= :get request-method)
                        (-> routes
                            (c/map (fn [[route action]]
                                     (when-let [route-params (match-route route uri)]
                                       [action route-params])))
                            (c/find identity)))]
      (let [[action route-params] match]
        (handler (-> req
                     (assoc-in [:uri] "/query")
                     (assoc-in [:route-params] route-params)
                     (update-in [:params] merge route-params {:action action}))))
      (handler req))))

(defn wrap-keyword-action [handler]
  (fn [req]
    (handler (update-in req [:params :action] keyword))))

(defn- wrap-dependencies [handler dependencies]
  (fn [req]
    (handler (update-in req [:params] merge dependencies))))

(defn- wrap-render [handler]
  (fn [req]
    (render (handler req))))

(defn make-app [{:keys [query command routes dependencies]}]
  (-> (make-handler query command)
      (wrap-render)
      (wrap-query-remapper routes)
      (wrap-dependencies dependencies)
      (wrap-keyword-action)
      (wrap-keyword-params)
      (wrap-params)))

(comment
  
(match-route "/people/:person-id" "/people/123")
(match-route "/people/:person-id" "/peoples/123")
(match-route "/dashboard/accounts/:accou" "/dashboard/accounts/1234")

  )