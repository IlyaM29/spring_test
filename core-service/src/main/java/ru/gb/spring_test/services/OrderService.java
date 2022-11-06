package ru.gb.spring_test.services;

//import lombok.RequiredArgsConstructor;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import ru.gb.spring_test.api.CartApi;
//import ru.gb.spring_test.converters.ProductMapper;
//import ru.gb.spring_test.dto.Cart;
//import ru.gb.spring_test.dto.FullOrderDetailsDto;
//import ru.gb.spring_test.dto.OrderDetailsDto;
//import ru.gb.spring_test.entities.Order;
//import ru.gb.spring_test.entities.OrderItem;
//import ru.gb.spring_test.repositories.OrderRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;

//@Service
//@RequiredArgsConstructor
//public class OrderService {
//
//    private final ProductService productService;
//
//    private final RestTemplate restTemplate;
//
//    private final OrderRepository orderRepository;
//
//    private final CartApi cartApi;
//
//    @KafkaListener(topics = "OrdersTopic")
//    public void orderListener(FullOrderDetailsDto fullOrderDetailsDto) {
//        createOrder(fullOrderDetailsDto.getUsername(),
//                fullOrderDetailsDto.getOrderDetailsDto(),
//                fullOrderDetailsDto.getCartName());
//    }
//    public void createOrder(String username, OrderDetailsDto orderDetailsDto, String cartName) {
//        Cart currentCart = cartApi.getCurrentCart(cartName);
////        Cart currentCart = restTemplate.postForObject("http://localhost:8187/web-market-cart/api/v1/carts", cartName, Cart.class);
//        Order order = new Order();
//        order.setAddress(orderDetailsDto.getAddress());
//        order.setPhone(orderDetailsDto.getPhone());
//        order.setUsername(username);
//        order.setTotalPrice(currentCart.getTotalPrice());
//        List<OrderItem> items = currentCart.getItems().stream()
//                .map(o -> {
//                    OrderItem orderItem = new OrderItem();
//                    orderItem.setOrder(order);
//                    orderItem.setQuantity(o.getQuantity());
//                    orderItem.setPrice(o.getPrice());
//                    orderItem.setPricePerProduct(o.getPricePerProduct());
//                    orderItem.setProduct(ProductMapper.MAPPER.toProduct(productService.findById(o.getProductId())));
//                    return orderItem;
//                }).collect(Collectors.toList());
//        order.setItems(items);
//        orderRepository.save(order);
//        currentCart.clear();
////        restTemplate.postForObject("http://localhost:8187/web-market-cart/api/v1/carts/clear", cartName, Cart.class);
//        cartApi.clearCart(cartName);
//    }
//
//    public List<Order> findOrdersByUsername(String username) {
//        try {
//            return orderRepository.findByUsername(username);
//        } catch (Exception e) {
//            return new ArrayList<>();
//        }
//    }
//}
