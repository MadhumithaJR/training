package com.wellsfargo.training.ims.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

/*
 * Model class for managing Product Objects
 * The @Entity annotation specifies that the class is an entity and is mapped to a database table.
 */

@Entity
public class Product {
	
	/* The @Id annotation specifies the primary key of an entity and the @GeneratedValue provides for 
	 * the specification of generation strategies for the values of primary keys. */

	@SequenceGenerator(name="product_seq", initialValue = 1000, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "product_seq")
	private Long pid;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String brand;
	
	@Column(nullable = false)
	private String madeIn;
	
	@Column(nullable = false)
	private float price;
	
	public Product() {
		super();
		
	}

	public Product(Long pid, String name, String brand, String madeIn, float price) {
		super();
		this.pid = pid;
		this.name = name;
		this.brand = brand;
		this.madeIn = madeIn;
		this.price = price;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getMadeIn() {
		return madeIn;
	}

	public void setMadeIn(String madeIn) {
		this.madeIn = madeIn;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	
}
