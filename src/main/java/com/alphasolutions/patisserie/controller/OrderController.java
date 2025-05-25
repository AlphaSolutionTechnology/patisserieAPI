package com.alphasolutions.patisserie.controller;

import com.alphasolutions.patisserie.model.dto.OrderRequestDTO;
import com.alphasolutions.patisserie.service.OrderService;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDTO order) {
        orderService.createOrder(order);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-order")
    public ResponseEntity<?> getOrder(@RequestBody String userCode) {
        return ResponseEntity.ok().body(orderService.getAllOrders(userCode));
    }
}
