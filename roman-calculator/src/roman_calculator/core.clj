(ns roman-calculator.core
  (:require [roman-numerals.core
             :refer [roman-to-numeral number-to-roman]]))

(defn add [& args]
  (number-to-roman
    (apply + (for [num args]
         (roman-to-numeral num)))))