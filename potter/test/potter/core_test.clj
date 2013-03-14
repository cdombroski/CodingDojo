(ns potter.core-test
  (:require [clojure.test :refer :all ]
            [potter.core :refer :all ]))

(deftest zero-books-cost-nothing []
  (is (zero? (price))))

(deftest one-book-costs-eight []
  (are [a] (= 8 (price a))
    0 1 2 3 4))

(run-tests)
