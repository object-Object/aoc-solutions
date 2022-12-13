from pathlib import Path

filename = "./input.txt"
path = Path(__file__).with_name(filename)
with open(path, "r") as f:
    stack_lines, procedure_lines = (s.splitlines() for s in f.read().split("\n\n"))

def day5(should_reverse: bool):
    # this is always an integer, but python doesn't realize it
    num_stacks = int((len(stack_lines[0]) + 1) / 4)
    stacks = [[] for _ in range(num_stacks)]

    for line in stack_lines:
        for i in range(num_stacks):
            box = line[i*4 + 1:i*4 + 2]
            stacks[i] = stacks[i] if box == " " else [box] + stacks[i]

    for line in procedure_lines:
        _, num_to_move, _, start, _, end = line.split(" ")
        num_to_move, start, end = int(num_to_move), int(start), int(end)
        start, end = start - 1, end - 1

        start_stack = stacks[start]
        start_len = len(start_stack)

        stacks[start] = start_stack[0:start_len - num_to_move]
        boxes = start_stack[start_len - num_to_move:start_len]

        if should_reverse: boxes.reverse()
        stacks[end].extend(boxes)

    print("".join(stack.pop() for stack in stacks))

# part 1
day5(True)

# part 2
day5(False)
