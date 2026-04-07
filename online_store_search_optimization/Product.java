package online_store_search_optimization;
public class Product {
  private int productId;
  private String productName;
  private String category;
  private double price;
  private int stockQuantity;

  public Product(int productId, String productName, String category, double price, int stockQuantity) {
    this.productId = productId;
    this.productName = productName;
    this.category = category;
    this.price = price;
    this.stockQuantity = stockQuantity;
  }

  public int getProductId() {
    return productId;
  }

  public String getProductName() {
    return productName;
  }

  public String getCategory() {
    return category;
  }

  public double getPrice() {
    return price;
  }

  public int getStockQuantity() {
    return stockQuantity;
  }

  public void setPrice(double price) {
    if (price < 0) {
      System.out.println("Price cannot be negative");
    }

    this.price = price;
  }

  public void setStockQuantity(int stockQuantity) {
    if (stockQuantity < 0) {
      System.out.println("Stock quantity cannot be negative");
    }
    this.stockQuantity = stockQuantity;
  }

}