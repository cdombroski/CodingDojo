(ns bankocr.core-test
  (:use bankocr.core clojure.test))

(def case1-accounts (str
                      " _  _  _  _  _  _  _  _  _ \n"
                      "| || || || || || || || || |\n"
                      "|_||_||_||_||_||_||_||_||_|\n\n"
                      "                           \n"
                      "  |  |  |  |  |  |  |  |  |\n"
                      "  |  |  |  |  |  |  |  |  |\n\n"
                      " _  _  _  _  _  _  _  _  _ \n"
                      " _| _| _| _| _| _| _| _| _|\n"
                      "|_ |_ |_ |_ |_ |_ |_ |_ |_ \n\n"
                      " _  _  _  _  _  _  _  _  _ \n"
                      " _| _| _| _| _| _| _| _| _|\n"
                      " _| _| _| _| _| _| _| _| _|\n\n"
                      "                           \n"
                      "|_||_||_||_||_||_||_||_||_|\n"
                      "  |  |  |  |  |  |  |  |  |\n\n"
                      " _  _  _  _  _  _  _  _  _ \n"
                      "|_ |_ |_ |_ |_ |_ |_ |_ |_ \n"
                      " _| _| _| _| _| _| _| _| _|\n\n"
                      " _  _  _  _  _  _  _  _  _ \n"
                      "|_ |_ |_ |_ |_ |_ |_ |_ |_ \n"
                      "|_||_||_||_||_||_||_||_||_|\n\n"
                      " _  _  _  _  _  _  _  _  _ \n"
                      "  |  |  |  |  |  |  |  |  |\n"
                      "  |  |  |  |  |  |  |  |  |\n\n"
                      " _  _  _  _  _  _  _  _  _ \n"
                      "|_||_||_||_||_||_||_||_||_|\n"
                      "|_||_||_||_||_||_||_||_||_|\n\n"
                      " _  _  _  _  _  _  _  _  _ \n"
                      "|_||_||_||_||_||_||_||_||_|\n"
                      " _| _| _| _| _| _| _| _| _|\n\n"
                      "    _  _     _  _  _  _  _ \n"
                      "  | _| _||_||_ |_   ||_||_|\n"
                      "  ||_  _|  | _||_|  ||_| _|\n\n"))

