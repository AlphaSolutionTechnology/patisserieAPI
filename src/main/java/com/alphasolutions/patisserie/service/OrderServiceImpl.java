package com.alphasolutions.patisserie.service;

import com.alphasolutions.patisserie.Enum.OrderStatus;
import com.alphasolutions.patisserie.mappers.OrderItemMapper;
import com.alphasolutions.patisserie.mappers.OrderMapper;
import com.alphasolutions.patisserie.model.dto.OrderRequestDTO;
import com.alphasolutions.patisserie.model.dto.OrderResponseDTO;
import com.alphasolutions.patisserie.model.entities.Order;
import com.alphasolutions.patisserie.model.entities.OrderItem;
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
                            OrderItemMapper orderItemMapper, UserRepository userRepository, OrderMapper orderMapper) {
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

        List<OrderItem> orderItemList = order.getItems().stream()
                .map(itemDTO -> orderItemMapper.fromDTO(itemDTO, orderEntity))
                .toList();
        orderItemRepository.saveAll(orderItemList);
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setName(orderEntity.getUser().getUsername());
        orderResponseDTO.setOrderCode(orderEntity.getOrderCode());
        orderResponseDTO.setOrderStatus(OrderStatus.preparing);
        orderResponseDTO.setOrderItems(order.getItems());
        orderResponseDTO.setOrderDate(orderEntity.getDateTime());
        List<OrderItem> orderItems = (orderItemRepository
                .findByOrderId(thisOrder.getId()));
        orderResponseDTO.setTotalPrice(
                orderItems
                .stream()
                .map(OrderItem :: getUnitPrice)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .sum()
                );

        return orderResponseDTO;
  }

    @Override
    public List<OrderResponseDTO> getAllOrders(String userCode) {
        List<Order> orderList = orderRepository.findAllByUser(userRepository.findByUserCode((userCode)));

        List<OrderResponseDTO> orderResponseList = new ArrayList<>();
        int i = 0;
        while (i < orderList.size()) {
           orderResponseList.add(orderMapper.toResponseDTO(orderList.get(i),orderItemRepository.findByOrderId(orderList.get(i).getId())));
        }
        return orderResponseList;
    }
}
