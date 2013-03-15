(ns potter.core-test
  (:require [clojure.test :refer :all ]
            [potter.core :refer :all ]))

(deftest zero-books-cost-nothing []
  (is (zero? (price))))

(deftest one-book-costs-eight []
  (are [a] (== 8 (price a))
    [0] [1] [2] [3] [4]))

(deftest no-discount-for-same-book-more-than-once []
  (are [x y] (== x (price y))
    16 [0 0]
    24 [1 1 1]))

(deftest simple-discounts []
  (are [x y] (== x (price y))
    (* 8 2 0.95) [0 1]
    (* 8 3 0.9) [0 2 4]
    (* 8 4 0.8) [0 1 2 4]
    (* 8 5 0.75) [0 1 2 3 4]))

(deftest multiple-discounts[]
  (are [x y] (== x (price y))
    (+ 8 (* 8 2 0.95)) [0 0 1]
    (* 2 8 2 0.95) [0 0 1 1]
    (+ (* 8 4 0.8) (* 8 2 0.95)) [0 0 1 2 2 3]
    (+ 8 (* 8 5 0.75)) [0 1 1 2 3 4]))

(deftest edge-cases []
  (are [x y] (== x (price y))
    (* 2 8 4 0.8) [0 0 1 1 2 2 3 4]
    (+ (* 3 8 5 0.75) (* 2 8 4 0.8))
    [0 0 0 0 0
     1 1 1 1 1
     2 2 2 2
     3 3 3 3 3
     4 4 4 4]))

(run-tests)
