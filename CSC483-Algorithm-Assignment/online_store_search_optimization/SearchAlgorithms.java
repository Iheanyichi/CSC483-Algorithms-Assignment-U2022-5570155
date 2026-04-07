package online_store_search_optimization;

public class SearchAlgorithms {
  public Product sequentialSearchById(Product[] products, int targetId) {
    for (Product product : products) {
      if (product.getProductId() == targetId) {
        return product;
      }
    }
    return null;
  }

  public Product binarySearchById(Product[] products, int targetId) {
    int left = 0;
    int right = products.length - 1;

    while (left <= right) {
      int mid = left + (right - left) / 2;
      if (products[mid].getProductId() == targetId) {
        return products[mid];
      } else if (products[mid].getProductId() < targetId) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }
    return null;
  }

  public Product searchByName(Product[] products, String targetName) {
    for (Product product : products) {
      if (product.getProductName().equalsIgnoreCase(targetName)) {
        return product;
      }
    }
    return null;
  }

  public Product addProduct(Product[] products, Product newProduct) {
    int insertPos = products.length - 1;

    if (products[insertPos] != null) {
      System.out.println("No empty slots found");
    }

    while (insertPos >= 0 && products[insertPos].getProductId() > newProduct.getProductId()) {
      products[insertPos + 1] = products[insertPos];
      insertPos--;
    }

    products[insertPos + 1] = newProduct;
    return newProduct;
  }
}
