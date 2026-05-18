package com.example.medicalstore.cart;

import com.example.medicalstore.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/cart")
    public String viewCart(@RequestParam String userId, Model model) {
        model.addAttribute("cart", cartService.getCartForUser(userId));
        model.addAttribute("userId", userId);
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addItem(@RequestParam String userId,
                          @RequestParam String medId,
                          @RequestParam String medName,
                          @RequestParam int quantity,
                          @RequestParam double unitPrice,
                          @RequestParam(defaultValue = "cart") String redirect) {

        cartService.addItem(new CartItem(userId, medId, medName, quantity, unitPrice));

        if ("medicines".equals(redirect)) {
            return "redirect:/medicines";
        }
        if (redirect.startsWith("medicine-detail")) {
            return "redirect:/medicines/view?id=" + medId;
        }
        return "redirect:/cart?userId=" + userId;
    }

    @PostMapping("/cart/update")
    public String updateQty(@RequestParam String userId,
                            @RequestParam String medId,
                            @RequestParam int quantity) {
        cartService.updateQuantity(userId, medId, quantity);
        return "redirect:/cart?userId=" + userId;
    }

    @GetMapping("/cart/remove")
    public String removeItem(@RequestParam String userId,
                             @RequestParam String medId) {
        cartService.removeItem(userId, medId);
        return "redirect:/cart?userId=" + userId;
    }

    @PostMapping("/cart/checkout")
    public String checkout(@RequestParam String userId) {
        Cart cart = cartService.getCartForUser(userId);
        if (!cart.isEmpty()) {
            for (CartItem item : cart.getItems()) {
                orderService.placeOrder(
                        userId,
                        item.getMedId(),
                        item.getMedName(),
                        item.getQuantity(),
                        item.getUnitPrice()
                );
            }
            cartService.clearCart(userId);
        }
        return "redirect:/cart?userId=" + userId + "&ordered=true";
    }
}
