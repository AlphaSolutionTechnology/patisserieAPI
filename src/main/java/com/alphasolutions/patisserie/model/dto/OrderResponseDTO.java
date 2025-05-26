package com.alphasolutions.patisserie.model.dto;

import com.alphasolutions.patisserie.model.entities.Product;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponseDTO {
    private String name;
    private String orderCode;
    private String orderStatus;
    private OffsetDateTime orderDate;
    private List<ProductDTO> products;
    private Double totalPrice;

    @Getter
    @Setter
    public static class ProductDTO {
        private Integer id;
        private String name;
        private Double price;
        private String category;
        private String imagePath;
        private Integer quantity;
        private String description;

        public ProductDTO(Product product, Integer quantity) {
            this.id = product.getId();
            this.name = product.getName();
            this.price = product.getPrice();
            this.category = product.getCategory();
            this.imagePath = product.getImagePath();
            this.quantity = quantity;
            this.description = product.getDescription();
        }
    }
}