(ns potter.core)

(defn price
  ([] 0)
  ([x]
    (if (coll? x)
      (* 8 (count x))
      8)))
