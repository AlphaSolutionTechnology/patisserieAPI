package com.alphasolutions.patisserie.controller;

import com.alphasolutions.patisserie.model.dto.OrderRequestDTO;
import com.alphasolutions.patisserie.pojo.Address;
import com.alphasolutions.patisserie.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final RestTemplate restTemplate;

    public OrderController(OrderService orderService, RestTemplate restTemplate) {
        this.orderService = orderService;
        this.restTemplate = restTemplate;
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

    @GetMapping("/address/{cep}")
    public ResponseEntity<?> getAddress(@PathVariable(value = "cep") String cep) {
        return ResponseEntity.ok().body(restTemplate.getForObject("https://viacep.com.br/ws/"+ cep + "/json", Address.class));

    }
}
