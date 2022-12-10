from pathlib import Path
import string

filename = "input.txt"
path = Path(__file__).with_name(filename)
with open(path, "r") as f:
    lines = f.read().splitlines()

def peek_set(s: set):
    return next(iter(s))

priorities = "-" + string.ascii_letters

# part 1
total = 0
for line in lines:
    middle = len(line) // 2
    first, second = line[:middle], line[middle:]
    common = set(first).intersection(set(second)).pop()
    total += priorities.index(common)
print(total)

# part 2
group = 0
total = 0
common = set()
for line_str in lines:
    line = set(line_str)
    common = line if not group else common.intersection(line)
    group = (group + 1) % 3
    total += priorities.index(peek_set(common)) if not group else 0
print(total)
