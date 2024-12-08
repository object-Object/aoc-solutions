(ns day7.main
  (:require [clojure.math :as math]
            [clojure.string :as str]))

(defn parse-line [line]
  (as-> line v
        (str/split v #":? ")
        (mapv parse-long v)
        [(first v) (rest v)]))

(defn parse-file [file]
  (->> file
       (slurp)
       (str/split-lines)
       (mapv parse-line)))

(defn part1-filter
  ([line]
   (part1-filter
     (first line)
     (first (second line))
     (rest (second line))))
  ([target acc values]
   (if (empty? values)
     (= target acc)
     (or
       (part1-filter
         target
         (+ acc (first values))
         (rest values))
       (part1-filter
         target
         (* acc (first values))
         (rest values))))))

(defn part1 [file]
  (->> file
       (parse-file)
       (filter part1-filter)
       (map first)
       (apply +)
       (println "part1" file)))

(part1 "src/day7/example.txt")
(part1 "src/day7/input.txt")

(defn part2-filter
  ([line]
   (part2-filter
     (first line)
     (first (second line))
     (rest (second line))))
  ([target acc values]
   (if (empty? values)
     (= target acc)
     (or
       (part2-filter
         target
         (+ acc (first values))
         (rest values))
       (part2-filter
         target
         (* acc (first values))
         (rest values))
       (part2-filter
         target
         (->> (first values)
              (math/log10)
              (math/floor)
              (inc)
              (math/pow 10)
              (long)
              (* acc)
              (+ (first values)))
         (rest values))))))

(defn part2 [file]
  (->> file
       (parse-file)
       (filter part2-filter)
       (map first)
       (apply +)
       (println "part2" file)))

(part2 "src/day7/example.txt")
(part2 "src/day7/input.txt")
