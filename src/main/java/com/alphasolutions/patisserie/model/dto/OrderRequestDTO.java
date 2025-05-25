package com.alphasolutions.patisserie.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class OrderRequestDTO {
    private OffsetDateTime dateTime;
    private List<OrderItemDTO> items;
    private Integer paymentMethod;
    private String address;
    private String userCode;
}
