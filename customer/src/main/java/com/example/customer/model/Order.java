package com.example.customer.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    
    private String status;         // PENDING, DELIVERED
    private double totalAmount; 

    private LocalDateTime orderdate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Column(name="orderItem")
    private List<OrderItem> items;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public LocalDateTime getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(LocalDateTime orderdate) {
		this.orderdate = orderdate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public Order(int orderId, String status, double totalAmount, LocalDateTime orderdate, User user,
			List<OrderItem> items) {
		super();
		this.orderId = orderId;
		this.status = status;
		this.totalAmount = totalAmount;
		this.orderdate = orderdate;
		this.user = user;
		this.items = items;
	}

	public Order() {
		super();
	}


}