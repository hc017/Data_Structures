/**
 * Day 14 - Binary Search: Search in a Sorted Array
 *
 * Problem: Given a sorted array of distinct integers and a target, return the
 * index of the target, or -1 if it is not present.
 *
 * Approaches:
 *   1. Brute force (linear scan)  - O(n) time, O(1) space
 *   2. Binary search (iterative)  - O(log n) time, O(1) space
 *   3. Binary search (recursive)  - O(log n) time, O(log n) stack space
 */
public class Solution {

    // ---------- Approach 1: Brute force ----------
    // Check every element from left to right.
    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    // ---------- Approach 2: Binary search (iterative) ----------
    // Each step throws away half of the remaining search space.
    public static int binarySearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            // low + (high - low) / 2 avoids integer overflow of (low + high)
            int mid = low + (high - low) / 2;

            if (arr[mid] == target) {
                return mid;            // found it
            } else if (arr[mid] < target) {
                low = mid + 1;         // target must be in the right half
            } else {
                high = mid - 1;        // target must be in the left half
            }
        }
        return -1;                     // search space is empty -> not present
    }

    // ---------- Approach 3: Binary search (recursive) ----------
    public static int binarySearchRecursive(int[] arr, int target, int low, int high) {
        if (low > high) {
            return -1;
        }
        int mid = low + (high - low) / 2;
        if (arr[mid] == target) {
            return mid;
        }
        if (arr[mid] < target) {
            return binarySearchRecursive(arr, target, mid + 1, high);
        }
        return binarySearchRecursive(arr, target, low, mid - 1);
    }

    public static void main(String[] args) {
        int[] arr = {2, 5, 8, 12, 16, 23, 38, 56, 72, 91};

        int target = 23;
        System.out.println("Array  : " + java.util.Arrays.toString(arr));
        System.out.println("Target : " + target);
        System.out.println("Linear search      -> index " + linearSearch(arr, target));
        System.out.println("Binary (iterative) -> index " + binarySearch(arr, target));
        System.out.println("Binary (recursive) -> index " + binarySearchRecursive(arr, target, 0, arr.length - 1));

        int missing = 40;
        System.out.println("\nTarget : " + missing + " (not in array)");
        System.out.println("Binary (iterative) -> index " + binarySearch(arr, missing));
    }
}
