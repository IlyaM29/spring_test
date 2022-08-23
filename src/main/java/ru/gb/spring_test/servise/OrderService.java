package ru.gb.spring_test.servise;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.spring_test.converters.ProductMapper;
import ru.gb.spring_test.dto.Cart;
import ru.gb.spring_test.dto.OrderDetailsDto;
import ru.gb.spring_test.entities.Order;
import ru.gb.spring_test.entities.OrderItem;
import ru.gb.spring_test.entities.User;
import ru.gb.spring_test.repositories.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductService productService;
    private final CartService cartService;
    private final OrderRepository orderRepository;

    public void createOrder(User user, OrderDetailsDto orderDetailsDto, String cartName) {
        Cart currentCart = cartService.getCurrentCart(cartName);
        Order order = new Order();
        order.setAddress(orderDetailsDto.getAddress());
        order.setPhone(orderDetailsDto.getPhone());
        order.setUser(user);
        order.setTotalPrice(currentCart.getTotalPrice());
        List<OrderItem> items = currentCart.getItems().stream()
                .map(o -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setQuantity(o.getQuantity());
                    orderItem.setPrice(o.getPrice());
                    orderItem.setPricePerProduct(o.getPricePerProduct());
                    orderItem.setProduct(ProductMapper.MAPPER.toProduct(productService.findById(o.getProductId())));
                    return orderItem;
                }).collect(Collectors.toList());
        order.setItems(items);
        orderRepository.save(order);
        currentCart.clear();
    }

    public List<Order> findOrdersByUsername(String username) {
        return orderRepository.findByUsername(username);
    }
}
