"""
Topic     : Sliding Window
Question  : Longest Substring Without Repeating Characters
Day       : 2026-07-27

Problem:
Given a string s, find the length of the longest substring that does
not contain any repeating characters.

Approach:
Brute force (O(n^3)) checks every substring and verifies uniqueness.
The optimized approach uses a sliding window with two pointers
(left, right) and a dict of characters currently inside the window.
When a duplicate is seen, the left edge is pulled forward until the
duplicate is removed, giving O(n) time, O(min(n, charset)) space.
"""

from typing import Dict


def length_of_longest_substring(s: str) -> int:
    """Optimized: sliding window with a dict -> O(n) time, O(min(n, charset)) space."""
    last_seen: Dict[str, int] = {}
    max_len = 0
    left = 0

    for right, char in enumerate(s):
        if char in last_seen and last_seen[char] >= left:
            # duplicate found inside current window -> shrink from left
            left = last_seen[char] + 1

        last_seen[char] = right
        max_len = max(max_len, right - left + 1)

    return max_len


def length_of_longest_substring_brute_force(s: str) -> int:
    """Reference brute force -> O(n^3) time, O(min(n, charset)) space."""
    n = len(s)
    max_len = 0

    for i in range(n):
        for j in range(i, n):
            substring = s[i:j + 1]
            if len(set(substring)) == len(substring):
                max_len = max(max_len, len(substring))

    return max_len


if __name__ == "__main__":
    s = "abcabcbb"

    result = length_of_longest_substring(s)
    print(f'Input : "{s}"')
    print(f"Longest substring length without repeats: {result}")

    for test in ["bbbbb", "pwwkew", "", "dvdf"]:
        print(f'"{test}" -> {length_of_longest_substring(test)}')
