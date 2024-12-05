(ns day5.main
  (:require [clojure.string :as str])
  (:require [clojure.math :as math]))

; part 1

(defn parse-input [file]
  (->> file
       (slurp)
       (str/split-lines)
       (reduce
         (fn [result line]
           (cond
             (str/includes? line "|") (as-> line v
                                            (str/split v #"\|")
                                            (map ^[String] Integer/parseInt v)
                                            (assoc-in
                                              result
                                              [:rules (first v)]
                                              (conj
                                                (get-in result [:rules (first v)] #{})
                                                (second v))))
             (str/includes? line ",") (as-> line v
                                            (str/split v #",")
                                            (mapv ^[String] Integer/parseInt v)
                                            (assoc result :updates (conj (:updates result) v)))
             :else result))
         {:rules {}
          :updates []})))

(defn is-correct [rules update]
  (every? false? (for [i (range 1 (count update))
                       j (range 0 i)]
                   (contains?
                     (get rules (get update i) #{})
                     (get update j)))))

(defn part1 [file]
  (let [{rules :rules updates :updates} (parse-input file)]
    (->> updates
         (filter #(is-correct rules %))
         (map #(get % (math/floor-div (count %) 2)))
         (apply +)
         (println "part 1" file "->"))))

(part1 "src/day5/example.txt")
(part1 "src/day5/input.txt")

; part 2

(defn fix-ordering [rules update]
  (sort
    #(contains? (get rules %1 #{}) %2)
    update))

(defn part2 [file]
  (let [{rules :rules updates :updates} (parse-input file)]
    (->> updates
         (filter #(not (is-correct rules %)))
         (map #(vec (fix-ordering rules %)))
         (map #(get % (math/floor-div (count %) 2)))
         (apply +)
         (println "part 2" file "->"))))

(part2 "src/day5/example.txt")
(part2 "src/day5/input.txt")
