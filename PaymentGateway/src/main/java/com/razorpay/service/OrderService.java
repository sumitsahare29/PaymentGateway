package com.razorpay.service;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.entity.Order;
import com.razorpay.repo.OrderRepo;

@Service
public class OrderService {

	@Autowired
	private OrderRepo orderRepo;

	@Value("${razorpay.key.id}")
	private String razorpayId;

	@Value("${razorpay.key.secret}")
	private String razorpaySecret;

	private RazorpayClient razorpayClient;

//	@PostConstruct
//	public void init() throws RazorpayException {
//		this.razorpayClient = new RazorpayClient(razorpayId, razorpaySecret);
//	}

	public Order createOrder(Order order) throws RazorpayException {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("amount", order.getAmount() * 100);
		jsonObject.put("currency", "INR");
		jsonObject.put("receipt", order.getEmail());

		this.razorpayClient = new RazorpayClient(razorpayId, razorpaySecret);

		com.razorpay.Order razorpayOrder = razorpayClient.orders.create(jsonObject);

		if (razorpayOrder != null) {
			order.setRazorpayOrderId(razorpayOrder.get("id"));
			order.setOrderStatus(razorpayOrder.get("status"));
		}

		return orderRepo.save(order);
	}

	public Order updateCallback(Map<String, String> map) {
		String razorpayId = map.get("razorpay_order_id");
		Order order = orderRepo.findByRazorpayOrderId(razorpayId);
		order.setOrderStatus("PAYMENT DONE");
		return orderRepo.save(order);
	}

}
