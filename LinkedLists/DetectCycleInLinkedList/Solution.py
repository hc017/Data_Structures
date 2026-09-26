"""
Day 10 -- Linked Lists -- Detect Cycle in a Linked List

Given the head of a singly linked list, determine whether the list
contains a cycle (some node's "next" pointer eventually points back
to a node earlier in the list, so traversal never reaches None).

Two approaches are shown:
  1. Brute force -- remember every node visited in a set, and if we
     ever revisit a node we've already seen, there's a cycle.
  2. Optimized   -- Floyd's Cycle Detection Algorithm ("Tortoise and
     Hare"): move one pointer one step at a time and another pointer
     two steps at a time. If there's a cycle, the fast pointer will
     eventually lap the slow pointer and they'll meet.
"""

from __future__ import annotations

from typing import Optional


class ListNode:
    """A single node in a singly linked list."""

    def __init__(self, val: int) -> None:
        self.val: int = val
        self.next: Optional["ListNode"] = None


def has_cycle_brute_force(head: Optional[ListNode]) -> bool:
    """Detect a cycle using a hash set of visited nodes.

    Time:  O(n)  -- visit each node at most once.
    Space: O(n)  -- set can hold up to n node references.
    """
    visited: set[ListNode] = set()
    current = head

    while current is not None:
        if current in visited:
            return True  # we've been here before -> cycle
        visited.add(current)
        current = current.next

    return False  # reached the end -> no cycle


def has_cycle_floyd(head: Optional[ListNode]) -> bool:
    """Detect a cycle using Floyd's Tortoise and Hare algorithm.

    Time:  O(n)  -- fast pointer traverses the list at most twice.
    Space: O(1)  -- only two extra pointers, no auxiliary structure.
    """
    slow = head
    fast = head

    while fast is not None and fast.next is not None:
        slow = slow.next              # moves 1 step
        fast = fast.next.next         # moves 2 steps

        if slow is fast:
            return True  # pointers met -> cycle

    return False  # fast reached the end -> no cycle


def _build_list(values: list[int], cycle_pos: int) -> Optional[ListNode]:
    """Build a list from `values`, optionally linking the last node
    back to the node at index `cycle_pos` (use -1 for no cycle)."""
    head: Optional[ListNode] = None
    tail: Optional[ListNode] = None
    cycle_entry: Optional[ListNode] = None

    for i, v in enumerate(values):
        node = ListNode(v)
        if i == cycle_pos:
            cycle_entry = node
        if head is None:
            head = node
        else:
            tail.next = node  # type: ignore[union-attr]
        tail = node

    if cycle_entry is not None and tail is not None:
        tail.next = cycle_entry  # wire the tail back into the list

    return head


if __name__ == "__main__":
    # Example 1: 3 -> 2 -> 0 -> -4 -> back to node at index 1 (value 2)
    cyclic = _build_list([3, 2, 0, -4], cycle_pos=1)
    print("Cyclic list:")
    print("  Brute force ->", has_cycle_brute_force(cyclic))
    print("  Floyd's     ->", has_cycle_floyd(cyclic))

    # Example 2: 1 -> 2 -> 3 -> None (no cycle)
    acyclic = _build_list([1, 2, 3], cycle_pos=-1)
    print("Acyclic list:")
    print("  Brute force ->", has_cycle_brute_force(acyclic))
    print("  Floyd's     ->", has_cycle_floyd(acyclic))
