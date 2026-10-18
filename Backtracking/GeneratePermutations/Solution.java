import java.util.*;

/**
 * Day 13 - Backtracking: Generate All Permutations
 *
 * Problem: Given an array of distinct integers, return all possible orderings.
 *
 * Approach 1 (Brute force-ish): build permutations by inserting each number
 *   into every position of every smaller permutation. Extra lists everywhere.
 * Approach 2 (Backtracking, in-place swap): fix one position at a time, swap in
 *   each candidate, recurse, then undo the swap. No extra "used" array needed.
 *
 * Time : O(n * n!)   (n! permutations, O(n) to copy each)
 * Space: O(n) recursion depth (excluding output)
 */
public class Solution {

    // Approach 1: insertion based
    public static List<List<Integer>> permuteInsertion(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        for (int num : nums) {
            List<List<Integer>> next = new ArrayList<>();
            for (List<Integer> perm : result) {
                for (int pos = 0; pos <= perm.size(); pos++) {
                    List<Integer> copy = new ArrayList<>(perm);
                    copy.add(pos, num);
                    next.add(copy);
                }
            }
            result = next;
        }
        return result;
    }

    // Approach 2: backtracking with swaps
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(nums, 0, result);
        return result;
    }

    private static void backtrack(int[] nums, int start, List<List<Integer>> result) {
        // Base case: every position is fixed, record this ordering
        if (start == nums.length) {
            List<Integer> perm = new ArrayList<>();
            for (int n : nums) perm.add(n);
            result.add(perm);
            return;
        }
        for (int i = start; i < nums.length; i++) {
            swap(nums, start, i);          // choose
            backtrack(nums, start + 1, result); // explore
            swap(nums, start, i);          // un-choose (backtrack)
        }
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        System.out.println("Insertion : " + permuteInsertion(nums));
        System.out.println("Backtrack : " + permute(nums.clone()));
        // Expected 6 permutations: [1,2,3] [1,3,2] [2,1,3] [2,3,1] [3,2,1] [3,1,2]
    }
}
