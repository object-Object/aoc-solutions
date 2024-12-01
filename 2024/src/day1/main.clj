(ns day1.main
  (:require [clojure.string :as str])
  (:require [clojure.java.io :as io]))

(defn parse-lines [lines]
  (for [line lines]
    (as-> line v
          (str/split v #"\s+")
          (mapv ^[String] Integer/parseInt v))))

(defn parse-text [text]
  (-> text
      (str/split-lines)
      (parse-lines)))

(defn parse-file [file]
  (-> file
      (slurp)
      (parse-text)))

; part 1

(defn part1 [input]
  (->> input
       (apply map vector)
       (map sort)
       (apply map #(abs (- %1 %2)))
       (reduce +)
       (println)))

(def example "3   4\n4   3\n2   5\n1   3\n3   9\n3   3")

(part1 (parse-text example))

(part1 (parse-file "src/day1/input.txt"))

; part 2

(defn part2 [input]
  (let [[left right] (apply map vector input)
        counts (frequencies right)]
    (->> left
         (map #(* % (get counts % 0)))
         (reduce +)
         (println))))

(part2 (parse-text example))

(part2 (parse-file "src/day1/input.txt"))
