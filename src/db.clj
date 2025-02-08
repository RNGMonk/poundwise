(ns db
  (:require [std.core :as c]))

(def conn 
  (let [monzo-id (c/uuid)
        starling-id (c/uuid)]
    (atom {:accounts
           [{:account/name "Monzo"
             :account/id monzo-id}
            {:account/name "Starling"
             :account/id starling-id}]
           
           :transactions
           [{:transaction/id (c/uuid)
             :transaction/date #inst "2024-12-21T18:30:00.000-00:00"
             :transaction/account-id monzo-id
             :transaction/description "Burgers"
             :transaction/amount -15.00}
            {:transaction/id (c/uuid)
             :transaction/date #inst "2024-12-21T18:30:00.000-00:00"
             :transaction/account-id monzo-id
             :transaction/description "Mortgage"
             :transaction/amount  -1750.00}
            {:transaction/id (c/uuid)
             :transaction/account-id monzo-id
             :transaction/date #inst "2024-12-21T18:30:00.000-00:00"
             :transaction/description "Thames Water"
             :transaction/amount  -40.00}
            {:transaction/id (c/uuid)
             :transaction/date #inst "2024-12-21T18:30:00.000-00:00"
             :transaction/account-id monzo-id
             :transaction/description "Netflix"
             :transaction/amount -15.00}
            {:transaction/id (c/uuid)
             :transaction/date #inst "2024-12-21T18:30:00.000-00:00"
             :transaction/account-id monzo-id
             :transaction/description "Salary"
             :transaction/amount  4200.00}]})))

(defn accounts [conn]
  (:accounts @conn))

(defn transactions [conn]
  (:transactions @conn))

(comment
  @conn
  )