package com.example.medicalstore.cart;

import com.example.medicalstore.order.Order;

public class CartOrder extends Order {

    public CartOrder(String orderId, String userId,
                     String medId, String medName,
                     int quantity, double totalPrice) {
        super(orderId, userId, medId, medName,
                quantity, totalPrice, "PENDING");
    }

    @Override
    public double calculateTotal(double unitPrice, int qty) {
        return unitPrice * qty;
    }
}
