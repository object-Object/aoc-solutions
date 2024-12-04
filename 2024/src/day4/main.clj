(ns day4.main
  (:require [clojure.string :as str]))

(defn part1 [file]
  (let [grid (->> file
                  (slurp)
                  (str/split-lines)
                  (mapv vec))
        size (count grid)
        indices (range size)]
    (->>
      (for [row indices col indices]
        (map
          #(apply str %)
          [(mapv #(get-in grid [row (+ col %)]) (range 4))
           (mapv #(get-in grid [row (- col %)]) (range 4))
           (mapv #(get-in grid [(+ row %) col]) (range 4))
           (mapv #(get-in grid [(- row %) col]) (range 4))
           (mapv #(get-in grid [(+ row %) (+ col %)]) (range 4))
           (mapv #(get-in grid [(+ row %) (- col %)]) (range 4))
           (mapv #(get-in grid [(- row %) (+ col %)]) (range 4))
           (mapv #(get-in grid [(- row %) (- col %)]) (range 4))]))
      (flatten)
      (map #(= % "XMAS"))
      (remove false?)
      (count)
      (println "part 1" file "->"))))

(part1 "src/day4/example.txt")
(part1 "src/day4/input.txt")

(defn part2 [file]
  (let [grid (->> file
                  (slurp)
                  (str/split-lines)
                  (mapv vec))
        size (count grid)
        indices (range 1 (- size 1))]
    (->>
      (for [row indices col indices]
        (and
          (= \A (get-in grid [row col]))
          (contains?
            #{"MS" "SM"}
            (str
              (get-in grid [(dec row) (dec col)])
              (get-in grid [(inc row) (inc col)])))
          (contains?
            #{"MS" "SM"}
            (str
              (get-in grid [(dec row) (inc col)])
              (get-in grid [(inc row) (dec col)])))))
      (remove false?)
      (count)
      (println "part 2" file "->"))))

(part2 "src/day4/example.txt")
(part2 "src/day4/input.txt")
