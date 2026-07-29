package Hashing.GroupAnagrams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Day 5 - Hashing - Group Anagrams
 *
 * Problem:
 *   Given an array of strings, group together all the strings that are
 *   anagrams of each other (same letters, rearranged).
 *
 *   Input : ["eat", "tea", "tan", "ate", "nat", "bat"]
 *   Output: [["eat", "tea", "ate"], ["tan", "nat"], ["bat"]]
 *
 * Core idea:
 *   Two words are anagrams if and only if they share the same "signature".
 *   A signature can be the sorted letters ("eat" -> "aet") or the count of
 *   each letter ("eat" -> a1e1t1). Words with an identical signature belong
 *   in the same bucket, so a HashMap from signature -> list of words solves it
 *   in a single pass.
 */
public class Solution {

    /* ------------------------------------------------------------------
     * APPROACH 1 - BRUTE FORCE
     * ------------------------------------------------------------------
     * For every word, walk through the groups built so far and check whether
     * it is an anagram of that group's first member. If yes, join that group;
     * otherwise start a new group.
     *
     * Time  : O(n^2 * k log k)  - n words, each compared against up to n groups,
     *                             each comparison sorts strings of length k
     * Space : O(n * k)          - the output itself
     * ------------------------------------------------------------------ */
    public static List<List<String>> groupAnagramsBruteForce(String[] words) {
        List<List<String>> groups = new ArrayList<>();

        for (String word : words) {
            boolean placed = false;

            for (List<String> group : groups) {
                // Compare against any existing member of the group.
                if (isAnagram(word, group.get(0))) {
                    group.add(word);
                    placed = true;
                    break;
                }
            }

            // No matching group found, so this word starts a brand new one.
            if (!placed) {
                List<String> fresh = new ArrayList<>();
                fresh.add(word);
                groups.add(fresh);
            }
        }
        return groups;
    }

    /** Two words are anagrams when their sorted characters match exactly. */
    private static boolean isAnagram(String a, String b) {
        if (a.length() != b.length()) return false;
        char[] x = a.toCharArray();
        char[] y = b.toCharArray();
        Arrays.sort(x);
        Arrays.sort(y);
        return Arrays.equals(x, y);
    }

    /* ------------------------------------------------------------------
     * APPROACH 2 - OPTIMIZED, SORTED-KEY HASHING
     * ------------------------------------------------------------------
     * Build the signature once per word (sort its letters) and use it as a
     * HashMap key. Every word lands in its bucket with one O(1) lookup.
     *
     * Time  : O(n * k log k)  - sorting each of the n words of length k
     * Space : O(n * k)        - map + output
     * ------------------------------------------------------------------ */
    public static List<List<String>> groupAnagramsSorted(String[] words) {
        Map<String, List<String>> buckets = new HashMap<>();

        for (String word : words) {
            char[] letters = word.toCharArray();
            Arrays.sort(letters);            // "eat" and "tea" both become "aet"
            String signature = new String(letters);

            // computeIfAbsent creates the list the first time we see a signature.
            buckets.computeIfAbsent(signature, k -> new ArrayList<>()).add(word);
        }

        return new ArrayList<>(buckets.values());
    }

    /* ------------------------------------------------------------------
     * APPROACH 3 - OPTIMIZED, CHARACTER-COUNT KEY (best for lowercase input)
     * ------------------------------------------------------------------
     * Instead of sorting, count how many times each of the 26 lowercase
     * letters appears and turn that count array into a key such as
     * "1#0#0#0#1#...#1#". This removes the log k sorting factor.
     *
     * Time  : O(n * k)
     * Space : O(n * k)
     * ------------------------------------------------------------------ */
    public static List<List<String>> groupAnagramsCounting(String[] words) {
        Map<String, List<String>> buckets = new HashMap<>();

        for (String word : words) {
            int[] counts = new int[26];
            for (char c : word.toCharArray()) {
                counts[c - 'a']++;
            }

            // Turn the frequency array into a stable, unique string key.
            StringBuilder key = new StringBuilder();
            for (int count : counts) {
                key.append(count).append('#');
            }

            buckets.computeIfAbsent(key.toString(), k -> new ArrayList<>()).add(word);
        }

        return new ArrayList<>(buckets.values());
    }

    /* ------------------------------------------------------------------
     * DEMO
     * ------------------------------------------------------------------ */
    public static void main(String[] args) {
        String[] words = {"eat", "tea", "tan", "ate", "nat", "bat"};

        System.out.println("Input: " + Arrays.toString(words));
        System.out.println();

        System.out.println("Brute force      -> " + groupAnagramsBruteForce(words));
        System.out.println("Sorted-key hash  -> " + groupAnagramsSorted(words));
        System.out.println("Count-key hash   -> " + groupAnagramsCounting(words));
        System.out.println();

        // Edge cases worth checking.
        System.out.println("Empty array      -> " + groupAnagramsSorted(new String[]{}));
        System.out.println("Single word      -> " + groupAnagramsSorted(new String[]{"solo"}));
        System.out.println("All identical    -> " + groupAnagramsSorted(new String[]{"aa", "aa", "aa"}));
        System.out.println("No anagrams      -> " + groupAnagramsSorted(new String[]{"abc", "def", "ghi"}));
        System.out.println("Empty strings    -> " + groupAnagramsSorted(new String[]{"", "", "a"}));
    }
}
