(ns bankocr.core
  (:use [clojure.java.io :only [reader]]))

(def digits
  {0
   [" _ "
    "| |"
    "|_|"]
   1
   ["   "
    "  |"
    "  |"]
   2
   [" _ "
    " _|"
    "|_ "]
   3
   [" _ "
    " _|"
    " _|"]
   4
   ["   "
    "|_|"
    "  |"]
   5
   [" _ "
    "|_ "
    " _|"]
   6
   [" _ "
    "|_ "
    "|_|"]
   7
   [" _ "
    "  |"
    "  |"]
   8
   [" _ "
    "|_|"
    "|_|"]
   9
   [" _ "
    "|_|"
    " _|"]})

(defn lines2digits [line1 line2 line3]
  (for [x (range 9)]
    [(.substring line1 (* x 3) (+ (* x 3) 3))
     (.substring line2 (* x 3) (+ (* x 3) 3))
     (.substring line3 (* x 3) (+ (* x 3) 3))]))

(defn digit2number [digit]
  (some #(when (= (val %) digit) (key %)) digits))

(defn read-account-numbers [file]
  (let [rdr (reader file)
        read-next-account
        (fn read-next-account []
          (if-let [line1 (.readLine rdr)]
            (let [line2 (.readLine rdr)
                  line3 (.readLine rdr)
                  _ (.readLine rdr)]
              (cons (map digit2number (lines2digits line1 line2 line3)) (lazy-seq (read-next-account))))
            (.close rdr)))]
    (read-next-account)))



