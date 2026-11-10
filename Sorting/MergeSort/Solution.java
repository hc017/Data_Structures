import java.util.Arrays;

/**
 * Day 18 - Sorting: Merge Sort
 * Sort an array using divide and conquer.
 */
public class Solution {

    // Brute force: Bubble Sort, O(n^2) time, O(1) space.
    static void bubbleSort(int[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    int t = a[j]; a[j] = a[j + 1]; a[j + 1] = t;
                    swapped = true;
                }
            }
            if (!swapped) break; // already sorted
        }
    }

    // Optimized: Merge Sort, O(n log n) time, O(n) space.
    static void mergeSort(int[] a, int lo, int hi) {
        if (lo >= hi) return;            // base case: 0 or 1 element
        int mid = lo + (hi - lo) / 2;    // avoid overflow
        mergeSort(a, lo, mid);           // sort left half
        mergeSort(a, mid + 1, hi);       // sort right half
        merge(a, lo, mid, hi);           // combine
    }

    // Merge two sorted ranges [lo..mid] and [mid+1..hi].
    static void merge(int[] a, int lo, int mid, int hi) {
        int[] tmp = new int[hi - lo + 1];
        int i = lo, j = mid + 1, k = 0;
        while (i <= mid && j <= hi) {
            // <= keeps the sort stable
            tmp[k++] = (a[i] <= a[j]) ? a[i++] : a[j++];
        }
        while (i <= mid) tmp[k++] = a[i++];
        while (j <= hi) tmp[k++] = a[j++];
        System.arraycopy(tmp, 0, a, lo, tmp.length);
    }

    public static void main(String[] args) {
        int[] data = {38, 27, 43, 3, 9, 82, 10};
        int[] brute = data.clone();
        int[] fast = data.clone();

        bubbleSort(brute);
        mergeSort(fast, 0, fast.length - 1);

        System.out.println("Input      : " + Arrays.toString(data));
        System.out.println("Bubble Sort: " + Arrays.toString(brute));
        System.out.println("Merge Sort : " + Arrays.toString(fast));
    }
}
