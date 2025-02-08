(ns commands
  (:require [heretic.core :as heretic]))

(defmulti command :action)

(defmethod command :login
  [{:keys [email password]}]
  (if (and (= email "hi@mikejackson.co.uk")
           (= password "password"))
    (heretic/redirect "/dashboard")
    (heretic/redirect "/")))

(defmethod command :default
  [params]
  (println "default command" params)
  "Unknown command")
