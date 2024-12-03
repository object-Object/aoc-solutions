(ns day3.main)

; part 1

(defn part1 [input]
  (->> input
       (re-seq #"mul\((\d{1,3}),(\d{1,3})\)")
       (map #(->> %
                  (rest)
                  (map ^[String] Integer/parseInt)
                  (apply *)))
       (apply +)
       (println)))

(part1 "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")

(part1 (slurp "src/day3/input.txt"))

; part 2

(defn part2-parse [result instrs]
  (if (empty? instrs)
    result
    (let [instr (first instrs)]
      (case (first instr)
        "do()" (recur
                 result
                 (rest instrs))
        "don't()" (recur
                    result
                    (drop-while #(not= "do()" (first %)) instrs))
        (recur
          (->> instr
               (rest)
               (map ^[String] Integer/parseInt)
               (apply *)
               (+ result))
          (rest instrs))))))

(defn part2 [input]
  (->> input
       (re-seq #"mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)")
       (part2-parse 0)
       (println)))

(part2 "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")

(part2 (slurp "src/day3/input.txt"))
