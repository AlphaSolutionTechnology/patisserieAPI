package com.alphasolutions.patisserie.service;

import com.alphasolutions.patisserie.Enum.OrderStatus;
import com.alphasolutions.patisserie.mappers.OrderItemMapper;
import com.alphasolutions.patisserie.mappers.OrderMapper;
import com.alphasolutions.patisserie.model.dto.OrderItemDTO;
import com.alphasolutions.patisserie.model.dto.OrderRequestDTO;
import com.alphasolutions.patisserie.model.dto.OrderResponseDTO;
import com.alphasolutions.patisserie.model.entities.Order;
import com.alphasolutions.patisserie.model.entities.OrderItem;
import com.alphasolutions.patisserie.model.entities.Product;
import com.alphasolutions.patisserie.model.repository.OrderItemRepository;
import com.alphasolutions.patisserie.model.repository.OrderRepository;
import com.alphasolutions.patisserie.model.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderItemRepository orderItemRepository,
                            OrderItemMapper orderItemMapper,
                            UserRepository userRepository,
                            OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO order) {
        Random random = new Random();
        String randomValue;
        do {
            randomValue = String.format("%06d", random.nextInt(1_000_000));
        } while (orderRepository.findByOrderCode(randomValue).isPresent());

        Order orderEntity = new Order();
        orderEntity.setOrderCode(randomValue);
        orderEntity.setUser(userRepository.findUserByUserCode(order.getUserCode()));
        orderEntity.setDateTime(order.getDateTime());
        orderEntity.setPaymentMethod(order.getPaymentMethod());
        orderEntity.setAddress(order.getAddress());
        orderEntity.setOrderStatus(OrderStatus.preparing.status);
        Order thisOrder = orderRepository.save(orderEntity);

        // Mapear OrderItemDTO para OrderItem e salvar
        List<OrderItem> orderItemList = order.getItems().stream()
                .map(itemDTO -> orderItemMapper.fromDTO(itemDTO, thisOrder))
                .toList();
        for (OrderItem orderItem : orderItemList) {
            System.out.println(orderItem);
        }
        orderItemRepository.saveAll(orderItemList);

        // Mapear para ProductDTO com quantidade
        List<OrderResponseDTO.ProductDTO> productDTOs = new ArrayList<>();
        for (OrderItemDTO itemDTO : order.getItems()) {
            Product product = orderItemMapper.getProductRepository()
                    .findById(itemDTO.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto com ID " + itemDTO.getId() + " não encontrado."));
            productDTOs.add(new OrderResponseDTO.ProductDTO(product, itemDTO.getQuantity()));
        }

        // Criar e preencher o OrderResponseDTO
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setName(orderEntity.getUser().getUsername());
        orderResponseDTO.setOrderCode(orderEntity.getOrderCode());
        orderResponseDTO.setOrderStatus(OrderStatus.preparing.name()); // Usando name() para string
        orderResponseDTO.setOrderDate(orderEntity.getDateTime());
        orderResponseDTO.setProducts(productDTOs);
        orderResponseDTO.setAddress(orderEntity.getAddress());

        // Calcular totalPrice a partir dos OrderItems salvos
        List<OrderItem> savedOrderItems = orderItemRepository.findByOrderId(thisOrder.getId());
        orderResponseDTO.setTotalPrice(
                savedOrderItems.stream()
                        .filter(item -> item.getUnitPrice() != null && item.getQuantity() != null)
                        .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                        .sum()
        );

        return orderResponseDTO;
    }

    @Override
    public List<OrderResponseDTO> getAllOrders(String userCode) {
        List<Order> orderList = orderRepository.findAllByUser(userRepository.findByUserCode(userCode));

        List<OrderResponseDTO> orderResponseList = new ArrayList<>();
        for (Order order : orderList) {
            orderResponseList.add(orderMapper.toResponseDTO(order, orderItemRepository.findByOrderId(order.getId())));
        }
        return orderResponseList;
    }
}