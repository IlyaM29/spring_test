package ru.gb.spring_test.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gb.spring_test.converters.OrderConverter;
import ru.gb.spring_test.dto.OrderDetailsDto;
import ru.gb.spring_test.dto.OrderDto;
import ru.gb.spring_test.entities.User;
import ru.gb.spring_test.exceptions.ResourceNotFoundException;
import ru.gb.spring_test.servise.OrderService;
import ru.gb.spring_test.servise.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @PostMapping
    public void createOrder(Principal principal, @RequestBody OrderDetailsDto orderDetailsDto) {
        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        orderService.createOrder(user, orderDetailsDto, "cart_" + principal.getName());
    }

    @GetMapping
    public List<OrderDto> getCurrentOrders(Principal principal) {
        return orderService.findOrdersByUsername(principal.getName()).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }
}
