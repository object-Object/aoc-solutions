(ns day6.main
  (:require [clojure.string :as str]))

; [row col]
(def UP    [-1 0])
(def RIGHT [0 1])
(def DOWN  [1 0])
(def LEFT  [0 -1])

(defn rotate-cw [[row col]]
  [col (- row)])

(defn parse-row [row]
  (mapv
    #(case %
       \^ :guard
       \# :obstacle
       \. nil)
    row))

(defn parse-map [file]
  (->> file
       (slurp)
       (str/split-lines)
       (mapv parse-row)))

(defn find-guard [guard-map]
  (reduce
    (fn [_ pos]
      (if (= :guard (get-in guard-map pos))
        (reduced pos)
        nil))
    nil
    (for [row (range (count guard-map))
          col (range (count (first guard-map)))]
      [row col])))

(defn part1 [file]
  (let [guard-map (parse-map file)
        max-row (dec (count guard-map))
        max-col (dec (count (first guard-map)))]
    (loop [position (find-guard guard-map)
           direction UP
           positions (transient #{})]
      (let [guard-row (first position)
            guard-col (second position)
            next-position (map + position direction)]
        (if (and (<= 0 guard-row max-row) (<= 0 guard-col max-col))
          (if (= :obstacle (get-in guard-map next-position))
            (recur
              position
              (rotate-cw direction)
              (conj! positions position))
            (recur
              next-position
              direction
              (conj! positions position)))
          (count positions))))))

(println (part1 "src/day6/example.txt"))
(println (part1 "src/day6/input.txt"))
