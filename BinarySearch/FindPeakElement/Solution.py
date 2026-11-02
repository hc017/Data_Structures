"""Day 17 - Binary Search: Find Peak Element.

A peak is an element strictly greater than its neighbours. Treat the
values outside the array as negative infinity. Return the index of any peak.
"""
from typing import List


def find_peak_brute_force(nums: List[int]) -> int:
    """Linear scan. Time O(n), Space O(1)."""
    n = len(nums)
    for i in range(n):
        left_ok = i == 0 or nums[i] > nums[i - 1]
        right_ok = i == n - 1 or nums[i] > nums[i + 1]
        if left_ok and right_ok:
            return i
    return -1


def find_peak_optimized(nums: List[int]) -> int:
    """Binary search on the slope. Time O(log n), Space O(1).

    If nums[mid] < nums[mid + 1] the slope goes up, so a peak is on the right.
    Otherwise a peak is at mid or on the left.
    """
    lo, hi = 0, len(nums) - 1
    while lo < hi:
        mid = lo + (hi - lo) // 2
        if nums[mid] < nums[mid + 1]:
            lo = mid + 1
        else:
            hi = mid
    return lo


if __name__ == "__main__":
    nums = [1, 3, 8, 12, 4, 2]
    print("Array:", nums)
    print("Brute force peak index:", find_peak_brute_force(nums))
    print("Binary search peak index:", find_peak_optimized(nums))

    zigzag = [1, 5, 2, 6, 3]
    idx = find_peak_optimized(zigzag)
    print(f"Zigzag peak index: {idx} (value {zigzag[idx]})")
