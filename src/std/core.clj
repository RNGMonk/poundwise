(ns std.core
  (:refer-clojure :exclude [map mapv filter filterv remove find]))

(defprotocol IMutable
  (! [this v]))

(extend-type
 clojure.lang.Atom
  IMutable
  (! [this v]
    (if (fn? v)
      (swap! this v)
      (reset! this v))))

(extend-type
 clojure.lang.Volatile
  IMutable
  (! [this v]
    (if (fn? v)
      (vswap! this v)
      (vreset! this v))))

(def len count)
(def len-1 (comp dec len))

(def ++ inc)
(def -- dec)

;; mutable stuff
(def mut! clojure.core/volatile!)
(def mut? clojure.core/volatile?)
(defn !++ [v]
  (let [_v @v]
    (vswap! v ++)
    _v))
(defn !-- [v]
  (let [_v @v]
    (vswap! v --)
    _v))
(def ++! #(vswap! % ++))
(def --! #(vswap! % --))

(defmacro && [& args] `(clojure.core/and ~@args))
(defmacro || [& args] `(clojure.core/or ~@args))

(defn map [xs f]
  (clojure.core/map f xs))

(defn mapv [xs f]
  (clojure.core/mapv f xs))

(defn filter [xs f]
  (clojure.core/filter f xs))

(defn filterv [xs f]
  (clojure.core/filterv f xs))

(defn remove [xs f]
  (clojure.core/remove f xs))

(defn removev [xs f]
  (into [] (clojure.core/remove f xs)))

(defn find [xs f]
  (first (filter xs f)))

(defn uuid 
  ([]
   (java.util.UUID/randomUUID))
  ([s]
   (java.util.UUID/fromString s)))

(comment
  
  (find [1 3 3] even?)

  (let [v (atom 5)
        _ (! v 0)]
    @v)

  (let [v (mut! 1)]
    (! v 6))

  (-> (range 10)
      (map #(* % %))
      (filter odd?))

  (get "hello" (len-1 "hello"))

  (&& true true false)
  (|| false false)

  (len [1 2 3])
  (len-1 [1 2 3])

  (map [1 2 3] #(* % %))
  (mapv [1 2 3] #(* % %))

  (filter [1 2 3] odd?)
  (filterv [1 2 3] odd?)

  (remove [1 2 3] odd?)
  (removev [1 2 3] odd?)

  (++ 1)
  (-- 1)




  )