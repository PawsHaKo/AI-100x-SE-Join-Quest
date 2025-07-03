package com.example.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Product> products;
    private List<Product> receivedProducts;
    private double discount;

    public Order() {
        this.products = new ArrayList<>();
        this.receivedProducts = new ArrayList<>();
        this.discount = 0.0;
    }

    public void addProduct(Product newProduct) {
        boolean found = false;
        for (Product existingProduct : products) {
            if (existingProduct.getProductName().equals(newProduct.getProductName()) &&
                (existingProduct.getCategory() == null && newProduct.getCategory() == null ||
                 existingProduct.getCategory() != null && existingProduct.getCategory().equals(newProduct.getCategory()))) {
                existingProduct.setQuantity(existingProduct.getQuantity() + newProduct.getQuantity());
                found = true;
                break;
            }
        }
        if (!found) {
            this.products.add(newProduct);
        }
    }

    public void addReceivedProduct(Product newProduct) {
        boolean found = false;
        for (Product existingProduct : receivedProducts) {
            if (existingProduct.getProductName().equals(newProduct.getProductName()) &&
                (existingProduct.getCategory() == null && newProduct.getCategory() == null ||
                 existingProduct.getCategory() != null && existingProduct.getCategory().equals(newProduct.getCategory()))) {
                existingProduct.setQuantity(existingProduct.getQuantity() + newProduct.getQuantity());
                found = true;
                break;
            }
        }
        if (!found) {
            this.receivedProducts.add(newProduct);
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Product> getReceivedProducts() {
        return receivedProducts;
    }

    public double getOriginalAmount() {
        double original = 0.0;
        for (Product product : products) {
            original += product.getQuantity() * product.getUnitPrice();
        }
        return original;
    }

    public double getTotalAmount() {
        return getOriginalAmount() - discount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
