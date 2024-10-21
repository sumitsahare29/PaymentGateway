package com.razorpay.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.razorpay.entity.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {
	Order findByRazorpayOrderId(String razorpayOrderId);
}
