import java.util.*;

/**
 * Day 3 - Two Pointers
 * Problem: Container With Most Water
 *
 * You are given an array `height` where height[i] is the height of a
 * vertical line drawn at position i. Two lines, together with the x-axis,
 * form a container. Find two lines that together with the x-axis form a
 * container that holds the most water.
 *
 * Water held by lines at (i, j) = min(height[i], height[j]) * (j - i)
 * (bounded by the shorter wall, since water spills over the shorter side).
 */
public class Solution {

    /**
     * Brute force: try every pair of lines and keep the best area.
     * Time:  O(n^2)  - nested loop over all pairs
     * Space: O(1)
     */
    public static int maxAreaBruteForce(int[] height) {
        int best = 0;
        for (int i = 0; i < height.length; i++) {
            for (int j = i + 1; j < height.length; j++) {
                int area = Math.min(height[i], height[j]) * (j - i);
                best = Math.max(best, area);
            }
        }
        return best;
    }

    /**
     * Optimized: two pointers starting at both ends, moving the shorter
     * wall inward each step. Moving the taller wall can never help, because
     * width only shrinks and height is still capped by the shorter side.
     * Time:  O(n)  - single pass, pointers move toward each other
     * Space: O(1)
     */
    public static int maxAreaTwoPointers(int[] height) {
        int left = 0, right = height.length - 1;
        int best = 0;

        while (left < right) {
            int h = Math.min(height[left], height[right]);
            int width = right - left;
            best = Math.max(best, h * width);

            // Move the shorter wall inward - it's the bottleneck.
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return best;
    }

    public static void main(String[] args) {
        int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};

        System.out.println("Input heights: " + Arrays.toString(height));
        System.out.println("Brute force max area:   " + maxAreaBruteForce(height));
        System.out.println("Two pointers max area:  " + maxAreaTwoPointers(height));
        // Expected output: 49 (between index 1, height 8 and index 8, height 7)
    }
}
