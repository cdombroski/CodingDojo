(ns potter.core-test
  (:require [clojure.test :refer :all ]
            [potter.core :refer :all ]))

(deftest zero-books-cost-nothing []
  (is (zero? (price))))

(deftest one-book-costs-eight []
  (are [a] (= 8 (price a))
    0 1 2 3 4))

(deftest no-discount-for-same-book-more-than-once []
  (are [x y] (= x (price y))
    16 [0 0]
    24 [1 1 1]))

(run-tests)
