(ns roman-calculator.core-test
  (:require [clojure.test :refer :all]
            [roman-calculator.core :refer :all]))

(deftest additions []
  (are [x y z] (= x (add y z))
    "II" "I" "I"
    "XXII" "XX" "II"
    "LXXIV" "XX" "LIV"
    "M" "D" "D"))
