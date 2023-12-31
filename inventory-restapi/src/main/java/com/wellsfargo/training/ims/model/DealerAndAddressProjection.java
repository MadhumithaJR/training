package com.wellsfargo.training.ims.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Template Class to fetch Objects from 2 Classes using Custome Queries
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DealerAndAddressProjection {
	
	private Long id;
	private String fname;
	private String lname;
	private String phoneNo;
	private String email;
	private String street;
	private  String city;
	private  int pincode;

}
