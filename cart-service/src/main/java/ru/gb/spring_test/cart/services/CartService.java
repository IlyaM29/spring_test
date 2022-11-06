package ru.gb.spring_test.cart.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.RestTemplate;
import ru.gb.spring_test.cart.api.ProductApi;
import ru.gb.spring_test.cart.dto.OrderDetailsDto;
import ru.gb.spring_test.cart.dto.OrderDto;
import ru.gb.spring_test.cart.dto.ProductDto;
import ru.gb.spring_test.cart.entities.Cart;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    @Qualifier("test")
    private final CacheManager cacheManager;

    @Qualifier(value = "Kafka")
    @Autowired
    private KafkaTemplate<Long, OrderDto> kafkaTemplate;

    private final ProductApi productApi;

    @Value("${spring.cache.user.name}")
    private String CACHE_CART;

    private Cart cart;

    @Cacheable(value = "Cart", key = "#cartName")
    public Cart getCurrentCart(String cartName) {
        cart = cacheManager.getCache(CACHE_CART).get(cartName, Cart.class);
        if (!Optional.ofNullable(cart).isPresent()) {
            cart = new Cart(cartName, cacheManager);
            cacheManager.getCache(CACHE_CART).put(cartName, cart);
        }
        return cart;
    }

    @CachePut(value = "Cart", key = "#cartName")
    public Cart addProductByIdToCart(Long id, String cartName) {
        Cart cart = getCurrentCart(cartName);
        if (!cart.addProductCount(id)) {
            ProductDto product = productApi.getById(id);
            cart.addProduct(product);
        }
        return cart;
    }

    @CachePut(value = "Cart", key = "#cartName")
    public Cart clear(String cartName) {
        Cart cart = getCurrentCart(cartName);
        cart.clear();
        return cart;
    }

    @CachePut(value = "Cart", key = "#cartName")
    public Cart removeProduct(Long id, String cartName) {
        Cart cart = getCurrentCart(cartName);
        cart.removeProduct(id);
        return cart;
    }

    @CachePut(value = "Cart", key = "#cartName")
    public Cart decreaseProduct(Long id, String cartName) {
        Cart cart = getCurrentCart(cartName);
        cart.decreaseProduct(id);
        return cart;
    }

    public void createOrder(String username, OrderDetailsDto orderDetailsDto, String cartName) {
        Cart currentCart = getCurrentCart(cartName);
        OrderDto order = new OrderDto();
        order.setAddress(orderDetailsDto.getAddress());
        order.setPhone(orderDetailsDto.getPhone());
        order.setUsername(username);
        order.setTotalPrice(currentCart.getTotalPrice());
        order.setItemDtoList(currentCart.getItems());
        clear(cartName);
//        currentCart.clear();
        ListenableFuture<SendResult<Long, OrderDto>> future = kafkaTemplate.send("OrdersTopic", order);
        future.addCallback(System.out::println, System.err::println);
        kafkaTemplate.flush();
    }
}
