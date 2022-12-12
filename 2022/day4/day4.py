from pathlib import Path

filename = "./input.txt"
path = Path(__file__).with_name(filename)
with open(path, "r") as f:
    lines = f.read().splitlines()

# part 1
total = 0
for line in lines:
    a, bc, d = line.split("-")
    b, c = bc.split(",")
    a, b, c, d = (int(x) for x in (a, b, c, d))

    total += 0 if (c - a) * (d - b) > 0 else 1
print(total)

# part 2
total = 0
for line in lines:
    a, bc, d = line.split("-")
    b, c = bc.split(",")
    a, b, c, d = (int(x) for x in (a, b, c, d))

    total += 1 if c <= b and d >= a else 0
print(total)
