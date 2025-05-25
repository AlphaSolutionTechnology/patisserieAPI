package com.alphasolutions.patisserie.mappers;

import com.alphasolutions.patisserie.model.dto.OrderResponseDTO;
import com.alphasolutions.patisserie.model.entities.Order;
import com.alphasolutions.patisserie.model.entities.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderResponseDTO toResponseDTO(Order order, List<OrderItem> orderItems) {
        // Mapear OrderItems para ProductDTOs
        List<OrderResponseDTO.ProductDTO> productDTOs = orderItems.stream()
                .map(item -> {
                    OrderResponseDTO.ProductDTO dto = new OrderResponseDTO.ProductDTO(
                            item.getProduct(), // Passa o Product associado
                            item.getQuantity() // Passa a quantidade do OrderItem
                    );
                    return dto;
                })
                .collect(Collectors.toList());

        // Calcular o total
        double total = orderItems.stream()
                .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                .sum();

        // Criar e preencher o OrderResponseDTO
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setName(order.getUser().getUsername());
        dto.setOrderCode(order.getOrderCode());
        dto.setOrderDate(order.getDateTime());
        dto.setProducts(productDTOs);
        dto.setTotalPrice(total);

        return dto;
    }
}