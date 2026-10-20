"""Day 15 - Binary Search: First and Last Position of Element in a Sorted Array.

Given a sorted list (duplicates allowed) and a target, return [first, last]
index of the target, or [-1, -1] if it is absent.
"""
from typing import List


def brute_force(nums: List[int], target: int) -> List[int]:
    """Linear scan. Time O(n), Space O(1)."""
    first = last = -1
    for i, value in enumerate(nums):
        if value == target:
            if first == -1:
                first = i
            last = i
    return [first, last]


def find_bound(nums: List[int], target: int, leftmost: bool) -> int:
    """Binary search that keeps going after a hit to reach the edge."""
    lo, hi, ans = 0, len(nums) - 1, -1
    while lo <= hi:
        mid = (lo + hi) // 2
        if nums[mid] == target:
            ans = mid
            if leftmost:
                hi = mid - 1  # keep looking left
            else:
                lo = mid + 1  # keep looking right
        elif nums[mid] < target:
            lo = mid + 1
        else:
            hi = mid - 1
    return ans


def search_range(nums: List[int], target: int) -> List[int]:
    """Two binary searches. Time O(log n), Space O(1)."""
    return [find_bound(nums, target, True), find_bound(nums, target, False)]


if __name__ == "__main__":
    nums = [1, 2, 2, 2, 3, 5, 5, 8]
    for target in (2, 5, 4, 8):
        print(f"target={target} brute={brute_force(nums, target)} "
              f"optimized={search_range(nums, target)}")
