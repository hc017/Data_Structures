"""Day 13 - Backtracking: Generate All Permutations.

Given a list of distinct integers, return every possible ordering.

Approach 1: insertion - insert each number into every slot of every smaller
            permutation.
Approach 2: backtracking with in-place swaps - fix one index at a time, try each
            candidate, recurse, then undo the swap.

Time : O(n * n!)
Space: O(n) recursion depth (excluding output)
"""
from typing import List


def permute_insertion(nums: List[int]) -> List[List[int]]:
    """Build permutations by inserting each number into all positions."""
    result: List[List[int]] = [[]]
    for num in nums:
        result = [p[:pos] + [num] + p[pos:] for p in result for pos in range(len(p) + 1)]
    return result


def permute(nums: List[int]) -> List[List[int]]:
    """Backtracking with swaps; returns all permutations of nums."""
    nums = nums[:]
    result: List[List[int]] = []

    def backtrack(start: int) -> None:
        if start == len(nums):          # base case: all positions fixed
            result.append(nums[:])
            return
        for i in range(start, len(nums)):
            nums[start], nums[i] = nums[i], nums[start]   # choose
            backtrack(start + 1)                          # explore
            nums[start], nums[i] = nums[i], nums[start]   # un-choose

    backtrack(0)
    return result


if __name__ == "__main__":
    data = [1, 2, 3]
    print("Insertion :", permute_insertion(data))
    print("Backtrack :", permute(data))
    # Expected 6 permutations
