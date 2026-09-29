"""Day 12 - Recursion: Generate All Subsets (Power Set).

Given a list of distinct integers, return every possible subset.
For n elements there are exactly 2**n subsets.
"""
from typing import List


def subsets_bitmask(nums: List[int]) -> List[List[int]]:
    """Iterative approach: each mask in 0..2^n-1 is a pick/skip pattern.

    Time: O(n * 2^n), Space: O(n * 2^n) for the output.
    """
    n = len(nums)
    result: List[List[int]] = []
    for mask in range(1 << n):
        result.append([nums[i] for i in range(n) if mask & (1 << i)])
    return result


def subsets_recursive(nums: List[int]) -> List[List[int]]:
    """Recursive pick/skip decision tree with backtracking.

    Time: O(n * 2^n), Space: O(n) recursion depth (excluding output).
    """
    result: List[List[int]] = []
    current: List[int] = []

    def helper(index: int) -> None:
        # Base case: decided on every element, record a copy of the subset
        if index == len(nums):
            result.append(current[:])
            return
        # Choice 1: skip nums[index]
        helper(index + 1)
        # Choice 2: pick nums[index]
        current.append(nums[index])
        helper(index + 1)
        # Backtrack: undo the pick
        current.pop()

    helper(0)
    return result


if __name__ == "__main__":
    nums = [1, 2, 3]
    print("Input:", nums)
    print("Bitmask   :", subsets_bitmask(nums))
    print("Recursive :", subsets_recursive(nums))
    print("Total subsets =", len(subsets_recursive(nums)), "(2^3)")
