package com.alphasolutions.patisserie.mappers;

import com.alphasolutions.patisserie.model.dto.OrderItemDTO;
import com.alphasolutions.patisserie.model.dto.OrderResponseDTO;
import com.alphasolutions.patisserie.model.entities.Order;
import com.alphasolutions.patisserie.model.entities.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderResponseDTO toResponseDTO(Order order, List<OrderItem> orderItems) {
        List<OrderItemDTO> itemDTOs = orderItems.stream()
                .map(item -> {
                    OrderItemDTO dto = new OrderItemDTO();
                    dto.setId(item.getProduct().getId());
                    dto.setQuantity(item.getQuantity());
                    return dto;
                })
                .toList();

        double total = orderItems.stream()
                .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                .sum();

        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setName(order.getUser().getUsername());
        dto.setQuantity(orderItems.stream().mapToInt(OrderItem::getQuantity).sum());
        dto.setTotalPrice(total);
        dto.setOrderCode(order.getOrderCode());
        dto.setOrderDate(order.getDateTime());
        dto.setOrderItems(itemDTOs);

        return dto;
    }
}
