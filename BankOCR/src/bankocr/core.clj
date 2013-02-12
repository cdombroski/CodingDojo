(ns bankocr.core
  (:use [clojure.java.io :only [reader]]))

(set! *warn-on-reflection* true)

(def digits
  {[" _ "
    "| |"
    "|_|"]
   0
   ["   "
    "  |"
    "  |"]
   1
   [" _ "
    " _|"
    "|_ "]
   2
   [" _ "
    " _|"
    " _|"]
   3
   ["   "
    "|_|"
    "  |"]
   4
   [" _ "
    "|_ "
    " _|"]
   5
   [" _ "
    "|_ "
    "|_|"]
   6
   [" _ "
    "  |"
    "  |"]
   7
   [" _ "
    "|_|"
    "|_|"]
   8
   [" _ "
    "|_|"
    " _|"]
   9})

(def ambiguous-digits
  {
    ["   "
     "   "
     "  |"]
    [1]
    ["   "
     "  |"
     "   "]
    [1]
    ["   "
     "  |"
     "  |"]
    [1 7]
    [" _ "
     "  |"
     "  |"]
    [7]
    ["   "
     "| |"
     "  |"]
    [1]
    ["   "
     "  |"
     "| |"]
    [1]
    ["   "
     " _|"
     "  |"]
    [1 4]
    ["   "
     "  |"
     " _|"]
    [1]
    ["   "
     "|_|"
     "  |"]
    [4]
    [" _ "
     "| |"
     "  |"]
    [7]
    [" _ "
     " _|"
     "  |"]
    [3 7]
    [" _ "
     "  |"
     "| |"]
    [7]
    [" _ "
     "  |"
     " _|"]
    [3 7]
    ["   "
     "|_ "
     " _|"]
    [5]
    [" _ "
     " _ "
     " _|"]
    [3 5]
    [" _ "
     "|  "
     " _|"]
    [5]
    [" _ "
     "|_ "
     " _ "]
    [5]
    [" _ "
     "|_ "
     "  |"]
    [5]
    ["   "
     " _|"
     " _|"]
    [3]
    [" _ "
     " _|"
     " _ "]
    [2 3]
    ["   "
     " _|"
     "|_ "]
    [2]
    [" _ "
     " _ "
     "|_ "]
    [2]
    [" _ "
     "  |"
     "|_ "]
    [2]
    [" _ "
     " _|"
     "|  "]
    [2]
    [" _ "
     "|_|"
     "  |"]
    [4 9]
    ["   "
     "|_|"
     " _|"]
    [4 9]
    ["   "
     "|_|"
     "| |"]
    [4]
    [" _ "
     " _|"
     "|_ "]
    [2]
    [" _ "
     "|_ "
     " _|"]
    [5 6 9]
    [" _ "
     " _|"
     " _|"]
    [3 9]
    ["   "
     "|_ "
     "|_|"]
    [6]
    [" _ "
     " _ "
     "|_|"]
    [6]
    [" _ "
     "|  "
     "|_|"]
    [0 6]
    [" _ "
     "|_ "
     "| |"]
    [6]
    [" _ "
     "|_ "
     "|_ "]
    [6]
    [" _ "
     "| |"
     " _|"]
    [0 9]
    [" _ "
     "|_|"
     " _ "]
    [9]
    ["   "
     "| |"
     "|_|"]
    [0]
    [" _ "
     "  |"
     "|_|"]
    [0]
    [" _ "
     "| |"
     "| |"]
    [0]
    [" _ "
     "| |"
     "|_ "]
    [0]
    [" _ "
     "| |"
     "|_|"]
    [0 8]
    [" _ "
     "|_|"
     " _|"]
    [9 8]
    [" _ "
     "|_ "
     "|_|"]
    [6 8]
    [" _ "
     "|_|"
     "| |"]
    [8]
    ["   "
     "|_|"
     "|_|"]
    [8]
    [" _ "
     " _|"
     "|_|"]
    [8]
    [" _ "
     "|_|"
     "|_ "]
    [8]
    [" _ "
     "|_|"
     "|_|"]
    [0 6 8 9]})

(defn lines2digits [^String line1 ^String line2 ^String line3]
  (for [x (range 9)]
    [(.substring line1 (* x 3) (+ (* x 3) 3))
     (.substring line2 (* x 3) (+ (* x 3) 3))
     (.substring line3 (* x 3) (+ (* x 3) 3))]))

(defn digit2number [digit]
  {:number (get digits digit) :digit digit})

(defn checksum [account]
  (zero? (mod (reduce + (map-indexed (fn [idx num]
               (* (inc idx) num)) (reverse account)))
           11)))

(defn check-accounts [accounts]
  (map (fn [account]
         (if (some #(nil? (:number %)) account)
           (if (> (count (filter #(nil? (:number %)) account)) 1)
             (concat (replace {nil \?} (map :number account)) [:ill])
             (first (for [x0 (if (:number (nth account 0)) [(:number (nth account 0))] (get ambiguous-digits (:digit (nth account 0))))
                   x1 (if (:number (nth account 1)) [(:number (nth account 1))] (get ambiguous-digits (:digit (nth account 1))))
                   x2 (if (:number (nth account 2)) [(:number (nth account 2))] (get ambiguous-digits (:digit (nth account 2))))
                   x3 (if (:number (nth account 3)) [(:number (nth account 3))] (get ambiguous-digits (:digit (nth account 3))))
                   x4 (if (:number (nth account 4)) [(:number (nth account 4))] (get ambiguous-digits (:digit (nth account 4))))
                   x5 (if (:number (nth account 5)) [(:number (nth account 5))] (get ambiguous-digits (:digit (nth account 5))))
                   x6 (if (:number (nth account 6)) [(:number (nth account 6))] (get ambiguous-digits (:digit (nth account 6))))
                   x7 (if (:number (nth account 7)) [(:number (nth account 7))] (get ambiguous-digits (:digit (nth account 7))))
                   x8 (if (:number (nth account 8)) [(:number (nth account 8))] (get ambiguous-digits (:digit (nth account 8))))
                   :when (checksum [x0 x1 x2 x3 x4 x5 x6 x7 x8])]
               [x0 x1 x2 x3 x4 x5 x6 x7 x8])))))
    accounts))

(defn read-account-numbers [file]
  (let [^java.io.BufferedReader rdr (reader file)
        read-next-account
        (fn read-next-account []
          (if-let [line1 (.readLine rdr)]
            (let [line2 (.readLine rdr)
                  line3 (.readLine rdr)
                  _ (.readLine rdr)]
              (cons (map digit2number (lines2digits line1 line2 line3))
                (lazy-seq (read-next-account))))
            (.close rdr)))]
    (read-next-account)))