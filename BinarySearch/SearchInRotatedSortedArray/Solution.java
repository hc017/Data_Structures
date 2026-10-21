/**
 * Day 16 - Binary Search: Search in Rotated Sorted Array
 *
 * Problem: A sorted array of distinct integers was rotated at an unknown pivot.
 * Given a target, return its index or -1. Required: O(log n).
 */
public class Solution {

    // Approach 1: Brute force linear scan. O(n) time, O(1) space.
    static int linearSearch(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) return i;
        }
        return -1;
    }

    // Approach 2: Modified binary search. O(log n) time, O(1) space.
    // Key idea: at every step, at least one half [lo..mid] or [mid..hi] is sorted.
    // Check whether the target lies inside the sorted half, and discard the other half.
    static int search(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2; // avoids int overflow
            if (nums[mid] == target) return mid;

            if (nums[lo] <= nums[mid]) {
                // Left half is sorted
                if (nums[lo] <= target && target < nums[mid]) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            } else {
                // Right half is sorted
                if (nums[mid] < target && target <= nums[hi]) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = {4, 5, 6, 7, 0, 1, 2};
        int[] targets = {0, 5, 3, 4, 2};
        for (int t : targets) {
            System.out.println("target=" + t + " -> brute=" + linearSearch(nums, t)
                    + ", optimized=" + search(nums, t));
        }
        // Expected: 4, 1, -1, 0, 6
    }
}
