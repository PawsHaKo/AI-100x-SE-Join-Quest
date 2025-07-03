package com.example.definitions;

import com.example.model.Order;
import com.example.model.Product;
import com.example.service.OrderService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.datatable.DataTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepDefinitions {

    private OrderService orderService;
    private Order currentOrder;

    @Given("no promotions are applied")
    public void no_promotions_are_applied() {
        orderService = new OrderService();
    }

    @When("a customer places an order with:")
    public void a_customer_places_an_order_with(DataTable dataTable) {
        List<Product> products = new ArrayList<>();
        for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
            String productName = row.get("productName");
            String category = row.get("category");
            int quantity = Integer.parseInt(row.get("quantity"));
            double unitPrice = Double.parseDouble(row.get("unitPrice"));
            if (category != null) {
                products.add(new Product(productName, category, quantity, unitPrice));
            } else {
                products.add(new Product(productName, quantity, unitPrice));
            }
        }
        currentOrder = orderService.calculateOrder(products);
    }

    @Then("the order summary should be:")
    public void the_order_summary_should_be(DataTable dataTable) {
        Map<String, String> expectedSummary = dataTable.asMaps(String.class, String.class).get(0);
        if (expectedSummary.containsKey("totalAmount")) {
            double expectedTotalAmount = Double.parseDouble(expectedSummary.get("totalAmount"));
            assertEquals(expectedTotalAmount, currentOrder.getTotalAmount(), 0.001);
        } else if (expectedSummary.containsKey("originalAmount")) {
            double expectedOriginalAmount = Double.parseDouble(expectedSummary.get("originalAmount"));
            double expectedDiscount = Double.parseDouble(expectedSummary.get("discount"));
            double expectedTotalAmount = Double.parseDouble(expectedSummary.get("totalAmount"));

            assertEquals(expectedOriginalAmount, currentOrder.getOriginalAmount(), 0.001);
            assertEquals(expectedDiscount, currentOrder.getDiscount(), 0.001);
            assertEquals(expectedTotalAmount, currentOrder.getTotalAmount(), 0.001);
        }
    }

    @Then("the customer should receive:")
    public void the_customer_should_receive(DataTable dataTable) {
        Map<String, Integer> expectedProductsMap = new HashMap<>();
        for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
            expectedProductsMap.put(row.get("productName"), Integer.parseInt(row.get("quantity")));
        }

        Map<String, Integer> actualProductsMap = new HashMap<>();
        for (Product product : currentOrder.getReceivedProducts()) {
            actualProductsMap.put(product.getProductName(), product.getQuantity());
        }

        assertEquals(expectedProductsMap.size(), actualProductsMap.size(), "Number of received products mismatch");

        for (Map.Entry<String, Integer> entry : expectedProductsMap.entrySet()) {
            String productName = entry.getKey();
            Integer expectedQuantity = entry.getValue();
            Integer actualQuantity = actualProductsMap.get(productName);

            assertEquals(expectedQuantity, actualQuantity, "Quantity mismatch for product: " + productName);
        }
    }

    @Given("the threshold discount promotion is configured:")
    public void the_threshold_discount_promotion_is_configured(DataTable dataTable) {
        Map<String, String> config = dataTable.asMaps(String.class, String.class).get(0);
        double threshold = Double.parseDouble(config.get("threshold"));
        double discount = Double.parseDouble(config.get("discount"));
        if (orderService == null) {
            orderService = new OrderService(threshold, discount);
        } else {
            orderService.setThreshold(threshold);
            orderService.setDiscount(discount);
        }
    }

    @Given("the buy one get one promotion for cosmetics is active")
    public void the_buy_one_get_one_promotion_for_cosmetics_is_active() {
        if (orderService == null) {
            orderService = new OrderService(true);
        } else {
            orderService.setBogoCosmeticsActive(true);
        }
    }

    @Given("the 'double eleven' is active")
    public void the_double_eleven_is_active() {
        if (orderService == null) {
            orderService = new OrderService(false, true); // Assuming false for bogoCosmeticsActive and true for doubleElevenActive
        } else {
            orderService.setDoubleElevenActive(true);
        }
    }
}
