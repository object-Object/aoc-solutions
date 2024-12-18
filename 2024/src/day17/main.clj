(ns day17.main
  (:require [clojure.math :as math]
            [clojure.string :as str]
            [clojure.test :refer :all]))

(defn parse-input [input]
  (let [[a b c program] (->> input
                             (re-seq #": ([0-9,]+)")
                             (map second))]
    {:a (parse-long a)
     :b (parse-long b)
     :c (parse-long c)
     :program (as-> program v
                    (str/split v #",")
                    (mapv parse-long v))
     :pc 0
     :output []}))

; part 1

(defn execute [{:as state
                a :a
                b :b
                c :c
                program :program
                pc :pc
                output :output}]
  (let [opcode (get program pc)
        literal (get program (+ pc 1))
        combo (case literal
                4 a
                5 b
                6 c
                literal)]
    (if (nil? opcode)
      state
      (as-> opcode v
          (case v
            0 {:a (long (/ a (math/pow 2 combo)))}
            1 {:b (bit-xor b literal)}
            2 {:b (mod combo 8)}
            3 (if (zero? a)
                {}
                {:pc literal})
            4 {:b (bit-xor b c)}
            5 {:output (conj output (mod combo 8))}
            6 {:b (long (/ a (math/pow 2 combo)))}
            7 {:c (long (/ a (math/pow 2 combo)))})
          (merge state {:pc (+ pc 2)} v)
          (recur v)))))

(defn part1 [file]
  (->> file
       (slurp)
       (parse-input)
       (execute)
       (:output)
       (str/join ",")))

(deftest part1-tests
  (testing "full example"
    (is (= (part1 "src/day17/example1.txt")
           "4,6,3,5,6,3,5,2,1,0")))
  (testing "execute"
    (let [tests [[{:c 9
                   :program [2,6]}
                  {:b 1}]
                 [{:a 10
                   :program [5,0,5,1,5,4]}
                  {:output [0,1,2]}]
                 [{:a 2024
                   :program [0,1,5,4,3,0]}
                  {:output [4,2,5,6,7,7,7,7,3,1,0]
                   :a 0}]
                 [{:b 29
                   :program [1,7]}
                  {:b 26}]
                 [{:b 2024
                   :c 43690
                   :program [4,0]}
                  {:b 44354}]]]
      (doseq [[partial-input want] tests]
        (let [input (merge
                      {:a 0
                       :b 0
                       :c 0
                       :pc 0
                       :output []}
                      partial-input)]
          (is (= want
                 (select-keys
                   (execute input)
                   (keys want)))))))))

(run-test part1-tests)

(println "\npart 1:" (part1 "src/day17/input.txt"))
