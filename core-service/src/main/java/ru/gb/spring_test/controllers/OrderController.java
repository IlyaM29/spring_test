package ru.gb.spring_test.controllers;

//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.SendResult;
//import org.springframework.util.concurrent.ListenableFuture;
//import org.springframework.web.bind.annotation.*;
//import ru.gb.spring_test.converters.OrderConverter;
//import ru.gb.spring_test.dto.FullOrderDetailsDto;
//import ru.gb.spring_test.dto.OrderDetailsDto;
//import ru.gb.spring_test.dto.OrderDto;
//import ru.gb.spring_test.services.OrderService;
//
//import java.util.List;
//import java.util.stream.Collectors;

//@RestController
//@RequestMapping("/api/v1/orders")
//@RequiredArgsConstructor
//public class OrderController {
//
//    private final OrderService orderService;
//    private final OrderConverter orderConverter;
//
//    @Qualifier(value = "Kafka")
//    @Autowired
//    private KafkaTemplate<Long, FullOrderDetailsDto> kafkaTemplate;
//
//    @PostMapping("/{cartName}")
//    public void createOrder(@RequestHeader String username, @RequestBody OrderDetailsDto orderDetailsDto, @PathVariable String cartName){
////        orderService.createOrder(username, orderDetailsDto, cartName);
//        System.out.println("controller: " + orderDetailsDto);
//        ListenableFuture<SendResult<Long, FullOrderDetailsDto>> future = kafkaTemplate.send("OrdersTopic", new FullOrderDetailsDto(username, orderDetailsDto, cartName));
//        future.addCallback(System.out::println, System.err::println);
//        kafkaTemplate.flush();
//    }
//
//    @GetMapping
//    public List<OrderDto> getCurrenOrders(@RequestHeader String username){
//        return orderService.findOrdersByUsername(username).stream()
//                .map(orderConverter::entityToDto).collect(Collectors.toList());
//    }
//}
