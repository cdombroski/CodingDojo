(ns potter.core)

(declare ^:private group-books)

(defn price
  ([] 0)
  ([books]
    (reduce + (for [[k v] (apply merge (group-books books))]
                (case k
                  4 (* 8 5 0.75 v)
                  3 (* 8 4 0.8 v)
                  2 (* 8 3 0.9 v)
                  1 (* 8 2 0.95 v)
                  0 (* 8 v))))))

(defn- group-books [books]
  (let [book-frequencies (into {0 0, 1 0, 2 0, 3 0, 4 0} (frequencies books))
        init-groups (loop [book-freqs book-frequencies
                           result {}
                           group 4]
          (let [group-num (if (> (count (remove zero? (vals book-freqs))) group)
                            (reduce min (remove zero? (vals book-freqs)))
                            0)
                new-freqs (for [[k v] book-freqs]
                            {k (max 0 (- v group-num))})]
            (if (pos? group)
              (recur (apply merge new-freqs) (conj result {group group-num}) (dec group))
              (conj result {group group-num}))))
        pairs-3-5 (min (get init-groups 4) (get init-groups 2))] ;paired groups of 3 and 5 are better as pairs of 4
      (for [[k v] init-groups]
        (case k
          (2 4) {k (- v pairs-3-5)}
          3 {k (+ v (* 2 pairs-3-5))}
          {k v}))))