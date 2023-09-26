package com.wellsfargo.training.ims.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import com.wellsfargo.training.ims.model.Product;
import com.wellsfargo.training.ims.service.ProductService;

@DisplayName("Testing ProductController Methods")
@SpringBootTest
class ProductControllerTest {
	
	@Autowired
	private ProductController productController; // instance of class to be tested
	
	private Product p;
	
	@MockBean
	private ProductService pservice; /// Create a mock object of dependencies

	@BeforeEach
	void setUp() throws Exception {
		p = new Product();
	}

	@AfterEach
	void tearDown() throws Exception {
		p = null;
	}

	@Test
	void testSaveProduct() {
		p.setName("Sound Bar");
		p.setBrand("Boat");
		p.setMadeIn("India");
		p.setPrice(5000.0f);
		
		// mockito methods
		// enables stubbing methods
		when(pservice.saveProduct(any(Product.class))).thenReturn(p);
		
		ResponseEntity<Product> re = productController.saveProduct(p);
		
		assertEquals(HttpStatus.CREATED, re.getStatusCode());
		assertEquals("Sound Bar", re.getBody().getName());
		assertEquals("Boat", re.getBody().getBrand());
		assertEquals("India", re.getBody().getMadeIn());
		assertEquals(5000.0f, re.getBody().getPrice());
		
		// verify method is used to check whether some specified methods are called or not
		verify(pservice, times(1)).saveProduct(any(Product.class));
	}

	@Test
	void testGetAllProducts() {
		List<Product> mockProducts = new ArrayList<Product>();
		mockProducts.add(new Product(1L, "Pen", "Reynolds", "India", 20.0f));
		mockProducts.add(new Product(2L, "HDD", "Seagate", "India", 5000.0f));
		
		when(pservice.listAll()).thenReturn(mockProducts);
		
		ResponseEntity<List<Product>> responseProducts = productController.getAllProducts();
		
		assertEquals(2, responseProducts.getBody().size());
		assertEquals("Pen", responseProducts.getBody().get(0).getName());
		assertEquals("HDD", responseProducts.getBody().get(1).getName());
		
		verify(pservice, times(1)).listAll();
	}

	@Test
	void testGetProductById() throws ResourceNotFoundException {
		Product mockProduct = new Product(2L, "HDD", "Seagate", "India", 5000.0f);
		
		when(pservice.getSingleProduct(2L)).thenReturn(Optional.of(mockProduct));
		
		ResponseEntity<Product> re = productController.getProductById(2L);
		
		assertEquals(HttpStatus.OK, re.getStatusCode());
		assertEquals("HDD", re.getBody().getName());
		assertEquals("Seagate", re.getBody().getBrand());
		assertEquals("India", re.getBody().getMadeIn());
		assertEquals(5000.0f, re.getBody().getPrice());
		
		verify(pservice, times(1)).getSingleProduct(2L);
	}

	@Test
	void testUpdateProduct() throws ResourceNotFoundException {
		Product existingProduct = new Product(2L, "HDD", "Seagate", "India", 5000.0f);
		Product updatedProduct = new Product(2L, "HDD", "Seagate", "USA", 7000.0f);
		
		when(pservice.getSingleProduct(2L)).thenReturn(Optional.of(existingProduct));
		when(pservice.saveProduct(any(Product.class))).thenReturn(updatedProduct);
		
		ResponseEntity<Product> re = productController.updateProduct(2L, updatedProduct);
		
		assertEquals(HttpStatus.OK, re.getStatusCode());
		assertEquals("HDD", re.getBody().getName());
		assertEquals("Seagate", re.getBody().getBrand());
		assertEquals("USA", re.getBody().getMadeIn());
		assertEquals(7000.0f, re.getBody().getPrice());
		
		verify(pservice, times(1)).getSingleProduct(2L);
		verify(pservice, times(1)).saveProduct(any(Product.class));
	}

	@Test
	void testDeleteProduct() throws ResourceNotFoundException {
		Product existingProduct = new Product(2L, "HDD", "Seagate", "India", 5000.0f);
		
		when(pservice.getSingleProduct(2L)).thenReturn(Optional.of(existingProduct));
		doNothing().when(pservice).deleteProduct(2L); // for setting void methods
		
		ResponseEntity<Map<String,Boolean>> response = productController.deleteProduct(2L);
		
		assertTrue(response.getBody().containsKey("Deleted"));
		assertTrue(response.getBody().get("Deleted"));
		
		verify(pservice, times(1)).getSingleProduct(2L);
		verify(pservice, times(1)).deleteProduct(2L);
	}

	@Test
	void testSearchProductsByName_Success() {
		String name="Pen";
		List<Product> mockProducts = new ArrayList<Product>();
		mockProducts.add(new Product(1L, "Pen", "Reynolds", "India", 20.0f));
		
		when(pservice.searchProductsByName(name)).thenReturn(mockProducts);
		
		ResponseEntity<?> re = productController.searchProductsByName(name);
		
		assertNotNull(re);
		assertEquals(HttpStatus.OK, re.getStatusCode());
		assertEquals(mockProducts, re.getBody());
		
		verify(pservice, times(1)).searchProductsByName(name);
	}

	@Test
	void testSearchProductsByName_NoProductsFound() {
		String name="Pencil";
		
		when(pservice.searchProductsByName(name)).thenReturn(new ArrayList<>());
		
		ResponseEntity<?> re = productController.searchProductsByName(name);
		
		assertNotNull(re);
		assertEquals(HttpStatus.NOT_FOUND, re.getStatusCode());
		assertEquals("No Products found with the given name.", re.getBody());
		
		verify(pservice, times(1)).searchProductsByName(name);
	}

}
