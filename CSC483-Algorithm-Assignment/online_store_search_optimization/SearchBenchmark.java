package online_store_search_optimization;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class SearchBenchmark {

  private static Product[] makeProducts(int size) {
    Product[] products = new Product[size];
    Random rnd = new Random(42);
    for (int i = 0; i < size; i++) {
      int id = i + 1;
      String name = "Product-" + id;
      String category = "Category-" + (rnd.nextInt(10) + 1);
      double price = Math.round(rnd.nextDouble() * 10000) / 100.0;
      int stock = rnd.nextInt(500);
      products[i] = new Product(id, name, category, price, stock);
    }
    return products;
  }

  public static void main(String[] args) {
    int size = 100_000;
    int trials = 5_000;
    if (args.length >= 1) {
      try {
        size = Integer.parseInt(args[0]);
      } catch (NumberFormatException ignored) {
      }
    }
    if (args.length >= 2) {
      try {
        trials = Integer.parseInt(args[1]);
      } catch (NumberFormatException ignored) {
      }
    }

    Product[] products = makeProducts(size);

    // Ensure sorted by productId for binary search
    Arrays.sort(products, Comparator.comparingInt(Product::getProductId));

    SearchAlgorithms alg = new SearchAlgorithms();
    Random rnd = new Random(123);

    long seqTotal = 0L;
    long binTotal = 0L;
    long nameTotal = 0L;

    // Warm up a bit
    for (int w = 0; w < 200; w++) {
      int r = rnd.nextInt(size);
      alg.sequentialSearchById(products, products[r].getProductId());
      alg.binarySearchById(products, products[r].getProductId());
      alg.searchByName(products, products[r].getProductName());
    }

    for (int t = 0; t < trials; t++) {
      int idx = rnd.nextInt(size);
      int targetId = products[idx].getProductId();
      String targetName = products[idx].getProductName();

      long s0 = System.nanoTime();
      alg.sequentialSearchById(products, targetId);
      long s1 = System.nanoTime();
      seqTotal += (s1 - s0);

      long b0 = System.nanoTime();
      alg.binarySearchById(products, targetId);
      long b1 = System.nanoTime();
      binTotal += (b1 - b0);

      long n0 = System.nanoTime();
      alg.searchByName(products, targetName);
      long n1 = System.nanoTime();
      nameTotal += (n1 - n0);
    }

    double seqAvgNs = seqTotal / (double) trials;
    double binAvgNs = binTotal / (double) trials;
    double nameAvgNs = nameTotal / (double) trials;

    System.out.println("Search benchmark summary");
    System.out.println("Products: " + size + ", Trials: " + trials);
    System.out.printf("Sequential by id: avg = %.3f ms\n", seqAvgNs / 1_000_000.0);
    System.out.printf("Binary by id:     avg = %.3f ms\n", binAvgNs / 1_000_000.0);
    System.out.printf("Search by name:   avg = %.3f ms\n", nameAvgNs / 1_000_000.0);
    
  }
}
