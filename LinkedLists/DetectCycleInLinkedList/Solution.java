import java.util.HashSet;
import java.util.Set;

/**
 * Day 10 — Linked Lists — Detect Cycle in a Linked List
 *
 * Given the head of a singly linked list, determine whether the list
 * contains a cycle (some node's "next" pointer eventually points back
 * to a node earlier in the list, so traversal never reaches null).
 *
 * Two approaches are shown:
 *   1. Brute force  — remember every node visited in a HashSet, and if
 *      we ever revisit a node we've already seen, there's a cycle.
 *   2. Optimized    — Floyd's Cycle Detection Algorithm ("Tortoise and
 *      Hare"): move one pointer one step at a time and another pointer
 *      two steps at a time. If there's a cycle, the fast pointer will
 *      eventually lap the slow pointer and they'll meet.
 */
public class Solution {

    // Simple singly linked list node.
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) {
            this.val = val;
        }
    }

    /**
     * Brute force: track visited nodes in a HashSet.
     * Time:  O(n)  — visit each node at most once.
     * Space: O(n)  — set can hold up to n node references.
     */
    public static boolean hasCycleBruteForce(ListNode head) {
        Set<ListNode> visited = new HashSet<>();
        ListNode current = head;

        while (current != null) {
            if (visited.contains(current)) {
                return true; // we've been here before -> cycle
            }
            visited.add(current);
            current = current.next;
        }
        return false; // reached the end -> no cycle
    }

    /**
     * Optimized: Floyd's Cycle Detection Algorithm (Tortoise and Hare).
     * Time:  O(n)  — fast pointer traverses the list at most twice.
     * Space: O(1)  — only two extra pointers, no auxiliary structure.
     */
    public static boolean hasCycleFloyd(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;          // moves 1 step
            fast = fast.next.next;     // moves 2 steps

            if (slow == fast) {
                return true; // pointers met -> cycle
            }
        }
        return false; // fast reached the end -> no cycle
    }

    // Helper to build a list from an array, optionally linking the
    // last node back to the node at `cyclePos` (use -1 for no cycle).
    private static ListNode buildList(int[] values, int cyclePos) {
        ListNode head = null;
        ListNode tail = null;
        ListNode cycleEntry = null;

        for (int i = 0; i < values.length; i++) {
            ListNode node = new ListNode(values[i]);
            if (i == cyclePos) {
                cycleEntry = node;
            }
            if (head == null) {
                head = node;
            } else {
                tail.next = node;
            }
            tail = node;
        }

        if (cycleEntry != null) {
            tail.next = cycleEntry; // wire the tail back into the list
        }
        return head;
    }

    public static void main(String[] args) {
        // Example 1: 3 -> 2 -> 0 -> -4 -> back to node at index 1 (value 2)
        ListNode cyclic = buildList(new int[]{3, 2, 0, -4}, 1);
        System.out.println("Cyclic list:");
        System.out.println("  Brute force -> " + hasCycleBruteForce(cyclic));
        System.out.println("  Floyd's     -> " + hasCycleFloyd(cyclic));

        // Example 2: 1 -> 2 -> 3 -> null (no cycle)
        ListNode acyclic = buildList(new int[]{1, 2, 3}, -1);
        System.out.println("Acyclic list:");
        System.out.println("  Brute force -> " + hasCycleBruteForce(acyclic));
        System.out.println("  Floyd's     -> " + hasCycleFloyd(acyclic));
    }
}
