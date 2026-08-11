import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * Day 6 - Stacks: Valid Parentheses
 *
 * Given a string containing only the characters '(', ')', '{', '}', '[' and ']',
 * determine if the input string is valid.
 *
 * A string is valid when:
 *   1. Every open bracket is closed by a bracket of the SAME type.
 *   2. Brackets are closed in the correct ORDER (most recent open closes first).
 *   3. No closing bracket appears without a matching open bracket before it.
 *
 * Two approaches are shown below:
 *   - Brute force  : repeatedly strip out adjacent matching pairs  -> O(n^2) time, O(n) space
 *   - Optimized    : single pass with a stack                      -> O(n)   time, O(n) space
 */
public class Solution {

    // ---------------------------------------------------------------------
    // APPROACH 1 - BRUTE FORCE
    // ---------------------------------------------------------------------
    // Idea: a valid string must contain at least one adjacent pair like "()"
    // "[]" or "{}". Delete every such pair, then repeat. If the string
    // eventually becomes empty, it was valid. If we can no longer delete
    // anything but characters remain, it was invalid.
    //
    // Each pass costs O(n) and we may need O(n) passes -> O(n^2) overall.
    public static boolean isValidBruteForce(String s) {
        if (s == null) return false;

        String current = s;
        boolean changed = true;

        while (changed) {
            changed = false;
            // Try to remove the first adjacent matching pair we can find.
            String next = current
                    .replace("()", "")
                    .replace("[]", "")
                    .replace("{}", "");
            if (!next.equals(current)) {
                current = next;
                changed = true;
            }
        }

        // Valid only if nothing is left over.
        return current.isEmpty();
    }

    // ---------------------------------------------------------------------
    // APPROACH 2 - OPTIMIZED (STACK)
    // ---------------------------------------------------------------------
    // Idea: walk the string once.
    //   - Opening bracket -> push it, we owe a matching close later.
    //   - Closing bracket -> the TOP of the stack must be its partner.
    //                        If the stack is empty, or the top does not match,
    //                        the string is invalid immediately.
    // At the end the stack must be empty (no unclosed brackets remain).
    //
    // A stack is the natural fit because brackets close in
    // Last-In-First-Out order: the most recently opened one closes first.
    public static boolean isValid(String s) {
        if (s == null) return false;

        // Maps each CLOSING bracket to its expected OPENING partner.
        Map<Character, Character> pairs = new HashMap<>();
        pairs.put(')', '(');
        pairs.put(']', '[');
        pairs.put('}', '{');

        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(' || c == '[' || c == '{') {
                // Opening bracket: remember it.
                stack.push(c);
            } else if (pairs.containsKey(c)) {
                // Closing bracket: it must cancel the most recent opening one.
                if (stack.isEmpty()) {
                    return false;
                }
                // Unbox to primitive char BEFORE comparing. Comparing two
                // Character objects with != would compare references, not values.
                char open = stack.pop();
                if (open != pairs.get(c)) {
                    return false;
                }
            }
            // Any other character is ignored (problem guarantees bracket-only input).
        }

        // Leftover opening brackets mean something was never closed.
        return stack.isEmpty();
    }

    // ---------------------------------------------------------------------
    // DEMO
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        String[] tests = {
                "()",
                "()[]{}",
                "(]",
                "([)]",
                "{[]}",
                "(((",
                "",
                "{[()()]()}"
        };

        System.out.println("Input            | Brute | Stack");
        System.out.println("-----------------+-------+------");
        for (String t : tests) {
            String shown = t.isEmpty() ? "(empty)" : t;
            System.out.printf("%-16s | %-5s | %-5s%n",
                    shown,
                    isValidBruteForce(t),
                    isValid(t));
        }

        // Expected output:
        // ()          -> true    ()[]{}     -> true
        // (]          -> false   ([)]       -> false
        // {[]}        -> true    (((        -> false
        // (empty)     -> true    {[()()]()} -> true
    }
}
