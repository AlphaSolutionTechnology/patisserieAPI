package com.alphasolutions.patisserie.mappers;
import com.alphasolutions.patisserie.model.dto.OrderItemDTO;
import com.alphasolutions.patisserie.model.entities.Order;
import com.alphasolutions.patisserie.model.entities.OrderItem;
import com.alphasolutions.patisserie.model.entities.Product;
import com.alphasolutions.patisserie.model.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderItemMapper {

    private final ProductRepository productRepository;

    public OrderItemMapper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public OrderItem fromDTO(OrderItemDTO dto, Order order) {
        Product product = productRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Produto com ID " + dto.getId() + " não encontrado."));

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setUnitPrice(product.getPrice());

        return orderItem;
    }
}
