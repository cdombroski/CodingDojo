(ns bankocr.core
  (:use [clojure.java.io :only [reader]]
        [bankocr.numbers]))

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
             (concat (replace {nil \?} (map :number account)) [:ill ])
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
                      [x0 x1 x2 x3 x4 x5 x6 x7 x8])))
           (if (checksum (map :number account))
             (map :number account)
             (let [init-number (vec (map :number account))
                   try-accounts (apply concat (map-indexed (fn [idx digit] (map #(assoc-in init-number [idx] %) (get ambiguous-digits (:digit digit)))) account))
                   ambiguous (for [try-account try-accounts :when (checksum try-account)] try-account)]
               (case (count ambiguous)
                 0 (conj init-number :err)
                 1 (first ambiguous)
                 (conj init-number :amb (set ambiguous)))))))
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