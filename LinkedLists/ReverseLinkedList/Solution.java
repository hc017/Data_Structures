import java.util.ArrayList;
import java.util.List;

/**
 * Day 9 - Linked Lists - Reverse a Linked List
 *
 * Problem:
 *   Given the head of a singly linked list, reverse the list and return the
 *   new head. The reversal must be done by re-pointing the existing `next`
 *   references, not by copying values around (that is the whole exercise).
 *
 *   1 -> 2 -> 3 -> 4 -> 5 -> null     becomes     5 -> 4 -> 3 -> 2 -> 1 -> null
 *
 * Approaches implemented here:
 *   1. Brute force  : copy every value into a list, walk it backwards, rebuild.
 *                     O(n) time, O(n) extra space.
 *   2. Optimised    : iterative three-pointer flip in one pass.
 *                     O(n) time, O(1) extra space.
 *   3. Recursive    : same idea expressed recursively.
 *                     O(n) time, O(n) stack space.
 */
public class Solution {

    /** Minimal singly linked list node. */
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    // ------------------------------------------------------------------
    // 1. BRUTE FORCE - collect values, rebuild the list in reverse order
    // ------------------------------------------------------------------
    // Honest but wasteful: it allocates a whole second structure and a whole
    // new chain of nodes, so the original list is left untouched and we pay
    // O(n) extra memory for the privilege.
    static ListNode reverseBruteForce(ListNode head) {
        List<Integer> values = new ArrayList<>();

        // Pass 1: read every value out of the list.
        for (ListNode cur = head; cur != null; cur = cur.next) {
            values.add(cur.val);
        }

        // Pass 2: build a brand new list, walking the values backwards.
        ListNode newHead = null;
        for (int i = 0; i < values.size(); i++) {
            // Prepending each value in original order naturally reverses it.
            newHead = new ListNode(values.get(i), newHead);
        }
        return newHead;
    }

    // ------------------------------------------------------------------
    // 2. OPTIMISED - iterative three-pointer reversal (the one to know)
    // ------------------------------------------------------------------
    // Invariant maintained on every loop iteration:
    //   `prev` is the head of the already-reversed prefix,
    //   `cur`  is the head of the not-yet-touched suffix.
    // We move exactly one node from the suffix to the prefix per step.
    static ListNode reverseIterative(ListNode head) {
        ListNode prev = null;   // reversed part behind us (starts empty)
        ListNode cur = head;    // untouched part ahead of us

        while (cur != null) {
            ListNode next = cur.next; // 1. save the rest, or we lose it forever
            cur.next = prev;          // 2. flip this node's arrow backwards
            prev = cur;               // 3. the reversed prefix grew by one
            cur = next;               // 4. step into the saved remainder
        }

        // cur is null, so prev is sitting on the last original node = new head.
        return prev;
    }

    // ------------------------------------------------------------------
    // 3. RECURSIVE - same flip, with the call stack holding the pointers
    // ------------------------------------------------------------------
    static ListNode reverseRecursive(ListNode head) {
        // Base case: empty list, or a single node that is already reversed.
        if (head == null || head.next == null) {
            return head;
        }

        // Trust the recursion: everything after `head` comes back reversed,
        // and newHead is the final node of the original list.
        ListNode newHead = reverseRecursive(head.next);

        // head.next is still the node that now sits at the TAIL of the
        // reversed suffix, so point it back at head and cut head loose.
        head.next.next = head;
        head.next = null;

        return newHead;
    }

    // ------------------------------------------------------------------
    // Helpers for the demo
    // ------------------------------------------------------------------
    static ListNode build(int... values) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        for (int v : values) {
            tail.next = new ListNode(v);
            tail = tail.next;
        }
        return dummy.next;
    }

    static String render(ListNode head) {
        StringBuilder sb = new StringBuilder();
        for (ListNode cur = head; cur != null; cur = cur.next) {
            sb.append(cur.val).append(" -> ");
        }
        return sb.append("null").toString();
    }

    // ------------------------------------------------------------------
    // Demo
    // ------------------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("=== Day 9: Reverse a Linked List ===\n");

        ListNode a = build(1, 2, 3, 4, 5);
        System.out.println("Original          : " + render(a));
        System.out.println("Brute force       : " + render(reverseBruteForce(a)));
        System.out.println("Original intact   : " + render(a) + "   (brute force copies)");

        ListNode b = build(1, 2, 3, 4, 5);
        System.out.println("\nIterative flip    : " + render(reverseIterative(b)));

        ListNode c = build(1, 2, 3, 4, 5);
        System.out.println("Recursive flip    : " + render(reverseRecursive(c)));

        System.out.println("\n--- Edge cases ---");
        System.out.println("Empty list        : " + render(reverseIterative(null)));
        System.out.println("Single node       : " + render(reverseIterative(build(42))));
        System.out.println("Two nodes         : " + render(reverseIterative(build(7, 9))));
        System.out.println("Duplicates        : " + render(reverseIterative(build(3, 3, 1, 3))));

        // Reversing twice must return the original ordering.
        ListNode twice = reverseIterative(reverseIterative(build(1, 2, 3, 4, 5)));
        System.out.println("Reversed twice    : " + render(twice) + "   (should match original)");
    }
}
