"""
Day 6 - Stacks: Valid Parentheses

Given a string containing only the characters '(', ')', '{', '}', '[' and ']',
determine whether the string is valid.

A string is valid when:
    1. Every open bracket is closed by a bracket of the SAME type.
    2. Brackets are closed in the correct ORDER (most recent open closes first).
    3. No closing bracket appears without a matching open bracket before it.

Approaches:
    - Brute force : repeatedly strip adjacent matching pairs -> O(n^2) time, O(n) space
    - Optimized   : single pass with a stack                 -> O(n)   time, O(n) space
"""

from typing import Dict, List


# ---------------------------------------------------------------------------
# APPROACH 1 - BRUTE FORCE
# ---------------------------------------------------------------------------
def is_valid_brute_force(s: str) -> bool:
    """Remove adjacent matching pairs until nothing changes.

    A valid string always contains at least one adjacent pair such as
    "()", "[]" or "{}". Deleting those pairs shrinks the string. If the
    string collapses to empty, the original was balanced.

    Time:  O(n^2)  - up to n/2 passes, each pass scanning O(n) characters
    Space: O(n)    - a new string is built on each pass
    """
    if s is None:
        return False

    current = s
    while True:
        nxt = current.replace("()", "").replace("[]", "").replace("{}", "")
        if nxt == current:
            break
        current = nxt

    return current == ""


# ---------------------------------------------------------------------------
# APPROACH 2 - OPTIMIZED (STACK)
# ---------------------------------------------------------------------------
def is_valid(s: str) -> bool:
    """Validate bracket nesting in a single pass using a stack.

    Walk the string once:
        - Opening bracket -> push it; we owe a matching close later.
        - Closing bracket -> the top of the stack must be its partner.
          If the stack is empty or the top does not match, fail immediately.
    At the end the stack must be empty, otherwise something was never closed.

    Time:  O(n)  - each character is pushed and popped at most once
    Space: O(n)  - worst case all characters are opening brackets

    >>> is_valid("{[()()]()}")
    True
    >>> is_valid("([)]")
    False
    """
    if s is None:
        return False

    # Closing bracket -> expected opening partner.
    pairs: Dict[str, str] = {")": "(", "]": "[", "}": "{"}
    stack: List[str] = []

    for ch in s:
        if ch in "([{":
            stack.append(ch)
        elif ch in pairs:
            # Must cancel the most recently opened bracket.
            if not stack or stack.pop() != pairs[ch]:
                return False
        # Any other character is ignored.

    return not stack


# ---------------------------------------------------------------------------
# DEMO
# ---------------------------------------------------------------------------
if __name__ == "__main__":
    tests = [
        "()",
        "()[]{}",
        "(]",
        "([)]",
        "{[]}",
        "(((",
        "",
        "{[()()]()}",
    ]

    print(f"{'Input':<14} | {'Brute':<6} | {'Stack':<6}")
    print("-" * 34)
    for t in tests:
        shown = t if t else "(empty)"
        print(f"{shown:<14} | {str(is_valid_brute_force(t)):<6} | {str(is_valid(t)):<6}")

    # Expected:
    #   ()          -> True     ()[]{}      -> True
    #   (]          -> False    ([)]        -> False
    #   {[]}        -> True     (((         -> False
    #   (empty)     -> True     {[()()]()}  -> True
