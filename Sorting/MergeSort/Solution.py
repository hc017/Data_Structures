"""Day 18 - Sorting: Merge Sort.

Sort a list using divide and conquer.
"""
from typing import List


def bubble_sort(a: List[int]) -> List[int]:
    """Brute force. O(n^2) time, O(1) extra space."""
    a = a[:]
    n = len(a)
    for i in range(n - 1):
        swapped = False
        for j in range(n - 1 - i):
            if a[j] > a[j + 1]:
                a[j], a[j + 1] = a[j + 1], a[j]
                swapped = True
        if not swapped:
            break
    return a


def merge(left: List[int], right: List[int]) -> List[int]:
    """Merge two sorted lists into one sorted list (stable)."""
    out: List[int] = []
    i = j = 0
    while i < len(left) and j < len(right):
        if left[i] <= right[j]:
            out.append(left[i])
            i += 1
        else:
            out.append(right[j])
            j += 1
    out.extend(left[i:])
    out.extend(right[j:])
    return out


def merge_sort(a: List[int]) -> List[int]:
    """Optimized. O(n log n) time, O(n) extra space."""
    if len(a) <= 1:
        return a
    mid = len(a) // 2
    return merge(merge_sort(a[:mid]), merge_sort(a[mid:]))


if __name__ == "__main__":
    data = [38, 27, 43, 3, 9, 82, 10]
    print("Input      :", data)
    print("Bubble Sort:", bubble_sort(data))
    print("Merge Sort :", merge_sort(data))
