"""Day 7 - Stacks: Next Greater Element (to the right).

Given a list ``nums``, for EVERY element find the first element to its right
that is strictly greater than it. If no such element exists, the answer is -1.

    nums = [4, 5, 2, 25]  ->  [5, 25, 25, -1]

Two approaches:
    * brute force     - for each i, scan to the right.  O(n^2) time, O(1) extra space
    * monotonic stack - one right-to-left pass.         O(n)   time, O(n) space

This is the classic monotonic-stack pattern; the same skeleton solves Daily
Temperatures, Stock Span, Largest Rectangle in Histogram and Trapping Rain Water.
"""

from typing import List


def next_greater_brute_force(nums: List[int]) -> List[int]:
    """Brute force: for every index, walk right until a bigger value appears.

    Args:
        nums: list of integers.

    Returns:
        A list where position ``i`` holds the first strictly greater value to
        the right of ``nums[i]``, or ``-1`` when there is none.
    """
    n = len(nums)
    res = [-1] * n

    for i in range(n):
        for j in range(i + 1, n):        # scan everything to the right
            if nums[j] > nums[i]:
                res[i] = nums[j]         # the FIRST bigger one wins
                break                    # stop immediately
    return res


def next_greater_optimal(nums: List[int]) -> List[int]:
    """Monotonic stack: a single right-to-left pass.

    Walking backwards, the stack keeps only values that could still be some
    element's answer. Anything ``<= nums[i]`` is shadowed by ``nums[i]`` (which
    is closer and at least as large), so it is popped and never needed again.
    Whatever survives on top is exactly the nearest strictly greater value.

    Each element is pushed once and popped at most once, so the inner ``while``
    does not make this quadratic - the total work is O(n).

    Args:
        nums: list of integers.

    Returns:
        List of next greater elements, ``-1`` where none exists.
    """
    n = len(nums)
    res = [-1] * n
    stack: List[int] = []                # VALUES, decreasing bottom -> top

    for i in range(n - 1, -1, -1):
        # 1. drop candidates shadowed by nums[i]
        while stack and stack[-1] <= nums[i]:
            stack.pop()

        # 2. the survivor on top is the answer
        if stack:
            res[i] = stack[-1]

        # 3. nums[i] is a candidate for everything to its left
        stack.append(nums[i])

    return res


def next_greater_indices(nums: List[int]) -> List[int]:
    """Same algorithm, but returns the INDEX of the next greater element.

    Most follow-up problems (Daily Temperatures, Stock Span) need positions
    rather than values. The stack simply stores indices instead.

    Args:
        nums: list of integers.

    Returns:
        List of indices, ``-1`` where no greater element exists to the right.
    """
    n = len(nums)
    res = [-1] * n
    stack: List[int] = []                # INDICES

    for i in range(n - 1, -1, -1):
        while stack and nums[stack[-1]] <= nums[i]:
            stack.pop()
        if stack:
            res[i] = stack[-1]
        stack.append(i)

    return res


if __name__ == "__main__":
    tests = [
        [4, 5, 2, 25],
        [13, 7, 6, 12],
        [1, 2, 3, 4],      # strictly increasing -> each points to its neighbour
        [4, 3, 2, 1],      # strictly decreasing -> all -1
        [2, 2, 2],         # duplicates: "strictly greater" means all -1
        [5],               # single element
        [],                # empty list
    ]

    for t in tests:
        brute = next_greater_brute_force(t)
        fast = next_greater_optimal(t)

        print(f"input   : {t}")
        print(f"brute   : {brute}")
        print(f"stack   : {fast}")
        print(f"indices : {next_greater_indices(t)}")
        print(f"agree   : {brute == fast}")
        print("-" * 40)

    # Expected:
    #   [4, 5, 2, 25]  -> [5, 25, 25, -1]
    #   [13, 7, 6, 12] -> [-1, 12, 12, -1]
    #   [1, 2, 3, 4]   -> [2, 3, 4, -1]
    #   [4, 3, 2, 1]   -> [-1, -1, -1, -1]
    #   [2, 2, 2]      -> [-1, -1, -1]
