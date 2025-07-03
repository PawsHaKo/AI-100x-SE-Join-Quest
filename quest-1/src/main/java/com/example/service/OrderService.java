package com.example.service;

import com.example.model.Order;
import com.example.model.Product;

import java.util.List;

public class OrderService {

    private double threshold;
    private double discount;
    private boolean bogoCosmeticsActive;
    private boolean doubleElevenActive;

    public OrderService() {
        this.threshold = 0;
        this.discount = 0;
        this.bogoCosmeticsActive = false;
        this.doubleElevenActive = false;
    }

    public OrderService(double threshold, double discount) {
        this.threshold = threshold;
        this.discount = discount;
        this.bogoCosmeticsActive = false;
        this.doubleElevenActive = false;
    }

    public OrderService(boolean bogoCosmeticsActive) {
        this.bogoCosmeticsActive = bogoCosmeticsActive;
        this.threshold = 0;
        this.discount = 0;
        this.doubleElevenActive = false;
    }

    public OrderService(boolean bogoCosmeticsActive, boolean doubleElevenActive) {
        this.bogoCosmeticsActive = bogoCosmeticsActive;
        this.doubleElevenActive = doubleElevenActive;
        this.threshold = 0;
        this.discount = 0;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setBogoCosmeticsActive(boolean bogoCosmeticsActive) {
        this.bogoCosmeticsActive = bogoCosmeticsActive;
    }

    public void setDoubleElevenActive(boolean doubleElevenActive) {
        this.doubleElevenActive = doubleElevenActive;
    }

    public Order calculateOrder(List<Product> products) {
        Order order = new Order();
        for (Product product : products) {
            order.addProduct(product);
            order.addReceivedProduct(new Product(product.getProductName(), product.getCategory(), product.getQuantity(), product.getUnitPrice()));
        }

        if (bogoCosmeticsActive) {
            for (Product product : products) { // Iterate through original products to add free items
                if ("cosmetics".equals(product.getCategory())) {
                    int freeItems = product.getQuantity();
                    order.addReceivedProduct(new Product(product.getProductName(), product.getCategory(), freeItems, 0.0));
                }
            }
        }

        if (doubleElevenActive) {
            // Group products by product name to check for identical items
            for (Product product : products) {
                if (product.getQuantity() >= 10) {
                    int discountUnits = product.getQuantity() / 10;
                    double productDiscount = discountUnits * 10 * product.getUnitPrice() * 0.20;
                    order.setDiscount(order.getDiscount() + productDiscount);
                }
            }
        }

        if (order.getOriginalAmount() >= threshold && threshold > 0) {
            order.setDiscount(order.getDiscount() + discount);
        }
        return order;
    }
}