import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

/**
 * Day 8 - Queues: Implement Queue Using Stacks
 *
 * Problem:
 *   Build a FIFO queue (first-in, first-out) using ONLY stack operations:
 *   push, pop, peek/top and isEmpty. Support:
 *     push(x)  - add x to the back of the queue
 *     pop()    - remove and return the front element
 *     peek()   - return the front element without removing it
 *     empty()  - is the queue empty?
 *
 * A stack is LIFO. A queue is FIFO. Pouring one stack into another flips the
 * order, and that single flip is exactly the conversion we need.
 *
 * Two approaches are implemented below:
 *   1. CostlyPushQueue  - push is O(n), pop/peek are O(1)
 *   2. TwoStackQueue    - push is O(1), pop/peek are AMORTISED O(1)  <-- preferred
 */
public class Solution {

    /* ------------------------------------------------------------------
     * APPROACH 1 (brute force): keep the queue order inside a single stack
     * ------------------------------------------------------------------
     * On every push we dump everything into a helper stack, drop the new
     * element at the bottom, then pour everything back. The main stack is
     * therefore always stored front-of-queue-on-top, so pop and peek are
     * trivial. The cost is that every single push touches every element.
     *
     * push : O(n)
     * pop  : O(1)
     * peek : O(1)
     * space: O(n)
     */
    static class CostlyPushQueue {
        private final Deque<Integer> main = new ArrayDeque<>();
        private final Deque<Integer> helper = new ArrayDeque<>();

        void push(int x) {
            // 1. empty the main stack into the helper
            while (!main.isEmpty()) {
                helper.push(main.pop());
            }
            // 2. the new element goes in first, so it ends up deepest
            main.push(x);
            // 3. pour everything back on top of it
            while (!helper.isEmpty()) {
                main.push(helper.pop());
            }
        }

        int pop() {
            if (main.isEmpty()) throw new NoSuchElementException("queue is empty");
            return main.pop();
        }

        int peek() {
            if (main.isEmpty()) throw new NoSuchElementException("queue is empty");
            return main.peek();
        }

        boolean empty() {
            return main.isEmpty();
        }

        int size() {
            return main.size();
        }
    }

    /* ------------------------------------------------------------------
     * APPROACH 2 (optimized): two stacks, transfer lazily
     * ------------------------------------------------------------------
     * inStack  - everything that has arrived recently, newest on top
     * outStack - elements already reversed, so the QUEUE FRONT is on top
     *
     * push  -> always onto inStack.                              O(1)
     * pop   -> if outStack is empty, pour ALL of inStack into it
     *          (this reverses the order exactly once), then pop.  amortised O(1)
     *
     * The key rule: never transfer while outStack still holds items, or new
     * arrivals would cut in front of older ones and FIFO order would break.
     *
     * Each element is moved between the stacks at most once in its lifetime,
     * so n operations cost O(n) in total -> O(1) amortised per operation,
     * even though one individual pop can cost O(n).
     */
    static class TwoStackQueue {
        private final Deque<Integer> inStack = new ArrayDeque<>();
        private final Deque<Integer> outStack = new ArrayDeque<>();

        /** Add to the back of the queue. Always O(1). */
        void push(int x) {
            inStack.push(x);
        }

        /** Remove and return the front of the queue. Amortised O(1). */
        int pop() {
            shiftIfNeeded();
            return outStack.pop();
        }

        /** Return the front of the queue without removing it. Amortised O(1). */
        int peek() {
            shiftIfNeeded();
            return outStack.peek();
        }

        boolean empty() {
            return inStack.isEmpty() && outStack.isEmpty();
        }

        int size() {
            return inStack.size() + outStack.size();
        }

        /**
         * Only refill outStack when it has run completely dry.
         * Doing it any earlier would interleave old and new elements.
         */
        private void shiftIfNeeded() {
            if (outStack.isEmpty()) {
                if (inStack.isEmpty()) {
                    throw new NoSuchElementException("queue is empty");
                }
                while (!inStack.isEmpty()) {
                    outStack.push(inStack.pop());
                }
            }
        }

        /** Debug helper: front-to-back view of the queue. */
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("front [");
            boolean first = true;
            // iterating an ArrayDeque used as a stack yields top-to-bottom,
            // and outStack top-to-bottom is already front-to-back
            for (Integer v : outStack) {
                if (!first) sb.append(", ");
                sb.append(v);
                first = false;
            }
            // inStack bottom-to-top continues the queue, so walk it in reverse
            Integer[] pending = inStack.toArray(new Integer[0]); // top-to-bottom
            for (int i = pending.length - 1; i >= 0; i--) {
                if (!first) sb.append(", ");
                sb.append(pending[i]);
                first = false;
            }
            return sb.append("] back").toString();
        }
    }

    // ---------------------------------------------------------------------
    // DEMO
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("=== Approach 2: Two Stacks (amortised O(1)) ===");
        TwoStackQueue q = new TwoStackQueue();

        q.push(1);
        q.push(2);
        q.push(3);
        System.out.println("after push 1,2,3 -> " + q);      // front [1, 2, 3] back

        System.out.println("peek() = " + q.peek());           // 1
        System.out.println("pop()  = " + q.pop());            // 1
        System.out.println("state  -> " + q);                 // front [2, 3] back

        q.push(4);                                            // arrives behind 3
        System.out.println("after push 4 -> " + q);           // front [2, 3, 4] back

        System.out.println("pop()  = " + q.pop());            // 2
        System.out.println("pop()  = " + q.pop());            // 3
        System.out.println("pop()  = " + q.pop());            // 4
        System.out.println("empty? = " + q.empty());          // true

        System.out.println();
        System.out.println("=== Approach 1: Costly Push (O(n) push) ===");
        CostlyPushQueue slow = new CostlyPushQueue();
        slow.push(10);
        slow.push(20);
        slow.push(30);
        System.out.println("peek() = " + slow.peek());        // 10
        System.out.println("pop()  = " + slow.pop());         // 10
        System.out.println("pop()  = " + slow.pop());         // 20
        slow.push(40);
        System.out.println("pop()  = " + slow.pop());         // 30
        System.out.println("pop()  = " + slow.pop());         // 40
        System.out.println("empty? = " + slow.empty());       // true

        System.out.println();
        System.out.println("=== Interleaved push/pop (the case that catches bugs) ===");
        TwoStackQueue mix = new TwoStackQueue();
        mix.push(1);
        mix.push(2);
        System.out.println("pop()  = " + mix.pop());          // 1
        mix.push(3);
        System.out.println("pop()  = " + mix.pop());          // 2  (NOT 3)
        System.out.println("pop()  = " + mix.pop());          // 3

        System.out.println();
        System.out.println("=== Edge case: popping an empty queue ===");
        try {
            new TwoStackQueue().pop();
        } catch (NoSuchElementException e) {
            System.out.println("caught as expected -> " + e.getMessage());
        }
    }
}
