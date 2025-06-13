package com.bookoryteam.bookory.service.impl;

import com.bookoryteam.bookory.model.Order;
import com.bookoryteam.bookory.repository.OrderRepository;
import com.bookoryteam.bookory.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    
    @Override
    public int countOrders() {
        return (int) orderRepository.count();
    }

    @Override
    public int countBooksSold() {
        // Nếu chưa có logic rõ ràng, tạm trả về 0 để tránh lỗi
        return 0;
    }

    @Override
    public double getTotalRevenue() {
        Double revenue = orderRepository.sumTotalRevenue();
        return revenue != null ? revenue : 0.0;
    }

    // Implement các method còn lại tương tự interface


    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> findByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    public List<Order> findByOrderDateBetween(OffsetDateTime startDate, OffsetDateTime endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate);
    }

    @Override
    public List<Order> findByDeletedFalse() {
        return orderRepository.findByDeletedFalse();
    }

    @Override
    public List<Order> findByDeletedTrue() {
        return orderRepository.findByDeletedTrue();
    }

    @Override
    public void softDelete(Long id) {
        orderRepository.findById(id).ifPresent(order -> {
            order.setDeleted(true);
            orderRepository.save(order);
        });
    }
}