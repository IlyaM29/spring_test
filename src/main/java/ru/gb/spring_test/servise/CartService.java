package ru.gb.spring_test.servise;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    @Value("${other.cache.cart}")
    private String CACHE_CART;

    private Cart cart;

    @Cacheable(value = "${other.cache.cart}", key = "#cartName")
    public Cart getCurrentCart(String cartName) {
        cart = cacheManager.getCache(CACHE_CART).get(cartName, Cart.class);
        if (!Optional.ofNullable(cart).isPresent()) {
            cart = new Cart(cartName, cacheManager);
            cacheManager.getCache(CACHE_CART).put(cartName, cart);
        }
        return cart;
    }

    @CachePut(value = "${other.cache.cart}", key = "#cartName")
    public Cart addProductByIdToCart(Long id, String cartName) {
        Cart cart = getCurrentCart(cartName);
        if (!getCurrentCart(cartName).addProductCount(id)) {
            Product product = ProductMapper.MAPPER.toProduct(productService.findById(id));
            cart.addProduct(product);
        }
        return cart;
    }

    @CachePut(value = "${other.cache.cart}", key = "#cartName")
    public Cart clear(String cartName) {
        Cart cart = getCurrentCart(cartName);
        cart.clear();
        return cart;
    }

    @CachePut(value = "${other.cache.cart}", key = "#cartName")
    public Cart removeProduct(Long id, String cartName) {
        Cart cart = getCurrentCart(cartName);
        cart.removeProduct(id);
        return cart;
    }

    @CachePut(value = "${other.cache.cart}", key = "#cartName")
    public Cart decreaseProduct(Long id, String cartName) {
        Cart cart = getCurrentCart(cartName);
        cart.decreaseProduct(id);
        return cart;
    }
}
