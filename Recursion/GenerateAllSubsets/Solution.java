import java.util.ArrayList;
import java.util.List;

/**
 * Day 12 - Recursion: Generate All Subsets (Power Set)
 *
 * Given an array of distinct integers, return every possible subset.
 * For n elements there are exactly 2^n subsets.
 */
public class Solution {

    // Approach 1: Bitmask (iterative). Each number from 0..2^n-1 is a "pick / skip" pattern.
    // Time: O(n * 2^n)  Space: O(n * 2^n) for the output
    public static List<List<Integer>> subsetsBitmask(int[] nums) {
        int n = nums.length;
        List<List<Integer>> result = new ArrayList<>();
        for (int mask = 0; mask < (1 << n); mask++) {
            List<Integer> subset = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {   // bit i set -> pick nums[i]
                    subset.add(nums[i]);
                }
            }
            result.add(subset);
        }
        return result;
    }

    // Approach 2: Recursion (pick / skip decision tree).
    // Time: O(n * 2^n)  Space: O(n) recursion depth (excluding output)
    public static List<List<Integer>> subsetsRecursive(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        helper(nums, 0, new ArrayList<>(), result);
        return result;
    }

    private static void helper(int[] nums, int index, List<Integer> current, List<List<Integer>> result) {
        // Base case: decided on every element -> record this subset
        if (index == nums.length) {
            result.add(new ArrayList<>(current));   // copy, because current keeps changing
            return;
        }
        // Choice 1: skip nums[index]
        helper(nums, index + 1, current, result);

        // Choice 2: pick nums[index]
        current.add(nums[index]);
        helper(nums, index + 1, current, result);

        // Backtrack: undo the pick
        current.remove(current.size() - 1);
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        System.out.println("Input: [1, 2, 3]");
        System.out.println("Bitmask   : " + subsetsBitmask(nums));
        System.out.println("Recursive : " + subsetsRecursive(nums));
        System.out.println("Total subsets = " + subsetsRecursive(nums).size() + " (2^3)");
    }
}
