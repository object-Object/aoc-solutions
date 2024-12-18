(ns day11.main
  (:require [clojure.math :as math]
            [clojure.string :as str]))

(defn parse-input [input]
  (as-> input v
        (str/split v #"\s+")
        (map parse-long v)))

(defn count-digits [n]
  (if (zero? n)
    1
    (-> n
        (math/log10)
        (inc)
        (long))))

; part 1

(defn part1-step [n]
  (let [digits (count-digits n)
        exp (long (math/pow 10 (/ digits 2)))]
    (cond
      (zero? n) 1
      (even? digits) [(long (/ n exp))
                      (mod n exp)]
      :else (* n 2024))))

(defn part1-run [stones blinks]
  (if (zero? blinks)
    stones
    (recur
      (->> stones
           (map part1-step)
           (flatten))
      (dec blinks))))

(defn part1 [input blinks]
  (-> input
       (parse-input)
       (part1-run blinks)
       (count)
       (println)))

(part1 "0 1 10 99 999" 1)

(part1 "125 17" 6)
(part1 "125 17" 25)

(part1 (slurp "src/day11/input.txt") 25)

; part 2
