"""Day 8 - Queues - Implement Queue Using Stacks.

Problem
-------
Build a FIFO queue using ONLY stack operations (push, pop, top, is-empty):

    push(x)  - add x to the back of the queue
    pop()    - remove and return the front element
    peek()   - return the front element without removing it
    empty()  - is the queue empty?

A stack is LIFO, a queue is FIFO. Pouring one stack into another reverses
the order; doing that once is precisely the conversion we need.

Two implementations:
    CostlyPushQueue - push O(n), pop/peek O(1)
    TwoStackQueue   - push O(1), pop/peek amortised O(1)   <-- preferred

In Python a plain ``list`` is a perfectly good stack: ``append`` pushes and
``pop()`` pops from the same end, both in O(1).
"""

from __future__ import annotations

from typing import List


class CostlyPushQueue:
    """Brute force: keep the stack permanently in queue order.

    Every push dumps the whole stack into a helper, inserts the newcomer at
    the bottom, and pours everything back. That makes push O(n) but leaves
    the front of the queue sitting on top, so pop and peek are O(1).
    """

    def __init__(self) -> None:
        self._main: List[int] = []
        self._helper: List[int] = []

    def push(self, x: int) -> None:
        """Add ``x`` to the back of the queue. O(n)."""
        while self._main:
            self._helper.append(self._main.pop())
        self._main.append(x)          # newcomer lands deepest
        while self._helper:
            self._main.append(self._helper.pop())

    def pop(self) -> int:
        """Remove and return the front element. O(1)."""
        if not self._main:
            raise IndexError("pop from an empty queue")
        return self._main.pop()

    def peek(self) -> int:
        """Return the front element without removing it. O(1)."""
        if not self._main:
            raise IndexError("peek from an empty queue")
        return self._main[-1]

    def empty(self) -> bool:
        return not self._main

    def __len__(self) -> int:
        return len(self._main)


class TwoStackQueue:
    """Optimised: two stacks with a lazy, one-time transfer.

    ``_in``  holds recent arrivals, newest on top.
    ``_out`` holds already-reversed elements, so the QUEUE FRONT is on top.

    The rule that makes it correct: only refill ``_out`` when it is completely
    empty. Refilling early would interleave older and newer elements and break
    FIFO order.

    Because every element moves from ``_in`` to ``_out`` at most once in its
    lifetime, a sequence of n operations costs O(n) in total, i.e. amortised
    O(1) per operation - even though one individual pop can cost O(n).
    """

    def __init__(self) -> None:
        self._in: List[int] = []
        self._out: List[int] = []

    def push(self, x: int) -> None:
        """Add ``x`` to the back of the queue. Always O(1)."""
        self._in.append(x)

    def pop(self) -> int:
        """Remove and return the front element. Amortised O(1)."""
        self._shift_if_needed()
        return self._out.pop()

    def peek(self) -> int:
        """Return the front element without removing it. Amortised O(1)."""
        self._shift_if_needed()
        return self._out[-1]

    def empty(self) -> bool:
        return not self._in and not self._out

    def _shift_if_needed(self) -> None:
        """Pour ``_in`` into ``_out`` only once ``_out`` has run dry."""
        if not self._out:
            if not self._in:
                raise IndexError("operation on an empty queue")
            while self._in:
                self._out.append(self._in.pop())

    def __len__(self) -> int:
        return len(self._in) + len(self._out)

    def __repr__(self) -> str:
        # _out top-to-bottom is front-to-back; _in bottom-to-top continues it
        order = list(reversed(self._out)) + list(self._in)
        return f"front {order} back"


def _demo() -> None:
    print("=== Approach 2: Two Stacks (amortised O(1)) ===")
    q = TwoStackQueue()

    q.push(1)
    q.push(2)
    q.push(3)
    print("after push 1,2,3 ->", q)        # front [1, 2, 3] back

    print("peek() =", q.peek())            # 1
    print("pop()  =", q.pop())             # 1
    print("state  ->", q)                  # front [2, 3] back

    q.push(4)                              # arrives behind 3
    print("after push 4 ->", q)            # front [2, 3, 4] back

    print("pop()  =", q.pop())             # 2
    print("pop()  =", q.pop())             # 3
    print("pop()  =", q.pop())             # 4
    print("empty? =", q.empty())           # True

    print()
    print("=== Approach 1: Costly Push (O(n) push) ===")
    slow = CostlyPushQueue()
    for value in (10, 20, 30):
        slow.push(value)
    print("peek() =", slow.peek())         # 10
    print("pop()  =", slow.pop())          # 10
    print("pop()  =", slow.pop())          # 20
    slow.push(40)
    print("pop()  =", slow.pop())          # 30
    print("pop()  =", slow.pop())          # 40
    print("empty? =", slow.empty())        # True

    print()
    print("=== Edge case: popping an empty queue ===")
    try:
        TwoStackQueue().pop()
    except IndexError as exc:
        print("caught as expected ->", exc)


if __name__ == "__main__":
    _demo()
