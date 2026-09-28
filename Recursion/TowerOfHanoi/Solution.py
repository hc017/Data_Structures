"""
Day 11 - Recursion
Problem: Tower of Hanoi

Move n disks from a source rod to a destination rod, using an auxiliary rod,
following the classic Tower of Hanoi rules:
    1. Only one disk can be moved at a time.
    2. Only the topmost disk of any rod can be moved.
    3. A larger disk can never be placed on top of a smaller disk.

Two approaches are shown:
    1. Recursive  - the natural, textbook way to think about this problem.
    2. Iterative  - using an explicit stack to simulate the recursion,
                    useful when call-stack depth is a concern for large n.
"""

from typing import List


def solve_recursive(n: int, source: str, auxiliary: str, destination: str, moves: List[str]) -> None:
    """
    Recursively move n disks from `source` to `destination` using `auxiliary`.

    Time:  O(2^n)  -- the number of moves roughly doubles with each extra disk.
    Space: O(n)    -- recursion call-stack depth equals n.
    """
    if n == 0:
        return

    # Step 1: move the top (n - 1) disks out of the way, onto the auxiliary rod
    solve_recursive(n - 1, source, destination, auxiliary, moves)

    # Step 2: move the single remaining (largest) disk to its final destination
    moves.append(f"Move disk {n} from {source} to {destination}")

    # Step 3: move the (n - 1) disks from the auxiliary rod onto the destination rod
    solve_recursive(n - 1, auxiliary, source, destination, moves)


def solve_iterative(n: int, source: str, auxiliary: str, destination: str, moves: List[str]) -> None:
    """
    Iteratively move n disks using an explicit stack that simulates the same
    recursion tree as `solve_recursive`, without relying on Python's call stack.

    Time:  O(2^n)
    Space: O(n) for the explicit stack (proportional to recursion depth).
    """
    # Each stack frame: [n, source, auxiliary, destination, stage]
    # stage 0 = not started, 1 = first sub-call done, 2 = move done
    stack = [[n, source, auxiliary, destination, 0]]

    while stack:
        frame = stack[-1]
        f_n, f_source, f_auxiliary, f_destination, stage = frame

        if f_n == 0:
            stack.pop()
            continue

        if stage == 0:
            frame[4] = 1
            stack.append([f_n - 1, f_source, f_destination, f_auxiliary, 0])
        elif stage == 1:
            moves.append(f"Move disk {f_n} from {f_source} to {f_destination}")
            frame[4] = 2
            stack.append([f_n - 1, f_auxiliary, f_source, f_destination, 0])
        else:
            stack.pop()


if __name__ == "__main__":
    n = 3

    print(f"=== Recursive approach (n = {n}) ===")
    recursive_moves: List[str] = []
    solve_recursive(n, "A", "B", "C", recursive_moves)
    for move in recursive_moves:
        print(move)
    print(f"Total moves: {len(recursive_moves)} (expected {2 ** n - 1})")

    print()

    print(f"=== Iterative approach (n = {n}) ===")
    iterative_moves: List[str] = []
    solve_iterative(n, "A", "B", "C", iterative_moves)
    for move in iterative_moves:
        print(move)
    print(f"Total moves: {len(iterative_moves)} (expected {2 ** n - 1})")
