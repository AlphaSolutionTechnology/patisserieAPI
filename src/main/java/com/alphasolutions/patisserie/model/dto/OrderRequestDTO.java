package com.alphasolutions.patisserie.model.dto;

import com.alphasolutions.patisserie.model.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderRequestDTO {
    private LocalDateTime dateTime;
    private List<OrderItemDTO> items;
    private User user;
}
