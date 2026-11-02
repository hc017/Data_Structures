/**
 * Day 17 - Binary Search: Find Peak Element
 *
 * A peak is an element strictly greater than its neighbours.
 * Assume nums[-1] = nums[n] = negative infinity. Return the index of ANY peak.
 */
public class Solution {

    // Approach 1: Brute force, linear scan. Time O(n), Space O(1)
    static int findPeakBruteForce(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            boolean leftOk = (i == 0) || nums[i] > nums[i - 1];
            boolean rightOk = (i == n - 1) || nums[i] > nums[i + 1];
            if (leftOk && rightOk) return i;
        }
        return -1;
    }

    // Approach 2: Binary search. Time O(log n), Space O(1)
    // If nums[mid] < nums[mid + 1], we are climbing, so a peak lies to the right.
    // Otherwise a peak lies at mid or to the left.
    static int findPeakOptimized(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] < nums[mid + 1]) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, 8, 12, 4, 2};
        System.out.println("Array: " + java.util.Arrays.toString(nums));
        System.out.println("Brute force peak index: " + findPeakBruteForce(nums));
        System.out.println("Binary search peak index: " + findPeakOptimized(nums));

        int[] zigzag = {1, 5, 2, 6, 3};
        int idx = findPeakOptimized(zigzag);
        System.out.println("Zigzag peak index: " + idx + " (value " + zigzag[idx] + ")");
    }
}
