package com.alphasolutions.patisserie.service;

import com.alphasolutions.patisserie.model.dto.OrderRequestDTO;
import com.alphasolutions.patisserie.model.dto.OrderResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    void createOrder(OrderRequestDTO order);
    List<OrderResponseDTO> getAllOrders(String userCode);
}
