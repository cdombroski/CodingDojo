(ns fizzbuzz.core)

(defn fizzbuzz []
  (for [n (range 1 101)]
    (cond
      (zero? (mod n 15)) "fizzbuzz"
      (zero? (mod n 3)) "fizz"
      (zero? (mod n 5)) "buzz"
      :else n)))