package com.alphasolutions.patisserie.model.dto;

import com.alphasolutions.patisserie.Enum.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private String name;
    private Integer quantity;
    private Double totalPrice;
    private LocalDate orderDate;
    private String orderCode;
    private List<OrderItemDTO> orderItems;
    private OrderStatus orderStatus;
}
