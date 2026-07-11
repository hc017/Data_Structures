"""
Topic     : Arrays
Question  : Maximum Subarray Sum (Kadane's Algorithm)
Day       : 2026-07-11

Problem:
    Given an array of integers (which may include negative numbers), find
    the contiguous subarray with the largest sum and return that sum.

Approach:
    Brute force (O(n^2)) checks the sum of every possible subarray.
    The optimized approach (Kadane's Algorithm) walks the array once,
    keeping a running sum. At each step we decide: is it better to extend
    the current subarray, or start a fresh one from here? That single
    decision, repeated once per element, brings it down to O(n) time,
    O(1) space.
"""

from typing import List


def max_subarray(nums: List[int]) -> int:
    """Optimized: Kadane's Algorithm -> O(n) time, O(1) space."""
    current_sum = nums[0]
    best_sum = nums[0]

    for num in nums[1:]:
        # Either extend the previous subarray, or start fresh at `num`
        current_sum = max(num, current_sum + num)
        best_sum = max(best_sum, current_sum)

    return best_sum


def max_subarray_brute_force(nums: List[int]) -> int:
    """Brute force reference implementation -> O(n^2) time, O(1) space."""
    best_sum = float("-inf")

    for i in range(len(nums)):
        running_sum = 0
        for j in range(i, len(nums)):
            running_sum += nums[j]
            best_sum = max(best_sum, running_sum)

    return int(best_sum)


if __name__ == "__main__":
    nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]

    optimized = max_subarray(nums)
    brute = max_subarray_brute_force(nums)

    print(f"Array          : {nums}")
    print(f"Max Subarray Sum (Kadane's): {optimized}")
    print(f"Max Subarray Sum (Brute)   : {brute}")
