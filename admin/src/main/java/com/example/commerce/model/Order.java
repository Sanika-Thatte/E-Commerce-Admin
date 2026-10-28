package com.example.commerce.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    private LocalDateTime orderdate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderitem = new ArrayList<>();

	public Order(int orderId, LocalDateTime orderdate, User user, List<OrderItem> orderitem) {
		super();
		this.orderId = orderId;
		this.orderdate = orderdate;
		this.user = user;
		this.orderitem = orderitem;
	}

	public Order() {
		super();
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
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

	public List<OrderItem> getOrderitem() {
		return orderitem;
	}

	public void setOrderitem(List<OrderItem> orderitem) {
		this.orderitem = orderitem;
	}
    
    

}