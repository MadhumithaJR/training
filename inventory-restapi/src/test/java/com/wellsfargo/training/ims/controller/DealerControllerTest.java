package com.wellsfargo.training.ims.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wellsfargo.training.ims.exception.ResourceNotFoundException;
import com.wellsfargo.training.ims.model.Address;
import com.wellsfargo.training.ims.model.Dealer;
import com.wellsfargo.training.ims.model.DealerAndAddressProjection;
import com.wellsfargo.training.ims.service.DealerService;


@SpringBootTest
@DisplayName("Dealer Authentication Tests")
class DealerControllerTest {

	@Autowired
	private DealerController dcontroller; // Instance of class to be tested
	
	Dealer dealer;
	DealerAndAddressProjection dealerInfo1;
	DealerAndAddressProjection dealerInfo2;
	
	@MockBean
	private DealerService dservice; // Create a mock object of DealerService
	
	@BeforeEach
	void setUp() throws Exception {
		dealer = new Dealer();
		dealerInfo1 = new DealerAndAddressProjection();
		dealerInfo2 = new DealerAndAddressProjection()
;	}

	@AfterEach
	void tearDown() throws Exception {
		dealer = null;
		dealerInfo1 = dealerInfo2 = null;
	}

	@Test
	void testCreateDealer() throws ParseException, ResourceNotFoundException {
		dealer.setEmail("jhonson@spring.com");
		dealer.setPassword("Password123");
		dealer.setFname("Rod");
		dealer.setLname("Jhonson");
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dob = new Date(df.parse("1985-01-01").getTime());
		dealer.setDob(dob);
		
		dealer.setPhoneNo("4534453445");
		
		Address address = new Address();
		
		address.setStreet("123 Main Street");
		address.setCity("Bengaluru");
		address.setPincode(560041);
		
		dealer.setAddress(address);
		
		/*
		 * Matchers are like regex or wildcards where instead of a specific input (and or output), 
		 * you specify a range/type of input/output based on which stubs/spies can be rest and calls to stubs can be verified.
		 * Matchers are a powerful tool, which enables a shorthand way of setting up stubs as well as verifying invocations on 
		 * the stubs by mentioning argument inputs as generic types to specific values depending on the use-case or scenario.
		 * 
		 * any(java language class) –

		Example: any(ClassUnderTest.class) – This is a more specific variant of any() 
		and will accept only objects of the class type that’s mentioned as the template parameter.
		 * */
		
		when(dservice.registerDealer(any(Dealer.class))).thenReturn(dealer);
		
		ResponseEntity<String> re = dcontroller.createDealer(dealer);
		
		assertEquals(HttpStatus.OK, re.getStatusCode());
		assertEquals("Registration Successful", re.getBody());
		
		verify(dservice, times(1)).registerDealer(any(Dealer.class));
		
		/*when() Then() method
		It enables stubbing methods. It should be used when we want to mock to return specific values when 
		particular methods are called. In simple terms, "When the XYZ() method is called, 
		then return ABC." It is mostly used when there is some condition to execute.
		 * 
		 * */
		
	}

	@Test
	void testLoginDealer() throws ResourceNotFoundException, ParseException {
		
		dealer.setEmail("jhonson@spring.com");
		dealer.setPassword("Password123");
		dealer.setFname("Rod");
		dealer.setLname("Jhonson");
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dob = new Date(df.parse("1985-01-01").getTime());
		dealer.setDob(dob);
		
		dealer.setPhoneNo("4534453445");
		
		Address address = new Address();
		
		address.setStreet("123 Main Street");
		address.setCity("Bengaluru");
		address.setPincode(560041);
		
		dealer.setAddress(address);
		
		when(dservice.loginDealer("jhonson@spring.com")).thenReturn(Optional.of(dealer));
		
		Dealer x = dservice.loginDealer("jhonson@spring.com").get();
		
		assertEquals(x.getEmail(), dealer.getEmail());
		assertEquals(x.getPassword(), dealer.getPassword());
		
		ResponseEntity<Boolean> result = dcontroller.loginDealer(dealer);
		
		assertTrue(result.getBody());
		
		verify(dservice, times(2)).loginDealer("jhonson@spring.com");
	}

	@Test
	void testGetDealerInfo() {
		List<DealerAndAddressProjection> mockDealerInfo = new ArrayList<>();
		dealerInfo1.setEmail("siya@ims.com");
		dealerInfo1.setFname("Siya");
		dealerInfo1.setLname("gs");
		dealerInfo1.setPhoneNo("9876543210");
		dealerInfo1.setStreet("123 Temple lane");
		dealerInfo1.setCity("Bengaluru");
		dealerInfo1.setPincode(560023);
		
		dealerInfo2.setEmail("naveen@ims.com");
		dealerInfo2.setFname("Naveen");
		dealerInfo2.setLname("Kumar");
		dealerInfo2.setPhoneNo("9766697666");
		dealerInfo2.setStreet("456 Temple lane");
		dealerInfo2.setCity("Bengaluru");
		dealerInfo2.setPincode(560023);
		
		mockDealerInfo.add(dealerInfo1);
		mockDealerInfo.add(dealerInfo2);
		
		when(dservice.getDealerInfo()).thenReturn(mockDealerInfo);
		
		ResponseEntity<List<DealerAndAddressProjection>> re = dcontroller.getDealerInfo();
		
		assertEquals(HttpStatus.OK, re.getStatusCode());
		assertEquals(mockDealerInfo, re.getBody());
		
		verify(dservice, times(1)).getDealerInfo();
		
	}

}
