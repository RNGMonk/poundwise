(ns components)

(defn dashboard-nav []
  [:nav
   [:ul
    [:li [:a {:href "/dashboard"} "Dashboard"]]
    [:li [:a {:href "/dashboard/accounts"} "Accounts"]]
    [:li [:a {:href "/dashboard/transactions"} "Transactions"]]]])