package algorithms_comparison;

public class SortingAlgorithms {

  // =========================================================================
  // INSERTION SORT
  // O(n) best | O(n^2) average/worst | O(1) space | Stable | In-place
  // =========================================================================

  /**
   * Sorts an integer array using insertion sort.
   * Excellent for small or nearly-sorted arrays due to O(n) best case.
   *
   * @param arr     array to sort
   * @param metrics tracks comparisons and swaps
   */
  public static void insertionSort(int[] arr, SortMetrics metrics) {
    if (arr == null || arr.length <= 1)
      return;

    for (int i = 1; i < arr.length; i++) {
      int key = arr[i];
      int j = i - 1;

      // Shift elements greater than key one position right
      while (j >= 0) {
        if (metrics != null)
          metrics.comparisons++;
        if (arr[j] > key) {
          arr[j + 1] = arr[j];
          if (metrics != null)
            metrics.swaps++;
          j--;
        } else {
          break;
        }
      }
      arr[j + 1] = key;
    }
  }

  // =========================================================================
  // MERGE SORT
  // O(n log n) all cases | O(n) space | Stable | NOT in-place
  // =========================================================================

  /**
   * Sorts an integer array using merge sort.
   * Guarantees O(n log n) in all cases; stable sort.
   *
   * @param arr     array to sort
   * @param metrics tracks comparisons
   */
  public static void mergeSort(int[] arr, SortMetrics metrics) {
    if (arr == null || arr.length <= 1)
      return;
    int[] temp = new int[arr.length];
    mergeSortHelper(arr, temp, 0, arr.length - 1, metrics);
  }

  private static void mergeSortHelper(int[] arr, int[] temp, int left, int right,
      SortMetrics metrics) {
    if (left >= right)
      return;

    int mid = left + (right - left) / 2;
    mergeSortHelper(arr, temp, left, mid, metrics);
    mergeSortHelper(arr, temp, mid + 1, right, metrics);
    merge(arr, temp, left, mid, right, metrics);
  }

  private static void merge(int[] arr, int[] temp, int left, int mid, int right,
      SortMetrics metrics) {
    // Copy sub array to temp
    for (int k = left; k <= right; k++)
      temp[k] = arr[k];

    int i = left, j = mid + 1, k = left;

    while (i <= mid && j <= right) {
      if (metrics != null)
        metrics.comparisons++;
      if (temp[i] <= temp[j]) {
        arr[k++] = temp[i++];
      } else {
        arr[k++] = temp[j++];
      }
    }
    while (i <= mid)
      arr[k++] = temp[i++];
    while (j <= right)
      arr[k++] = temp[j++];
  }

  // =========================================================================
  // QUICK SORT
  // O(n log n) best/avg | O(n^2) worst | O(log n) space | NOT stable | In-place
  // =========================================================================

  /**
   * Sorts an integer array using quick sort with median-of-three pivot selection.
   * Median-of-three pivot significantly reduces the likelihood of the O(n^2)
   * worst case.
   *
   * @param arr     array to sort (modified in-place)
   * @param metrics tracks comparisons and swaps (pass null to skip)
   */
  public static void quickSort(int[] arr, SortMetrics metrics) {
    if (arr == null || arr.length <= 1)
      return;
    quickSortHelper(arr, 0, arr.length - 1, metrics);
  }

  private static void quickSortHelper(int[] arr, int low, int high, SortMetrics metrics) {
    if (low < high) {
      int pivotIndex = partition(arr, low, high, metrics);
      quickSortHelper(arr, low, pivotIndex - 1, metrics);
      quickSortHelper(arr, pivotIndex + 1, high, metrics);
    }
  }

  private static int partition(int[] arr, int low, int high, SortMetrics metrics) {
    // Median-of-three pivot selection
    int mid = low + (high - low) / 2;
    if (arr[mid] < arr[low])
      swap(arr, mid, low, metrics);
    if (arr[high] < arr[low])
      swap(arr, high, low, metrics);
    if (arr[mid] < arr[high])
      swap(arr, mid, high, metrics);
    // arr[high] is now the median — use as pivot
    int pivot = arr[high];
    int i = low - 1;

    for (int j = low; j < high; j++) {
      if (metrics != null)
        metrics.comparisons++;
      if (arr[j] <= pivot) {
        i++;
        swap(arr, i, j, metrics);
      }
    }
    swap(arr, i + 1, high, metrics);
    return i + 1;
  }

  // =========================================================================
  // Utility
  // =========================================================================

  private static void swap(int[] arr, int i, int j, SortMetrics metrics) {
    if (i == j)
      return;
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
    if (metrics != null)
      metrics.swaps++;
  }

  // =========================================================================
  // SortMetrics — tracks algorithm statistics
  // =========================================================================

  /**
   * Tracks the number of comparisons and swaps performed by a sorting algorithm.
   * Reset between runs by calling {@link #reset()}.
   */
  public static class SortMetrics {
    /** Total element comparisons performed */
    public long comparisons = 0;

    /** Total element swaps / assignments performed */
    public long swaps = 0;

    /** Resets all counters to zero */
    public void reset() {
      comparisons = 0;
      swaps = 0;
    }

    @Override
    public String toString() {
      return String.format("comparisons=%,d  swaps=%,d", comparisons, swaps);
    }
  }

}