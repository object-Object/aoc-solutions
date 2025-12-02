filename = "2022/day2/input.txt"

# part 1
score = 0
with open(filename, "r") as f:
    for line in f.readlines():
        opponent_str, me_str = line.rstrip().split(" ")
        opponent = "ABC".index(opponent_str)
        me = "XYZ".index(me_str)

        score += me + 1
        score += (me == opponent) and 3 or 0
        score += ((me + 2) % 3 == opponent) and 6 or 0
print(score)

# part 2
score = 0
with open(filename, "r") as f:
    for line in f.readlines():
        opponent_str, goal_str = line.rstrip().split(" ")
        opponent = "ABC".index(opponent_str)
        goal = "XYZ".index(goal_str)

        score += (goal + opponent + 2) % 3 + 3*goal + 1
print(score)
