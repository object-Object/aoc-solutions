elves = [0]
with open("day1_input.txt", "r") as f:
    for line in f.readlines():
        if not line.isspace():
            elves[-1] += int(line)
        else:
            elves.append(0)
print(sum(sorted(elves)[-3:]))


current_sum = 0
max_sums = [0, 0, 0]
with open("day1_input.txt", "r") as f:
    for line_str in f.readlines():
        try:
            line = int(line_str)
        except ValueError:
            line = None
        line = current_sum + (line if line != None else -current_sum)
        current_sum = line

        c = max(line, max_sums[2])
        b = max(min(line, max_sums[2]), max_sums[1])
        a = max(min(line, max_sums[1]), max_sums[0])
        max_sums = [a, b, c]
print(sum(max_sums))
