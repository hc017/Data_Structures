"""
Topic     : Arrays
Question  : Two Sum - Pair With Given Sum
Day       : 2026-07-08

Problem:
Given an array of integers and a target sum, find the indices of the
two numbers such that they add up to the target.

Approach:
Brute force (O(n^2)) checks every pair. The optimized approach uses a
dictionary (hash map) to remember numbers already seen, so for each new
number we only check "have I seen (target - current) before?" which
brings it down to O(n) time, O(n) space.
"""

from typing import List, Tuple


def two_sum(nums: List[int], target: int) -> Tuple[int, int]:
    """Optimized: single pass with a dict -> O(n) time, O(n) space."""
    seen = {}  # value -> index

    for i, num in enumerate(nums):
        complement = target - num
        if complement in seen:
            return seen[complement], i
        seen[num] = i

    return -1, -1  # no pair found


def two_sum_brute_force(nums: List[int], target: int) -> Tuple[int, int]:
    """Reference brute force -> O(n^2) time, O(1) space."""
    for i in range(len(nums)):
        for j in range(i + 1, len(nums)):
            if nums[i] + nums[j] == target:
                return i, j
    return -1, -1


if __name__ == "__main__":
    nums = [2, 7, 11, 15, 1, 9]
    target = 9

    i, j = two_sum(nums, target)

    if i == -1:
        print(f"No pair found for target {target}")
    else:
        print(f"Indices: [{i}, {j}]")
        print(f"Values : [{nums[i]}, {nums[j]}]")
