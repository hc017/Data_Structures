/*
 * Topic     : Sliding Window
 * Question  : Longest Substring Without Repeating Characters
 * Day       : 2026-07-27
 *
 * Problem:
 * Given a string s, find the length of the longest substring that does
 * not contain any repeating characters.
 *
 * Approach:
 * Brute force (O(n^3)) checks every substring and verifies uniqueness.
 * The optimized approach uses a sliding window with two pointers
 * (left, right) and a set/map of characters currently inside the
 * window. When a duplicate is seen, the left edge is pulled forward
 * until the duplicate is removed, giving O(n) time, O(min(n, charset))
 * space.
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Solution {

    // Optimized: sliding window with HashMap (char -> last seen index)
    // Time: O(n)   Space: O(min(n, charset size))
    public static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> lastSeen = new HashMap<>();
        int maxLen = 0;
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            if (lastSeen.containsKey(c) && lastSeen.get(c) >= left) {
                // duplicate found inside current window -> shrink from left
                left = lastSeen.get(c) + 1;
            }

            lastSeen.put(c, right);
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }

    // Brute force reference implementation -> O(n^3) time, O(min(n, charset)) space
    public static int lengthOfLongestSubstringBruteForce(String s) {
        int n = s.length();
        int maxLen = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (isUnique(s, i, j)) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }
        return maxLen;
    }

    private static boolean isUnique(String s, int start, int end) {
        Set<Character> chars = new HashSet<>();
        for (int k = start; k <= end; k++) {
            if (!chars.add(s.charAt(k))) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String s = "abcabcbb";

        int result = lengthOfLongestSubstring(s);
        System.out.println("Input : \"" + s + "\"");
        System.out.println("Longest substring length without repeats: " + result);

        String[] more = { "bbbbb", "pwwkew", "", "dvdf" };
        for (String test : more) {
            System.out.println("\"" + test + "\" -> " + lengthOfLongestSubstring(test));
        }
    }
}
