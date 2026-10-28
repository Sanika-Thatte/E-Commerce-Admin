package com.example.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.customer.model.Order;
import com.example.customer.service.OrderService;

@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/order")
	public String myOrders(Model model, @AuthenticationPrincipal UserDetails userDetails) {

	    String username = userDetails.getUsername();

	    List<Order> orders = orderService.getOrdersForUser(username);

	    model.addAttribute("orders", orders);

	    return "order";
	}

}
