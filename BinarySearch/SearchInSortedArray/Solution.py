"""
Day 14 - Binary Search: Search in a Sorted Array

Problem: Given a sorted list of distinct integers and a target, return the
index of the target, or -1 if it is not present.

Approaches:
    1. Brute force (linear scan)  - O(n) time, O(1) space
    2. Binary search (iterative)  - O(log n) time, O(1) space
    3. Binary search (recursive)  - O(log n) time, O(log n) stack space
"""

from typing import List


def linear_search(arr: List[int], target: int) -> int:
    """Brute force: check every element from left to right."""
    for i, value in enumerate(arr):
        if value == target:
            return i
    return -1


def binary_search(arr: List[int], target: int) -> int:
    """Iterative binary search. Each step discards half of the search space."""
    low, high = 0, len(arr) - 1

    while low <= high:
        mid = low + (high - low) // 2

        if arr[mid] == target:
            return mid             # found it
        if arr[mid] < target:
            low = mid + 1          # target must be in the right half
        else:
            high = mid - 1         # target must be in the left half

    return -1                      # search space is empty -> not present


def binary_search_recursive(arr: List[int], target: int, low: int, high: int) -> int:
    """Recursive binary search on arr[low..high]."""
    if low > high:
        return -1
    mid = low + (high - low) // 2
    if arr[mid] == target:
        return mid
    if arr[mid] < target:
        return binary_search_recursive(arr, target, mid + 1, high)
    return binary_search_recursive(arr, target, low, mid - 1)


if __name__ == "__main__":
    arr = [2, 5, 8, 12, 16, 23, 38, 56, 72, 91]

    target = 23
    print("Array  :", arr)
    print("Target :", target)
    print("Linear search      -> index", linear_search(arr, target))
    print("Binary (iterative) -> index", binary_search(arr, target))
    print("Binary (recursive) -> index", binary_search_recursive(arr, target, 0, len(arr) - 1))

    missing = 40
    print("\nTarget :", missing, "(not in array)")
    print("Binary (iterative) -> index", binary_search(arr, missing))
