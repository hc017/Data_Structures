/*
 * Topic     : Arrays
 * Question  : Two Sum - Pair With Given Sum
 * Day       : 2026-07-08
 *
 * Problem:
 * Given an array of integers and a target sum, find the indices of the
 * two numbers such that they add up to the target.
 *
 * Approach:
 * Brute force (O(n^2)) checks every pair. The optimized approach uses a
 * HashMap to remember numbers we've already seen, so for each new number
 * we only need to check "have I seen (target - current) before?" which
 * brings it down to O(n) time, O(n) space.
 */

import java.util.HashMap;
import java.util.Map;

public class Solution {

    // Optimized: single pass with HashMap -> O(n) time, O(n) space
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> seen = new HashMap<>(); // value -> index

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            if (seen.containsKey(complement)) {
                return new int[] { seen.get(complement), i };
            }

            seen.put(nums[i], i);
        }

        return new int[] { -1, -1 }; // no pair found
    }

    // Brute force reference implementation -> O(n^2) time, O(1) space
    public static int[] twoSumBruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[] { i, j };
                }
            }
        }
        return new int[] { -1, -1 };
    }

    public static void main(String[] args) {
        int[] nums = { 2, 7, 11, 15, 1, 9 };
        int target = 9;

        int[] result = twoSum(nums, target);

        if (result[0] == -1) {
            System.out.println("No pair found for target " + target);
        } else {
            System.out.println("Indices: [" + result[0] + ", " + result[1] + "]");
            System.out.println("Values : [" + nums[result[0]] + ", " + nums[result[1]] + "]");
        }
    }
}
