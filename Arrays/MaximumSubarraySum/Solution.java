/*
 * Topic     : Arrays
 * Question  : Maximum Subarray Sum (Kadane's Algorithm)
 * Day       : 2026-07-11
 *
 * Problem:
 * Given an array of integers (which may include negative numbers), find
 * the contiguous subarray with the largest sum and return that sum.
 *
 * Approach:
 * Brute force (O(n^2)) checks the sum of every possible subarray.
 * The optimized approach (Kadane's Algorithm) walks the array once,
 * keeping a running sum. At each step we decide: is it better to extend
 * the current subarray, or start a fresh one from here? That single
 * decision, repeated once per element, brings it down to O(n) time,
 * O(1) space.
 */

public class Solution {

    // Optimized: Kadane's Algorithm -> O(n) time, O(1) space
    public static int maxSubArray(int[] nums) {
        int currentSum = nums[0];
        int bestSum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            // Either extend the previous subarray, or start fresh at nums[i]
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            bestSum = Math.max(bestSum, currentSum);
        }

        return bestSum;
    }

    // Brute force reference implementation -> O(n^2) time, O(1) space
    public static int maxSubArrayBruteForce(int[] nums) {
        int bestSum = Integer.MIN_VALUE;

        for (int i = 0; i < nums.length; i++) {
            int runningSum = 0;
            for (int j = i; j < nums.length; j++) {
                runningSum += nums[j];
                bestSum = Math.max(bestSum, runningSum);
            }
        }

        return bestSum;
    }

    public static void main(String[] args) {
        int[] nums = { -2, 1, -3, 4, -1, 2, 1, -5, 4 };

        int optimized = maxSubArray(nums);
        int brute = maxSubArrayBruteForce(nums);

        System.out.println("Array          : " + java.util.Arrays.toString(nums));
        System.out.println("Max Subarray Sum (Kadane's): " + optimized);
        System.out.println("Max Subarray Sum (Brute)   : " + brute);
    }
}
