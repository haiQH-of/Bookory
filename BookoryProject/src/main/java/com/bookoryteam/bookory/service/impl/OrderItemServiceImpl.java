package com.bookoryteam.bookory.service.impl;

import com.bookoryteam.bookory.model.OrderItem;
import com.bookoryteam.bookory.repository.OrderItemRepository;
import com.bookoryteam.bookory.service.OrderItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

	public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
		this.orderItemRepository = orderItemRepository;
	}

	@Override
	public OrderItem save(OrderItem orderItem) {
		return orderItemRepository.save(orderItem);
	}

	@Override
	public Optional<OrderItem> findById(Long id) {
		return orderItemRepository.findById(id);
	}

	@Override
	public List<OrderItem> findAll() {
		return orderItemRepository.findAll();
	}

	@Override
	public void deleteById(Long id) {
		orderItemRepository.deleteById(id);
	}

	@Override
	public List<OrderItem> findByOrderId(Long orderId) {
		return orderItemRepository.findByOrderId(orderId);
	}

	@Override
	public List<OrderItem> findByBookId(Long bookId) {
		return orderItemRepository.findByBookId(bookId);
	}

	@Override
	public List<OrderItem> findByQuantityGreaterThanEqual(Integer quantity) {
		return orderItemRepository.findByQuantityGreaterThanEqual(quantity);
	}

	@Override
	public List<OrderItem> findByPriceAtPurchase(Double priceAtPurchase) {
		return orderItemRepository.findByPriceAtPurchase(priceAtPurchase);
	}

	// ✅ TỔNG SÁCH ĐÃ BÁN
	@Override
	public int sumQuantitySold() {
		Integer result = orderItemRepository.sumQuantitySold();
		return result != null ? result : 0;
	}

	// ✅ TỔNG DOANH THU
	@Override
	public double sumRevenue() {
		Double result = orderItemRepository.sumRevenue();
		return result != null ? result : 0.0;
	}

	@Override
	public int countBooksSold() {
		Integer count = orderItemRepository.countTotalBooksSold();
		return count != null ? count : 0;
	}
}
