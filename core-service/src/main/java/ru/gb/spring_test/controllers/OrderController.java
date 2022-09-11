package ru.gb.spring_test.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.spring_test.converters.OrderConverter;
import ru.gb.spring_test.dto.OrderDetailsDto;
import ru.gb.spring_test.dto.OrderDto;
import ru.gb.spring_test.servise.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @PostMapping("/{cartName}")
    public void createOrder(@RequestHeader String username, @RequestBody OrderDetailsDto orderDetailsDto, @PathVariable String cartName){
        orderService.createOrder(username, orderDetailsDto, cartName);
    }

    @GetMapping
    public List<OrderDto> getCurrenOrders(@RequestHeader String username){
        return orderService.findOrdersByUsername(username).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }
}
