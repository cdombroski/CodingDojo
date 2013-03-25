(ns roman-numerals.core)

(defn ones-to-roman [num]
  (case num
    1 "I"
    2 "II"
    3 "III"
    4 "IV"
    5 "V"
    6 "VI"
    7 "VII"
    8 "VIII"
    9 "IX"
    ""))

(defn tens-to-roman [num]
  (case num
    1 "X"
    2 "XX"
    3 "XXX"
    4 "XL"
    5 "L"
    6 "LX"
    7 "LXX"
    8 "LXXX"
    9 "XC"
    ""))

(defn hundreds-to-roman [num]
  (case num
    1 "C"
    2 "CC"
    3 "CCC"
    4 "CD"
    5 "D"
    6 "DC"
    7 "DCC"
    8 "DCCC"
    9 "CM"
    ""))

(defn thousands-to-roman [num]
  (case num
    1 "M"
    2 "MM"
    3 "MMM"
    ""))

(defn number-to-roman [num]
  (str
    (thousands-to-roman (quot num 1000))
    (hundreds-to-roman (quot (rem num 1000) 100))
    (tens-to-roman (quot (rem num 100) 10))
    (ones-to-roman (rem num 10))))