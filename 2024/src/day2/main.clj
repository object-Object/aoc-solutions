(ns day2.main
  (:require [clojure.string :as str]))

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

(defn is-safe [row]
  (and
    (or
      (every? #(> 0 %) row)
      (every? #(< 0 %) row))
    (every? #(<= 1 (abs %) 3) row)))

(defn part1-row [row]
  (->> row
       (partition 2 1)
       (map #(apply - %))
       (is-safe)))

(defn part1 [input]
  (->> input
       (map part1-row)
       (remove false?)
       (count)
       (println)))

(part1 (parse-text "7 6 4 2 1\n1 2 7 8 9\n9 7 6 2 1\n1 3 2 4 5\n8 6 4 4 1\n1 3 6 7 9"))

(part1 (parse-file "src/day2/input.txt"))

; part 2

(defn part2-row [row]
  (as-> row v
       (map
         #(into (subvec v 0 %) (subvec v (inc %)))
         (range (count v)))
       (map part1-row v)
       (some identity v)))



(defn part2 [input]
  (->> input
       (map part2-row)
       (remove nil?)
       (count)
       (println)))

(part2 (parse-text "7 6 4 2 1\n1 2 7 8 9\n9 7 6 2 1\n1 3 2 4 5\n8 6 4 4 1\n1 3 6 7 9"))

(part2 (parse-file "src/day2/input.txt"))
