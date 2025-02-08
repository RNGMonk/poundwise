(ns std.html
  (:require  [hiccup2.core :as h]))

(defn page [body]
  (let [title (get (meta body) :title "Default Title")]
    (str
     (h/html
      (h/raw "<!DOCTYPE html>")
      [:html
       [:head
        [:title title]
        [:script {:src "https://unpkg.com/htmx.org@2.0.4"}]]
       [:body body]]))))