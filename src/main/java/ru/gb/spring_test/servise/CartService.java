package ru.gb.spring_test.servise;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import ru.gb.spring_test.converters.ProductMapper;
import ru.gb.spring_test.dto.Cart;
import ru.gb.spring_test.entities.Product;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductService productService;
    private final CacheManager cacheManager;
    private Cart cart;

    public Cart getCurrentCart(String cartName) {
        cart = cacheManager.getCache("Cart").get(cartName, Cart.class);
        if (!Optional.ofNullable(cart).isPresent()) {
            cart = new Cart(cartName, cacheManager);
            cacheManager.getCache("Cart").put(cartName, cart);
        }
        return cart;
    }

    public void addProductByIdToCart(Long id, String cartName) {
        if (!getCurrentCart(cartName).addProductCount(id)) {
            Product product = ProductMapper.MAPPER.toProduct(productService.findById(id));
            Cart cart = getCurrentCart(cartName);
            cart.addProduct(product);
            cacheManager.getCache("Cart").put(cartName, cart);
        }
    }

    public void clear(String cartName) {
        Cart cart = getCurrentCart(cartName);
        cart.clear();
        cacheManager.getCache("Cart").put(cartName, cart);
    }

    public void removeProduct(Long id, String cartName) {
        Cart cart = getCurrentCart(cartName);
        cart.removeProduct(id);
        cacheManager.getCache("Cart").put(cartName, cart);
    }

    public void decreaseProduct(Long id, String cartName) {
        Cart cart = getCurrentCart(cartName);
        cart.decreaseProduct(id);
        cacheManager.getCache("Cart").put(cartName, cart);
    }
}
