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
       \. :empty)
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

(println "part1 example" (part1 "src/day6/example.txt"))
(println "part1 input"(part1 "src/day6/input.txt"))

(defn check-loop [guard-map max-row max-col position direction visited]
  (let [guard-row (first position)
        guard-col (second position)
        next-position (map + position direction)]
    (cond
      (not (and (<= 0 guard-row max-row) (<= 0 guard-col max-col))) false
      (contains? visited [position direction]) true
      (= :obstacle (get-in guard-map next-position)) (recur
                                                       guard-map max-row max-col
                                                       position
                                                       (rotate-cw direction)
                                                       visited)
      :else (recur
              guard-map max-row max-col
              next-position
              direction
              (conj! visited [position direction])))))

(defn part2 [file]
  (let [guard-map (parse-map file)
        max-row (dec (count guard-map))
        max-col (dec (count (first guard-map)))]
    (loop [position (find-guard guard-map)
           direction UP
           positions (transient #{})
           visited (transient #{})]
      (let [guard-row (first position)
            guard-col (second position)
            next-position (map + position direction)]
        (if (and (<= 0 guard-row max-row) (<= 0 guard-col max-col))
          (case (get-in guard-map next-position)
            :obstacle (recur
                        position
                        (rotate-cw direction)
                        positions
                        visited)
            :empty (recur
                     next-position
                     direction
                     (if (and
                           (not (contains? visited next-position))
                           (check-loop
                             (assoc-in guard-map next-position :obstacle)
                             max-row
                             max-col
                             position
                             direction
                             (transient #{})))
                       (conj! positions next-position)
                       positions)
                     (conj! visited position))
            (recur
              next-position
              direction
              positions
              (conj! visited position)))
          (count positions))))))

(println "part2 example" (part2 "src/day6/example.txt"))
(println "part2 input" (part2 "src/day6/input.txt"))

; 1847 - too high
; 1846 - too high
