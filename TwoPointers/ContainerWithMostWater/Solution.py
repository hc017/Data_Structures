"""
Day 3 - Two Pointers
Problem: Container With Most Water

Given a list `height` where height[i] is the height of a vertical line at
position i, find two lines that, together with the x-axis, form a container
holding the most water.

Water held by lines at (i, j) = min(height[i], height[j]) * (j - i)
(bounded by the shorter wall, since water spills over the shorter side).
"""

from typing import List


def max_area_brute_force(height: List[int]) -> int:
    """
    Try every pair of lines and keep the best area.

    Time:  O(n^2) - nested loop over all pairs
    Space: O(1)
    """
    best = 0
    n = len(height)
    for i in range(n):
        for j in range(i + 1, n):
            area = min(height[i], height[j]) * (j - i)
            best = max(best, area)
    return best


def max_area_two_pointers(height: List[int]) -> int:
    """
    Two pointers starting at both ends, moving the shorter wall inward.
    Moving the taller wall can never help - width only shrinks and
    height is still capped by the shorter side.

    Time:  O(n) - single pass, pointers move toward each other
    Space: O(1)
    """
    left, right = 0, len(height) - 1
    best = 0

    while left < right:
        h = min(height[left], height[right])
        width = right - left
        best = max(best, h * width)

        # Move the shorter wall inward - it's the bottleneck.
        if height[left] < height[right]:
            left += 1
        else:
            right -= 1

    return best


if __name__ == "__main__":
    height = [1, 8, 6, 2, 5, 4, 8, 3, 7]

    print(f"Input heights: {height}")
    print(f"Brute force max area:  {max_area_brute_force(height)}")
    print(f"Two pointers max area: {max_area_two_pointers(height)}")
    # Expected output: 49 (between index 1, height 8 and index 8, height 7)
