"""
Day 5 - Hashing - Group Anagrams

Problem:
    Given a list of strings, group together all strings that are anagrams of
    each other (same letters, rearranged).

    Input : ["eat", "tea", "tan", "ate", "nat", "bat"]
    Output: [["eat", "tea", "ate"], ["tan", "nat"], ["bat"]]

Core idea:
    Two words are anagrams if and only if they share the same "signature".
    A signature can be the sorted letters ("eat" -> "aet") or a tuple of
    letter counts. Words with an identical signature belong in the same
    bucket, so a dict from signature -> list of words solves it in one pass.
"""

from collections import defaultdict
from typing import Dict, List, Tuple


def group_anagrams_brute_force(words: List[str]) -> List[List[str]]:
    """Brute force: compare each word against a representative of every group.

    For every word, scan the groups built so far and check whether it is an
    anagram of that group's first member. If so, join it; otherwise start a
    new group.

    Time:  O(n^2 * k log k) - n words compared against up to n groups,
                              each comparison sorts strings of length k
    Space: O(n * k)         - the output itself

    Args:
        words: List of strings to group.

    Returns:
        A list of groups, where each group holds mutual anagrams.
    """
    groups: List[List[str]] = []

    for word in words:
        placed = False

        for group in groups:
            # Sorted letters match => the two words are anagrams.
            if sorted(word) == sorted(group[0]):
                group.append(word)
                placed = True
                break

        # No matching group, so this word starts a brand new one.
        if not placed:
            groups.append([word])

    return groups


def group_anagrams_sorted(words: List[str]) -> List[List[str]]:
    """Optimized: use the sorted letters of each word as a dict key.

    Build the signature once per word instead of re-sorting on every
    comparison. Each word then lands in its bucket with one O(1) lookup.

    Time:  O(n * k log k) - sorting each of the n words of length k
    Space: O(n * k)       - dict + output

    Args:
        words: List of strings to group.

    Returns:
        A list of groups, where each group holds mutual anagrams.
    """
    buckets: Dict[str, List[str]] = defaultdict(list)

    for word in words:
        signature = "".join(sorted(word))  # "eat" and "tea" both become "aet"
        buckets[signature].append(word)

    return list(buckets.values())


def group_anagrams_counting(words: List[str]) -> List[List[str]]:
    """Optimized: use a 26-slot letter-count tuple as the key (lowercase a-z).

    Counting letters avoids the log k sorting factor entirely. The tuple is
    hashable, so it works directly as a dict key.

    Time:  O(n * k)
    Space: O(n * k)

    Args:
        words: List of lowercase strings to group.

    Returns:
        A list of groups, where each group holds mutual anagrams.
    """
    buckets: Dict[Tuple[int, ...], List[str]] = defaultdict(list)

    for word in words:
        counts = [0] * 26
        for char in word:
            counts[ord(char) - ord("a")] += 1
        buckets[tuple(counts)].append(word)

    return list(buckets.values())


if __name__ == "__main__":
    sample = ["eat", "tea", "tan", "ate", "nat", "bat"]

    print(f"Input: {sample}")
    print()

    print(f"Brute force      -> {group_anagrams_brute_force(sample)}")
    print(f"Sorted-key hash  -> {group_anagrams_sorted(sample)}")
    print(f"Count-key hash   -> {group_anagrams_counting(sample)}")
    print()

    # Edge cases worth checking.
    print(f"Empty list       -> {group_anagrams_sorted([])}")
    print(f"Single word      -> {group_anagrams_sorted(['solo'])}")
    print(f"All identical    -> {group_anagrams_sorted(['aa', 'aa', 'aa'])}")
    print(f"No anagrams      -> {group_anagrams_sorted(['abc', 'def', 'ghi'])}")
    print(f"Empty strings    -> {group_anagrams_sorted(['', '', 'a'])}")
