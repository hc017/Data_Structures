"""Day 16 - Binary Search: Search in Rotated Sorted Array.

Given a sorted array of distinct integers rotated at an unknown pivot,
return the index of target or -1 in O(log n).
"""
from typing import List


def linear_search(nums: List[int], target: int) -> int:
    """Brute force scan. O(n) time, O(1) space."""
    for i, value in enumerate(nums):
        if value == target:
            return i
    return -1


def search(nums: List[int], target: int) -> int:
    """Modified binary search. O(log n) time, O(1) space.

    At each step one half is always sorted. If the target lies inside the
    sorted half, keep that half, otherwise keep the other one.
    """
    lo, hi = 0, len(nums) - 1
    while lo <= hi:
        mid = (lo + hi) // 2
        if nums[mid] == target:
            return mid

        if nums[lo] <= nums[mid]:  # left half sorted
            if nums[lo] <= target < nums[mid]:
                hi = mid - 1
            else:
                lo = mid + 1
        else:  # right half sorted
            if nums[mid] < target <= nums[hi]:
                lo = mid + 1
            else:
                hi = mid - 1
    return -1


if __name__ == "__main__":
    arr = [4, 5, 6, 7, 0, 1, 2]
    for t in (0, 5, 3, 4, 2):
        print(f"target={t} -> brute={linear_search(arr, t)}, optimized={search(arr, t)}")
    # Expected: 4, 1, -1, 0, 6
