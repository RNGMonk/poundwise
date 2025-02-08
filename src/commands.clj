(ns commands
  (:require [heretic.core :refer [command redirect]]))

(defmethod command :login
  [{:keys [email password]}]
  (if (and (= email "hi@mikejackson.co.uk")
           (= password "password"))
    (redirect "/dashboard")
    (redirect "/")))

(defmethod command :default
  [params]
  (println "default command" params)
  "Unknown command")
