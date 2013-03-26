(ns roman-numerals.core)

(defn- ones-to-roman [num]
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

(defn- tens-to-roman [num]
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

(defn- hundreds-to-roman [num]
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

(defn- thousands-to-roman [num]
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

(defn- roman-to-thousands [rom]
  (case rom
    "M" 1000
    "MM" 2000
    "MMM" 3000
    0))

(defn- roman-to-hundreds [rom]
  (case rom
    "C" 100
    "CC" 200
    "CCC" 300
    "CD" 400
    "D" 500
    "DC" 600
    "DCC" 700
    "DCCC" 800
    "CM" 900
    0))

(defn- roman-to-tens [rom]
  (case rom
    "X" 10
    "XX" 20
    "XXX" 30
    "XL" 40
    "L" 50
    "LX" 60
    "LXX" 70
    "LXXX" 80
    "XC" 90
    0))

(defn- roman-to-ones [rom]
  (case rom
    "I" 1
    "II" 2
    "III" 3
    "IV" 4
    "V" 5
    "VI" 6
    "VII" 7
    "VIII" 8
    "IX" 9
    0))

(defn roman-to-numeral [rom]
  (let [roman-matcher (re-matcher #"(M{0,3})(C[DM]|DC{0,3}|C{0,3})(X[CL]|LX{0,3}|X{0,3})(I[VX]|VI{0,3}|I{0,3})" rom)
        matches (re-find roman-matcher)]
    (+
      (roman-to-thousands (nth matches 1))
      (roman-to-hundreds (nth matches 2))
      (roman-to-tens (nth matches 3))
      (roman-to-ones (nth matches 4)))))