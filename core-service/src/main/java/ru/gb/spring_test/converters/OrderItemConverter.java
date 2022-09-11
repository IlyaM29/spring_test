package ru.gb.spring_test.converters;

import org.springframework.stereotype.Component;
import ru.gb.spring_test.dto.OrderItemDto;
import ru.gb.spring_test.entities.OrderItem;

@Component
public class OrderItemConverter {

    public OrderItemDto entityToDto(OrderItem orderItem) {
        return new OrderItemDto(orderItem.getProduct().getId(), orderItem.getProduct().getTitle(),
                orderItem.getQuantity(), orderItem.getPricePerProduct(), orderItem.getPrice());
    }
}