(deftest case1 []
  (is (= [[0 0 0 0 0 0 0 0 0]
          [1 1 1 1 1 1 1 1 1]
          [2 2 2 2 2 2 2 2 2]
          [3 3 3 3 3 3 3 3 3]
          [4 4 4 4 4 4 4 4 4]
          [5 5 5 5 5 5 5 5 5]
          [6 6 6 6 6 6 6 6 6]
          [7 7 7 7 7 7 7 7 7]
          [8 8 8 8 8 8 8 8 8]
          [9 9 9 9 9 9 9 9 9]
          [1 2 3 4 5 6 7 8 9]]
        (map #(map :number %) (read-account-numbers (java.io.StringReader. case1-accounts))))))

(def case2-accounts (str
                      "    _  _  _  _  _  _  _  _ \n"
                      "|_||_   ||_ | ||_|| || || |\n"
                      "  | _|  | _||_||_||_||_||_|\n\n"
                      " _  _     _  _        _  _ \n"
                      "|_ |_ |_| _|  |  ||_||_||_ \n"
                      "|_||_|  | _|  |  |  | _| _|\n\n"))

(deftest case2 []
  (is (= [true false]
        (map checksum (map #(map :number %) (read-account-numbers (java.io.StringReader. case2-accounts)))))))

;(def case3-accounts (str
;                      case2-accounts
;                      " _  _  _  _  _  _  _  _    \n"
;                      "| || || || || || || ||_   |\n"
;                      "|_||_||_||_||_||_||_| _|  |\n\n"
;                      "    _  _  _  _  _  _     _ \n"
;                      "|_||_|| || ||_   |  |  | _ \n"
;                      "  | _||_||_||_|  |  |  | _|\n\n"
;                      "    _  _     _  _  _  _  _ \n"
;                      "  | _| _||_| _ |_   ||_||_|\n"
;                      "  ||_  _|  | _||_|  ||_| _ \n\n"))
;
;(deftest case3 []
;  (is (= [[4 5 7 5 0 8 0 0 0]
;          [6 6 4 3 7 1 4 9 5 :err]
;          [0 0 0 0 0 0 0 5 1]
;          [4 9 0 0 6 7 7 1 \? :ill]
;          [1 2 3 4 \? 6 7 8 \? :ill]]
;        (check-accounts (read-account-numbers (java.io.StringReader. case3-accounts))))))

(deftest case4 []
  (are [value ocr] (= value (check-accounts (read-account-numbers (java.io.StringReader. ocr))))
    [[7 1 1 1 1 1 1 1 1]] (str
                            "                           \n"
                            "  |  |  |  |  |  |  |  |  |\n"
                            "  |  |  |  |  |  |  |  |  |\n\n")
    [[7 7 7 7 7 7 1 7 7]] (str
                            " _  _  _  _  _  _  _  _  _ \n"
                            "  |  |  |  |  |  |  |  |  |\n"
                            "  |  |  |  |  |  |  |  |  |\n\n")
    [[2 0 0 8 0 0 0 0 0]] (str
                            " _  _  _  _  _  _  _  _  _ \n"
                            " _|| || || || || || || || |\n"
                            "|_ |_||_||_||_||_||_||_||_|\n\n")
    [[3 3 3 3 9 3 3 3 3]] (str
                            " _  _  _  _  _  _  _  _  _ \n"
                            " _| _| _| _| _| _| _| _| _|\n"
                            " _| _| _| _| _| _| _| _| _|\n\n")
    [[8 8 8 8 8 8 8 8 8
      :amb #{[8 8 8 8 8 6 8 8 8]
        [8 8 8 8 8 8 8 8 0]
        [8 8 8 8 8 8 9 8 8]}]] (str
                                 " _  _  _  _  _  _  _  _  _ \n"
                                 "|_||_||_||_||_||_||_||_||_|\n"
                                 "|_||_||_||_||_||_||_||_||_|\n\n")
    [[5 5 5 5 5 5 5 5 5
      :amb #{[5 5 5 6 5 5 5 5 5]
        [5 5 9 5 5 5 5 5 5]}]] (str
                                 " _  _  _  _  _  _  _  _  _ \n"
                                 "|_ |_ |_ |_ |_ |_ |_ |_ |_ \n"
                                 " _| _| _| _| _| _| _| _| _|\n\n")
    [[6 6 6 6 6 6 6 6 6
      :amb #{[6 6 6 5 6 6 6 6 6]
        [6 8 6 6 6 6 6 6 6]}]] (str
                                 " _  _  _  _  _  _  _  _  _ \n"
                                 "|_ |_ |_ |_ |_ |_ |_ |_ |_ \n"
                                 "|_||_||_||_||_||_||_||_||_|\n\n")
    [[9 9 9 9 9 9 9 9 9
      :amb #{[8 9 9 9 9 9 9 9 9]
        [9 9 3 9 9 9 9 9 9]
        [9 9 9 9 5 9 9 9 9]}]] (str
                                 " _  _  _  _  _  _  _  _  _ \n"
                                 "|_||_||_||_||_||_||_||_||_|\n"
                                 " _| _| _| _| _| _| _| _| _|\n\n")
    [[4 9 0 0 6 7 7 1 5
      :amb #{[4 9 0 0 6 7 1 1 5]
        [4 9 0 0 6 7 7 1 9]
        [4 9 0 8 6 7 7 1 5]}]] (str
                                 "    _  _  _  _  _  _     _ \n"
                                 "|_||_|| || ||_   |  |  ||_ \n"
                                 "  | _||_||_||_|  |  |  | _|\n\n")
    [[1 2 3 4 5 6 7 8 9]] (str
                            "   _  _     _  _  _  _  _ \n"
                            "_| _| _||_||_ |_   ||_||_|\n"
                            " ||_  _|  | _||_|  ||_| _|\n\n")
    [[0 0 0 0 0 0 0 5 1]] (str
                            " _     _  _  _  _  _  _    \n"
                            "| || || || || || || ||_   |\n"
                            "|_||_||_||_||_||_||_| _|  |\n\n")
    [[4 9 0 8 6 7 7 1 5]] (str
                            "    _  _  _  _  _  _     _ \n"
                            "|_||_|| ||_||_   |  |  | _ \n"
                            "  | _||_||_||_|  |  |  | _|\n\n")
    [[\? \? 0 0 0 0 0 0 0 :ill ]] (str
                                    "       _  _  _  _  _  _  _ \n"
                                    "| || || || || || || || || |\n"
                                    "|_||_||_||_||_||_||_||_||_|\n\n")))
(run-tests)
