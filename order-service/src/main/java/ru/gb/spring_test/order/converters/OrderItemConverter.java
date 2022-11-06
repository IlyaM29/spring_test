package ru.gb.spring_test.order.converters;

import org.springframework.stereotype.Component;
import ru.gb.spring_test.order.dto.OrderItemDto;
import ru.gb.spring_test.order.entities.OrderItem;

@Component
public class OrderItemConverter {

    public OrderItemDto entityToDto(OrderItem orderItem) {
        return new OrderItemDto(orderItem.getProductId(), orderItem.getQuantity(),
                orderItem.getPricePerProduct(), orderItem.getPrice());
    }
}
