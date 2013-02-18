(ns fizzbuzz.core)

(defn fizzbuzz []
  (for [n (range 1 101)]
    (cond
      (zero? (mod n 15)) "fizzbuzz"
      :else n)))