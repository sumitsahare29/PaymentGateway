package com.razorpay.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.razorpay.RazorpayException;
import com.razorpay.entity.Order;
import com.razorpay.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/order")
	public String ordersPage() {
		return "order";
	}

	@PostMapping(value = "/createOrder", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Order> createOrder(@RequestBody Order order) throws RazorpayException {
		Order razorpayOrder = orderService.createOrder(order);
		System.out.println(order);
		return new ResponseEntity<>(razorpayOrder, HttpStatus.CREATED);
	}

	@PostMapping("/paymentCallback")
	public String paymentCallback(@RequestParam Map<String, String> map) {
		orderService.updateCallback(map);
		return "success";
	}
}
