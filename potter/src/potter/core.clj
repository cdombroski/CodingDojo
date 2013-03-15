(ns potter.core)

(defn price
  ([] 0)
  ([x]
    (if (coll? x)
      (* 8 (count x))
      8)))

(defn- group-books [books]
  (let [book-frequencies (into {0 0, 1 0, 2 0, 3 0, 4 0} (frequencies books))
        init-groups (loop [book-freqs book-frequencies
                           result {}
                           group 4]
          (let [group-num (if (> (count (remove zero? (vals book-freqs))) group)
                            (map min (remove zero? (vals book-freqs)))
                            0)
                new-freqs (for [[k v] book-freqs]
                            {k (max 0 (- v group-num))})]
            (if (pos? group)
              (recur new-freqs (conj result {group group-num} (dec group)))
              (conj result {group group-num}))))
        pairs-3-5 (min (get init-groups 4) (get init-groups 2))] ;paired groups of 3 and 5 are better as pairs of 4
      (for [[k v] init-groups]
        (case k
          (3 5) {k (- v pairs-3-5)}
          4 {k (+ v pairs-3-5)}
          :else {k v}))))