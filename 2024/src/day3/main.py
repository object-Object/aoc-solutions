from types import NoneType

# part 1


def part1():
    print(
        (
            data := open(
                __file__.replace("\\", "/").rsplit("/", 1)[0] + "/input.txt"
            ).read()
        )
        and sum(
            (
                f := lambda s, mode, result:
                # fmt: off
                isinstance(s, str)
                and isinstance(mode, int)
                and isinstance(result, (int, NoneType))
                and
                # fmt: on
                [
                    # default
                    lambda: f(s.removeprefix("mul("), 1, None)
                    if s.startswith("mul(")
                    else 0,
                    # number
                    lambda: (
                        f(s[len(n) :], 3, result * int(n))
                        if result is not None
                        else f(s[len(n) :], 2, int(n))
                    )
                    if 1 <= len(n := s[: len(s) - len(s.lstrip("0123456789"))]) <= 3
                    else 0,
                    # ,
                    lambda: f(s[1:], 1, result) if s[0] == "," else 0,
                    # )
                    lambda: result if result is not None and s[0] == ")" else 0,
                ][mode]()
            )(data[i:], 0, None)
            for i in range(len(data))
        )
    )


part1()

# part 2


def part2():
    print(
        (
            data := open(
                __file__.replace("\\", "/").rsplit("/", 1)[0] + "/input.txt"
            ).read()
        )
        and (
            f := lambda s, mode, result, state:
            # fmt: off
            isinstance(s, str)
            and isinstance(mode, int)
            and isinstance(result, (int, NoneType))
            and isinstance(state, bool)
            and
            # fmt: on
            [
                # default
                lambda: f(s.removeprefix("mul("), 1, None, state)
                if s.startswith("mul(")
                else (0, False)
                if s.startswith("don't()")
                else (0, True),
                # number
                lambda: (
                    f(s[len(n) :], 3, result * int(n), state)
                    if result is not None
                    else f(s[len(n) :], 2, int(n), state)
                )
                if 1 <= len(n := s[: len(s) - len(s.lstrip("0123456789"))]) <= 3
                else (0, True),
                # ,
                lambda: f(s[1:], 1, result, state) if s[0] == "," else (0, True),
                # )
                lambda: (result, True)
                if result is not None and s[0] == ")"
                else (0, True),
            ][mode]()
            if state
            else (0, True)
            if s.startswith("do()")
            else (0, False)
        )
        and __import__("functools").reduce(
            lambda acc, i: (ret := f(data[i:], 0, None, acc[1]))
            and (acc[0] + ret[0], ret[1]),
            range(len(data)),
            (0, True),
        )[0]
    )


part2()

# fmt: off
print((data := open(__file__.replace("\\", "/").rsplit("/", 1)[0] + "/input.txt").read()) and (f := lambda s, mode, result, state: [lambda: f(s.removeprefix("mul("), 1, None, state) if s.startswith("mul(") else (0, False) if s.startswith("don't()") else (0, True), lambda: (f(s[len(n) :], 3, result * int(n), state) if result is not None else f(s[len(n) :], 2, int(n), state)) if 1 <= len(n := s[: len(s) - len(s.lstrip("0123456789"))]) <= 3 else (0, True), lambda: f(s[1:], 1, result, state) if s[0] == "," else (0, True), lambda: (result, True) if result is not None and s[0] == ")" else (0, True)][mode]() if state else (0, True) if s.startswith("do()") else (0, False)) and __import__("functools").reduce(lambda acc, i: (ret := f(data[i:], 0, None, acc[1])) and (acc[0] + ret[0], ret[1]), range(len(data)),(0, True))[0])
# fmt: on
