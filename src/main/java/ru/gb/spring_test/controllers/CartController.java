package ru.gb.spring_test.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;
import ru.gb.spring_test.dto.Cart;
import ru.gb.spring_test.servise.CartService;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService service;

    @PostMapping
    public Cart getCurrentCart(@RequestBody String cartName) {
        return service.getCurrentCart(cartName);
    }

    @PostMapping("/add/{id}")
    public void addProductToCart(@PathVariable Long id, @RequestBody String cartName) {
        service.addProductByIdToCart(id, cartName);
    }

    @PostMapping("/clear")
    public void clearCart(@RequestBody String cartName) {
        service.clear(cartName);
    }

    @PostMapping("/remove/{id}")
    public void removeProduct(@PathVariable Long id, @RequestBody String cartName) {
        service.removeProduct(id, cartName);
    }

    @PostMapping("/decrease/{id}")
    public void decreaseProduct(@PathVariable Long id, @RequestBody String cartName) {
        service.decreaseProduct(id, cartName);
    }
}
