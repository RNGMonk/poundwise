(ns queries
  (:require [heretic.core :refer [query]]
            [db]
            [components :refer [dashboard-nav]]))

(defmethod query :show-home 
  [params] 
  (println params)
  ^{:title "Poundwise | Home"}
  [:div {:id "app"}
   [:nav
    [:ul
     [:li [:a {:href "/dashboard"} "Dashboard"]]]]
   [:h1 "Welcome to Poundwise"]
   [:form {:method "post" :action "/command"} 
    [:input {:type "hidden" :name "action" :value "login"}]
    [:label {:for "email"} "Email"]
    [:input {:id "email" :type "email" :name "email"}]
    [:label {:for "password"} "Password"]
    [:input {:id "password ":type "password" :name "password"}]
    [:button {:type "submit"} "Login"]]])

(defmethod query :show-dashboard
  [params]
  (println params)
  ^{:title "Poundwise | Dashboard"}
  [:div {:id "app"}
   (dashboard-nav)
   [:h1 "Overview"]])

(defmethod query :show-accounts
  [{:keys [conn] :as params}]
  (println params) 
  ^{:title "Poundwise | Accounts"}
  [:div {:id "app"}
   (dashboard-nav)
   [:h1 "Accounts"]
   [:table
    [:thead
     [:tr
      [:th "Name"]]]
    [:tbody
     (for [{:keys [:account/id :account/name]} (db/accounts conn)]
       [:tr
        [:td [:a {:href (str "/dashboard/transactions?account-id=" id)} name]]])]]])

(defmethod query :show-account
  [{:keys [account-id] :as params}]
  (println params) 
  ^{:title "Poundwise | Account"}
  [:h1 (str "Show Account "  account-id)])

(defmethod query :show-transactions
  [{:keys [conn] :as params}]
  (println params) 
  ^{:title "Poundwise | Transactions"}
  [:div {:id "app"}
   (dashboard-nav)
   [:h1 "Transactions"]
   [:table
    [:thead
     [:tr
      [:th "Date"]
      [:th "Description"]
      [:th "Amount"]]]
    [:tbody
     (for [{:keys [:transaction/date :transaction/description :transaction/amount]} (db/transactions conn)]
       [:tr
        [:td date]
        [:td description]
        [:td amount]])
     ]]
   ])

(defmethod query :show-transaction
  [{:keys [transaction-id] :as params}]
  (println params) 
  ^{:title "Poundwise | Transactions"}
  [:h1 (str "Show Transaction "  transaction-id)])

(defmethod query :default
  [params]
  (println params) 
  "Unknown query")

(comment 
  
  (meta ^{:title "Foo"} [123] )
  (query {:action :show-home})
  
  (query {:transaction-id 1234, :action :show-transaction}) 
  )