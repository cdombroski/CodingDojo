(ns bankocr.core
  (:use [clojure.java.io :only [reader]]))

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

(defn lines2digits [line1 line2 line3]
  (for [x (range 9)]
    [(.substring line1 (* x 3) (+ (* x 3) 3))
     (.substring line2 (* x 3) (+ (* x 3) 3))
     (.substring line3 (* x 3) (+ (* x 3) 3))]))

(defn digit2number [digit]
  (get digits digit))

(defn checksum [account]
  (zero? (mod (reduce + (map-indexed (fn [idx num]
               (* (inc idx) num)) (reverse account)))
           11)))

(defn check-accounts [accounts]
  (map (fn [account]
         (let [parsed-account (replace {nil \?} account)]
           (concat parsed-account
             (if (some #{\?} parsed-account)
               [:ill]
               (when-not (checksum parsed-account)
                 [:err])))))
    accounts))

(defn read-account-numbers [file]
  (let [rdr (reader file)
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