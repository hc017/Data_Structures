import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Day 7 - Stacks: Next Greater Element (to the right)
 *
 * Given an array nums, for EVERY element find the first element to its right
 * that is strictly greater than it. If no such element exists, the answer is -1.
 *
 *   nums = [4, 5, 2, 25]  ->  [5, 25, 25, -1]
 *
 * Two approaches are shown below:
 *   - Brute force      : for each i, scan everything to its right -> O(n^2) time, O(1) extra space
 *   - Monotonic stack  : one right-to-left pass                   -> O(n)   time, O(n) space
 *
 * This is the classic "monotonic stack" pattern. Once you see it here you will
 * recognise it in Daily Temperatures, Stock Span, Largest Rectangle in Histogram
 * and Trapping Rain Water.
 */
public class Solution {

    // ---------------------------------------------------------------------
    // APPROACH 1 - BRUTE FORCE
    // ---------------------------------------------------------------------
    // For every index i, walk forward until we meet a strictly bigger value.
    // Obviously correct, but every element may end up rescanning the whole
    // tail of the array, so a sorted-descending input costs O(n^2).
    public static int[] nextGreaterBruteForce(int[] nums) {
        if (nums == null) return new int[0];

        int n = nums.length;
        int[] res = new int[n];

        for (int i = 0; i < n; i++) {
            res[i] = -1;                        // assume nothing bigger exists
            for (int j = i + 1; j < n; j++) {   // scan to the right
                if (nums[j] > nums[i]) {
                    res[i] = nums[j];           // the FIRST bigger one wins
                    break;                      // stop immediately
                }
            }
        }
        return res;
    }

    // ---------------------------------------------------------------------
    // APPROACH 2 - OPTIMIZED (MONOTONIC STACK)
    // ---------------------------------------------------------------------
    // Walk the array from RIGHT to LEFT, keeping a stack of "candidates":
    // values that could still serve as somebody's next greater element.
    //
    // For the current value nums[i]:
    //   1. Pop every candidate <= nums[i]. Such a candidate is useless forever:
    //      for any element further left, nums[i] is CLOSER and at least as big,
    //      so that candidate can never be the answer again.
    //   2. Whatever survives on top is the nearest strictly greater value.
    //      If the stack is empty, there is nothing bigger to the right -> -1.
    //   3. Push nums[i], because it is a candidate for elements to its left.
    //
    // The stack always decreases from bottom to top, hence "monotonic".
    // Every element is pushed once and popped at most once, so despite the
    // inner while loop the total work is O(n), not O(n^2).
    public static int[] nextGreaterOptimal(int[] nums) {
        if (nums == null) return new int[0];

        int n = nums.length;
        int[] res = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();   // holds VALUES, top = nearest candidate

        for (int i = n - 1; i >= 0; i--) {
            // 1. discard candidates shadowed by nums[i]
            while (!stack.isEmpty() && stack.peek() <= nums[i]) {
                stack.pop();
            }

            // 2. the survivor on top is the answer
            res[i] = stack.isEmpty() ? -1 : stack.peek();

            // 3. nums[i] itself becomes a candidate for the elements to its left
            stack.push(nums[i]);
        }
        return res;
    }

    // ---------------------------------------------------------------------
    // VARIANT - return INDICES instead of values
    // ---------------------------------------------------------------------
    // Interviewers often ask for the position rather than the value, and most
    // follow-up problems (e.g. Daily Temperatures) need indices. Same algorithm,
    // the stack simply stores indices and we compare nums[stack.peek()].
    public static int[] nextGreaterIndices(int[] nums) {
        if (nums == null) return new int[0];

        int n = nums.length;
        int[] res = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();   // holds INDICES

        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[stack.peek()] <= nums[i]) {
                stack.pop();
            }
            res[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        return res;
    }

    // ---------------------------------------------------------------------
    // DEMO
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        int[][] tests = {
                {4, 5, 2, 25},
                {13, 7, 6, 12},
                {1, 2, 3, 4},      // strictly increasing -> each points to its neighbour
                {4, 3, 2, 1},      // strictly decreasing -> all -1
                {2, 2, 2},         // duplicates: "strictly greater" means all -1
                {5},               // single element
                {}                 // empty array
        };

        String line = "----------------------------------------";

        for (int[] t : tests) {
            int[] brute = nextGreaterBruteForce(t);
            int[] fast = nextGreaterOptimal(t);

            System.out.println("input   : " + Arrays.toString(t));
            System.out.println("brute   : " + Arrays.toString(brute));
            System.out.println("stack   : " + Arrays.toString(fast));
            System.out.println("indices : " + Arrays.toString(nextGreaterIndices(t)));
            System.out.println("agree   : " + Arrays.equals(brute, fast));
            System.out.println(line);
        }

        // Expected:
        //   [4, 5, 2, 25]  -> [5, 25, 25, -1]
        //   [13, 7, 6, 12] -> [-1, 12, 12, -1]
        //   [1, 2, 3, 4]   -> [2, 3, 4, -1]
        //   [4, 3, 2, 1]   -> [-1, -1, -1, -1]
        //   [2, 2, 2]      -> [-1, -1, -1]
    }
}
