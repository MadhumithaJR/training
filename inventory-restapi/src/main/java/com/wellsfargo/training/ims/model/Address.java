package com.wellsfargo.training.ims.model;

import jakarta.persistence.*;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.NonNull;
//import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="did")
	private Long addressId;
	
	private @NonNull String street;
	private @NonNull String city;
	private int pincode;
	
	/*
     * Modeling with foreign key relationship in JPA.
     * Place @OneToOne on the primary class entity field Dealer.
     * Place @JoinColumn to configure foreign key column dealer_id in the Address class 
     * that maps to the primary key column of Dealer class. 
     */
	
	@OneToOne
	@JoinColumn(name="dealer_id")
	private Dealer dealer;
	
	

	public Address(@NonNull String street, @NonNull String city, int pincode) {
		super();
		this.street = street;
		this.city = city;
		this.pincode = pincode;
	}



	public Address() {
		super();
	}
	
	

}
