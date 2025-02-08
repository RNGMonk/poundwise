(ns server
  (:require [ring.adapter.jetty :as jetty]
            [queries :refer [query]]
            [commands :refer [command]]
            [heretic.core :as heretic]
            [db]))

(defonce server (atom nil))

(def routes
  [["/" :show-home]
   ["/dashboard" :show-dashboard]
   ["/dashboard/accounts" :show-accounts]
   ["/dashboard/accounts/:account-id" :show-account]
   ["/dashboard/transactions" :show-transactions]
   ["/dashboard/transactions/:transaction-id" :show-transaction]])

(def app 
  (heretic/make-app
   {:query query
    :command command
    :routes routes
    :dependencies {:conn db/conn}}))

(defn start-server []
  (reset! server (jetty/run-jetty app {:port 8000 :join? false})))

(defn stop-server [] 
  (swap! server #(when % ( .stop %))))

(defn -main [& [port]]
  (jetty/run-jetty app {:port 8000}))


(comment
  (-main)

  (do (stop-server)
      (start-server))
  )

