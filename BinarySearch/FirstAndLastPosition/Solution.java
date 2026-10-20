import java.util.Arrays;

/**
 * Day 15 - Binary Search: First and Last Position of Element in a Sorted Array.
 *
 * Given a sorted array (duplicates allowed) and a target, return [first, last]
 * index of the target, or [-1, -1] if it is absent.
 */
public class Solution {

    // Brute force: scan the whole array. Time O(n), Space O(1).
    static int[] bruteForce(int[] nums, int target) {
        int first = -1, last = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                if (first == -1) first = i;
                last = i;
            }
        }
        return new int[]{first, last};
    }

    // Optimized: two binary searches. Time O(log n), Space O(1).
    static int[] searchRange(int[] nums, int target) {
        return new int[]{findBound(nums, target, true), findBound(nums, target, false)};
    }

    // Binary search that keeps searching after a hit to push toward one edge.
    private static int findBound(int[] nums, int target, boolean leftmost) {
        int lo = 0, hi = nums.length - 1, ans = -1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2; // avoids int overflow
            if (nums[mid] == target) {
                ans = mid;                // record candidate
                if (leftmost) hi = mid - 1; // keep looking left
                else lo = mid + 1;          // keep looking right
            } else if (nums[mid] < target) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 2, 2, 3, 5, 5, 8};
        int[][] tests = {{2}, {5}, {4}, {8}};
        for (int[] t : tests) {
            System.out.println("target=" + t[0]
                + " brute=" + Arrays.toString(bruteForce(nums, t[0]))
                + " optimized=" + Arrays.toString(searchRange(nums, t[0])));
        }
    }
}
