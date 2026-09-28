import java.util.ArrayList;
import java.util.List;

/**
 * Day 11 - Recursion
 * Problem: Tower of Hanoi
 *
 * Move n disks from a source rod to a destination rod, using an auxiliary rod,
 * following the classic Tower of Hanoi rules:
 *   1. Only one disk can be moved at a time.
 *   2. Only the topmost disk of any rod can be moved.
 *   3. A larger disk can never be placed on top of a smaller disk.
 *
 * This file demonstrates two approaches:
 *   1. Recursive (the natural, textbook way to think about this problem)
 *   2. Iterative using an explicit stack (simulates the recursion manually,
 *      useful when recursion depth / call-stack size is a concern)
 */
public class Solution {

    // ------------------------------------------------------------------
    // Approach 1: Recursive solution
    // Time:  O(2^n)  -> every extra disk roughly doubles the number of moves
    // Space: O(n)    -> recursion call stack depth equals n
    // ------------------------------------------------------------------
    public static void solveRecursive(int n, char source, char auxiliary, char destination, List<String> moves) {
        // Base case: no disks left to move
        if (n == 0) {
            return;
        }

        // Step 1: move the top (n - 1) disks out of the way, onto the auxiliary rod
        solveRecursive(n - 1, source, destination, auxiliary, moves);

        // Step 2: move the single remaining (largest) disk to its final destination
        moves.add("Move disk " + n + " from " + source + " to " + destination);

        // Step 3: move the (n - 1) disks from the auxiliary rod onto the destination rod
        solveRecursive(n - 1, auxiliary, source, destination, moves);
    }

    // ------------------------------------------------------------------
    // Approach 2: Iterative solution using an explicit stack
    // Simulates the exact same recursion tree manually instead of relying on
    // the language's call stack. Useful for very large n where a deep
    // recursive call stack could overflow.
    // Time:  O(2^n)
    // Space: O(n) for the explicit stack (proportional to recursion depth)
    // ------------------------------------------------------------------
    private static class Frame {
        int n;
        char source, auxiliary, destination;
        int stage; // 0 = not started, 1 = first recursive call done, 2 = move done

        Frame(int n, char source, char auxiliary, char destination) {
            this.n = n;
            this.source = source;
            this.auxiliary = auxiliary;
            this.destination = destination;
            this.stage = 0;
        }
    }

    public static void solveIterative(int n, char source, char auxiliary, char destination, List<String> moves) {
        java.util.Deque<Frame> stack = new java.util.ArrayDeque<>();
        stack.push(new Frame(n, source, auxiliary, destination));

        while (!stack.isEmpty()) {
            Frame f = stack.peek();

            if (f.n == 0) {
                stack.pop();
                continue;
            }

            if (f.stage == 0) {
                f.stage = 1;
                // push the "move (n-1) disks to auxiliary" sub-problem
                stack.push(new Frame(f.n - 1, f.source, f.destination, f.auxiliary));
            } else if (f.stage == 1) {
                moves.add("Move disk " + f.n + " from " + f.source + " to " + f.destination);
                f.stage = 2;
                // push the "move (n-1) disks from auxiliary to destination" sub-problem
                stack.push(new Frame(f.n - 1, f.auxiliary, f.source, f.destination));
            } else {
                stack.pop();
            }
        }
    }

    public static void main(String[] args) {
        int n = 3;

        System.out.println("=== Recursive approach (n = " + n + ") ===");
        List<String> recursiveMoves = new ArrayList<>();
        solveRecursive(n, 'A', 'B', 'C', recursiveMoves);
        for (String move : recursiveMoves) {
            System.out.println(move);
        }
        System.out.println("Total moves: " + recursiveMoves.size() + " (expected " + ((1 << n) - 1) + ")");

        System.out.println();

        System.out.println("=== Iterative approach (n = " + n + ") ===");
        List<String> iterativeMoves = new ArrayList<>();
        solveIterative(n, 'A', 'B', 'C', iterativeMoves);
        for (String move : iterativeMoves) {
            System.out.println(move);
        }
        System.out.println("Total moves: " + iterativeMoves.size() + " (expected " + ((1 << n) - 1) + ")");
    }
}
