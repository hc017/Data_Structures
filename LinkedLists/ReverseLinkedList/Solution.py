"""
Day 9 - Linked Lists - Reverse a Linked List

Problem:
    Given the head of a singly linked list, reverse it and return the new head.
    The reversal must be done by re-pointing the existing `next` references,
    not by copying values around -- that is the whole point of the exercise.

        1 -> 2 -> 3 -> 4 -> 5 -> None   becomes   5 -> 4 -> 3 -> 2 -> 1 -> None

Approaches:
    1. Brute force : copy values out, rebuild a new list in reverse.
                     O(n) time, O(n) extra space.
    2. Optimised   : iterative three-pointer flip in a single pass.
                     O(n) time, O(1) extra space.
    3. Recursive   : the same flip expressed recursively.
                     O(n) time, O(n) stack space.
"""

from __future__ import annotations

from typing import Iterable, List, Optional


class ListNode:
    """Minimal singly linked list node."""

    __slots__ = ("val", "next")

    def __init__(self, val: int = 0, next: Optional["ListNode"] = None) -> None:
        self.val = val
        self.next = next

    def __repr__(self) -> str:  # pragma: no cover - debug convenience only
        return f"ListNode({self.val})"


# ----------------------------------------------------------------------
# 1. BRUTE FORCE - read every value, then rebuild the chain backwards
# ----------------------------------------------------------------------
def reverse_brute_force(head: Optional[ListNode]) -> Optional[ListNode]:
    """Reverse by copying values into a Python list and rebuilding.

    Honest but wasteful: it allocates a second container AND a fresh set of
    nodes, so it costs O(n) extra memory. The original list is left untouched,
    which is occasionally useful but is not what the problem asks for.

    Time:  O(n)
    Space: O(n)
    """
    values: List[int] = []
    cur = head
    while cur is not None:
        values.append(cur.val)
        cur = cur.next

    new_head: Optional[ListNode] = None
    for value in values:
        # Prepending each value in original order naturally reverses the list.
        new_head = ListNode(value, new_head)
    return new_head


# ----------------------------------------------------------------------
# 2. OPTIMISED - iterative three-pointer reversal (the one to know)
# ----------------------------------------------------------------------
def reverse_iterative(head: Optional[ListNode]) -> Optional[ListNode]:
    """Reverse in place with three pointers and one pass.

    Loop invariant, true at the top of every iteration:
        `prev` is the head of the already-reversed prefix
        `cur`  is the head of the not-yet-touched suffix
    Each step moves exactly one node from the suffix to the prefix.

    Time:  O(n)
    Space: O(1)
    """
    prev: Optional[ListNode] = None
    cur = head

    while cur is not None:
        nxt = cur.next   # 1. save the remainder, or we lose it forever
        cur.next = prev  # 2. flip this node's arrow backwards
        prev = cur       # 3. the reversed prefix grew by one node
        cur = nxt        # 4. step into the saved remainder

    # cur is None, so prev sits on the last original node: the new head.
    return prev


# ----------------------------------------------------------------------
# 3. RECURSIVE - same flip, with the call stack holding the pointers
# ----------------------------------------------------------------------
def reverse_recursive(head: Optional[ListNode]) -> Optional[ListNode]:
    """Reverse recursively.

    Elegant, but each pending frame costs memory, so a list of a million nodes
    will blow Python's recursion limit. Prefer the iterative version in anger.

    Time:  O(n)
    Space: O(n) call stack
    """
    # Base case: empty list, or a single node that is already reversed.
    if head is None or head.next is None:
        return head

    # Trust the recursion: everything after `head` comes back reversed, and
    # new_head is the final node of the original list.
    new_head = reverse_recursive(head.next)

    # head.next is still the node that now sits at the TAIL of the reversed
    # suffix, so point it back at head and cut head loose.
    head.next.next = head
    head.next = None

    return new_head


# ----------------------------------------------------------------------
# Helpers
# ----------------------------------------------------------------------
def build(values: Iterable[int]) -> Optional[ListNode]:
    """Build a linked list from an iterable of ints."""
    dummy = ListNode()
    tail = dummy
    for value in values:
        tail.next = ListNode(value)
        tail = tail.next
    return dummy.next


def render(head: Optional[ListNode]) -> str:
    """Render a linked list as `1 -> 2 -> None`."""
    parts: List[str] = []
    cur = head
    while cur is not None:
        parts.append(str(cur.val))
        cur = cur.next
    parts.append("None")
    return " -> ".join(parts)


# ----------------------------------------------------------------------
# Demo
# ----------------------------------------------------------------------
if __name__ == "__main__":
    print("=== Day 9: Reverse a Linked List ===\n")

    a = build([1, 2, 3, 4, 5])
    print("Original          :", render(a))
    print("Brute force       :", render(reverse_brute_force(a)))
    print("Original intact   :", render(a), "  (brute force copies)")

    b = build([1, 2, 3, 4, 5])
    print("\nIterative flip    :", render(reverse_iterative(b)))

    c = build([1, 2, 3, 4, 5])
    print("Recursive flip    :", render(reverse_recursive(c)))

    print("\n--- Edge cases ---")
    print("Empty list        :", render(reverse_iterative(None)))
    print("Single node       :", render(reverse_iterative(build([42]))))
    print("Two nodes         :", render(reverse_iterative(build([7, 9]))))
    print("Duplicates        :", render(reverse_iterative(build([3, 3, 1, 3]))))

    # Reversing twice must give back the original ordering.
    twice = reverse_iterative(reverse_iterative(build([1, 2, 3, 4, 5])))
    print("Reversed twice    :", render(twice), "  (should match original)")

    # Quick self-check so the demo doubles as a test.
    assert render(reverse_iterative(build([1, 2, 3]))) == "3 -> 2 -> 1 -> None"
    assert render(reverse_recursive(build([1, 2, 3]))) == "3 -> 2 -> 1 -> None"
    assert render(reverse_brute_force(build([1, 2, 3]))) == "3 -> 2 -> 1 -> None"
    assert reverse_iterative(None) is None
    print("\nAll self-checks passed.")
