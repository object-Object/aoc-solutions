# part 1

print(
    sum(
        (all(x > 0 for x in d) or all(x < 0 for x in d))
        and all(1 <= abs(x) <= 3 for x in d)
        for l in open(__file__.replace("\\", "/").rsplit("/", 1)[0] + "/input.txt")
        .read()
        .splitlines()
        for r in [[int(n) for n in l.split()]]
        for d in [[a - b for a, b in zip(r, r[1:])]]
    )
)

# part 2

print(
    sum(
        any(
            (all(x > 0 for x in d) or all(x < 0 for x in d))
            and all(1 <= abs(x) <= 3 for x in d)
            for p in [r[:i] + r[i + 1 :] for i in range(len(r))]
            for d in [[a - b for a, b in zip(p, p[1:])]]
        )
        for l in open(__file__.replace("\\", "/").rsplit("/", 1)[0] + "/input.txt")
        .read()
        .splitlines()
        for r in [[int(n) for n in l.split()]]
    )
)

# lmao.

# fmt: off
print(sum(any((all(x > 0 for x in d) or all(x < 0 for x in d)) and all(1 <= abs(x) <= 3 for x in d) for p in [r[:i] + r[i + 1 :] for i in range(len(r))] for d in [[a - b for a, b in zip(p, p[1:])]]) for l in open(__file__.replace("\\", "/").rsplit("/", 1)[0] + "/input.txt").read().splitlines() for r in [[int(n) for n in l.split()]]))
# fmt: on
