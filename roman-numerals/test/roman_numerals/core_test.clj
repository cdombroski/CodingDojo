(ns roman-numerals.core-test
  (:use clojure.test
        roman-numerals.core))

(deftest single-digits []
  (are [x y] (= y (number-to-roman x))
    1 "I"
    2 "II"
    3 "III"
    4 "IV"
    5 "V"
    6 "VI"
    7 "VII"
    8 "VIII"
    9 "IX"
    0 ""))

(deftest tens-digit []
  (are [x y] (= y (number-to-roman x))
    10 "X"
    20 "XX"
    30 "XXX"
    40 "XL"
    50 "L"
    60 "LX"
    70 "LXX"
    80 "LXXX"
    90 "XC"))

(deftest hundreds-digit []
  (are [x y] (= y (number-to-roman x))
    100 "C"
    200 "CC"
    300 "CCC"
    400 "CD"
    500 "D"
    600 "DC"
    700 "DCC"
    800 "DCCC"
    900 "CM"))

(deftest thousands-digit []
  (are [x y] (= y (number-to-roman x))
    1000 "M"
    2000 "MM"
    3000 "MMM"))

(deftest acceptance []
  (= "MCMXCIX" (number-to-roman 1999)))
