(ns fizzbuzz.core-test
  (:use clojure.test
        fizzbuzz.core))

(deftest sequence-is-100-items []
  (let [test (fizzbuzz)]
    (is (= (count test) 100))))

(deftest sequence-starts-at-1 []
  (let [test (fizzbuzz)]
    (is (= (first test) 1))))

(run-tests)
