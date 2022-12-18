from pathlib import Path

filename = "./input.txt"
path = Path(__file__).with_name(filename)
with open(path, "r") as f:
    data = f.read().rstrip()

# note: just hardcode limit and limit-1 when porting to hex
def day6(limit: int):
    i = 1
    buffer = list(data[:limit-1])
    for c in data:
        if isinstance(buffer, int):
            print(buffer)
            return
        buffer.append(c)
        buffer = buffer[1:] if len(set(buffer)) != limit else i
        i += 1

# part 1
day6(4)

# part 2
day6(14)
