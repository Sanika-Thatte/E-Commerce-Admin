package com.example.customer.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="product")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
	
	private String name;
	private int quantity;
	private double price;
	private String image;

	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;

	@OneToMany(mappedBy = "product" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<CartItem> cartitem = new ArrayList<>();
	
	@OneToMany(mappedBy = "product" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<OrderItem> orderitem = new ArrayList<>();

	public Product(int productId, String name, int quantity, double price, String image, Category category,
			List<CartItem> cartitem, List<OrderItem> orderitem) {
		super();
		this.productId = productId;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.image = image;
		this.category = category;
		this.cartitem = cartitem;
		this.orderitem = orderitem;
	}

	public Product() {
		super();
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<CartItem> getCartitem() {
		return cartitem;
	}

	public void setCartitem(List<CartItem> cartitem) {
		this.cartitem = cartitem;
	}

	public List<OrderItem> getOrderitem() {
		return orderitem;
	}

	public void setOrderitem(List<OrderItem> orderitem) {
		this.orderitem = orderitem;
	}

	
	
	

}
