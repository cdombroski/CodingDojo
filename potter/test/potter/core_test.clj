(ns potter.core-test
  (:require [clojure.test :refer :all ]
            [potter.core :refer :all ]))

(deftest zero-books-cost-nothing []
  (is (zero? (price))))

(run-tests)
