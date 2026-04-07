package algorithms_comparison;

import java.util.Arrays;
import java.util.Random;

public class SortingBenchmark {

  private static final int RUNS = 5;
  private static final int[] SIZES = { 100, 1_000, 10_000, 100_000 };
  private static final int SEED = 42;
  private static final int DUPLICATE_DISTINCT = 10; // for "many duplicates" dataset

  public static void main(String[] args) {
    System.out.println("================================================================");
    System.out.println("      SORTING ALGORITHMS EMPIRICAL BENCHMARK - CSC 483");
    System.out.println("================================================================");

    String[] datasetTypes = { "Random", "Sorted", "Reverse Sorted", "Nearly Sorted", "Many Duplicates" };

    for (String datasetType : datasetTypes) {
      printDatasetHeader(datasetType);

      System.out.printf("%-15s %-12s %-14s %-16s %-14s%n",
          "Algorithm", "Size", "Time (ms)", "Comparisons", "Swaps");
      System.out.println("-".repeat(73));

      for (int size : SIZES) {
        int[] base = generateDataset(datasetType, size);

        benchmarkAlgorithm("Insertion", base, size, "insertion");
        benchmarkAlgorithm("Merge", base, size, "merge");
        benchmarkAlgorithm("Quick", base, size, "quick");

        if (size != SIZES[SIZES.length - 1])
          System.out.println(); // blank line between sizes
      }
      System.out.println();
    }

    System.out.println("================================================================");
    System.out.println("CONCLUSIONS:");
    System.out.println("  - Quick Sort is fastest on average for random data");
    System.out.println("  - Insertion Sort excels on nearly-sorted data (approaches O(n))");
    System.out.println("  - Merge Sort provides consistent O(n log n) across all data types");
    System.out.println("  - Insertion Sort degrades severely on random/reverse-sorted large data");
    System.out.println("================================================================");
  }

  // -------------------------------------------------------------------------
  // Dataset Generators
  // -------------------------------------------------------------------------

  /**
   * Generates an integer array of the specified dataset type and size.
   *
   * @param type one of: "Random", "Sorted", "Reverse Sorted", "Nearly Sorted",
   *             "Many Duplicates"
   * @param size number of elements
   * @return generated integer array
   */
  public static int[] generateDataset(String type, int size) {
    Random rng = new Random(SEED);
    int[] arr = new int[size];

    switch (type) {
      case "Random":
        for (int i = 0; i < size; i++)
          arr[i] = rng.nextInt(size * 10);
        break;

      case "Sorted":
        for (int i = 0; i < size; i++)
          arr[i] = i;
        break;

      case "Reverse Sorted":
        for (int i = 0; i < size; i++)
          arr[i] = size - i;
        break;

      case "Nearly Sorted":
        for (int i = 0; i < size; i++)
          arr[i] = i;
        // Shuffle 10% of elements to random positions
        int swapCount = size / 10;
        for (int i = 0; i < swapCount; i++) {
          int a = rng.nextInt(size);
          int b = rng.nextInt(size);
          int tmp = arr[a];
          arr[a] = arr[b];
          arr[b] = tmp;
        }
        break;

      case "Many Duplicates":
        for (int i = 0; i < size; i++)
          arr[i] = rng.nextInt(DUPLICATE_DISTINCT);
        break;

      default:
        throw new IllegalArgumentException("Unknown dataset type: " + type);
    }
    return arr;
  }

  // -------------------------------------------------------------------------
  // Benchmark Runner
  // -------------------------------------------------------------------------

  private static void benchmarkAlgorithm(String label, int[] base, int size, String algo) {
    long totalTime = 0;
    long totalComparisons = 0;
    long totalSwaps = 0;
    SortingAlgorithms.SortMetrics metrics = new SortingAlgorithms.SortMetrics();

    for (int run = 0; run <= RUNS; run++) {
      int[] arr = Arrays.copyOf(base, base.length);
      metrics.reset();

      long start = System.nanoTime();
      switch (algo) {
        case "insertion":
          SortingAlgorithms.insertionSort(arr, metrics);
          break;
        case "merge":
          SortingAlgorithms.mergeSort(arr, metrics);
          break;
        case "quick":
          SortingAlgorithms.quickSort(arr, metrics);
          break;
      }
      totalTime += System.nanoTime() - start;
      totalComparisons += metrics.comparisons;
      totalSwaps += metrics.swaps;
    }

    double avgMs = (totalTime / RUNS) / 1_000_000.0;
    long avgComp = totalComparisons / RUNS;
    long avgSwaps = totalSwaps / RUNS;

    // Merge sort doesn't do traditional swaps (it copies); display N/A
    String swapStr = algo.equals("merge") ? "N/A" : String.format("%,d", avgSwaps);

    System.out.printf("%-15s %-12s %-14s %-16s %-14s%n",
        label + " Sort",
        String.format("%,d", size),
        String.format("%.3f", avgMs),
        String.format("%,d", avgComp),
        swapStr);
  }

  private static void printDatasetHeader(String type) {
    System.out.println("================================================================");
    System.out.println("SORTING ALGORITHMS COMPARISON - " + type.toUpperCase() + " DATA");
    System.out.println("================================================================");
  }
}