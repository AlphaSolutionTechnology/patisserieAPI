package com.alphasolutions.patisserie.model.repository;

import com.alphasolutions.patisserie.model.entities.Order;
import com.alphasolutions.patisserie.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByOrderCode(String orderCode);

    Order findByUser(User user);

    List<Order> findAllByUser(User byUserCode);
}
