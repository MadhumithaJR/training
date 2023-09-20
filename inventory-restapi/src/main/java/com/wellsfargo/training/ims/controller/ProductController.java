package com.wellsfargo.training.ims.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wellsfargo.training.ims.exception.ResourceNotFoundException;
import com.wellsfargo.training.ims.model.Product;
import com.wellsfargo.training.ims.service.ProductService;

/*
 * Spring RestController annotation is used to create RESTful web services using Spring MVC. 
 * Spring RestController takes care of mapping request data to the defined request handler method. 
 * Once response body is generated from the handler method, it converts it to JSON or XML response. 
 * 
 * @RequestMapping - maps HTTP request with a path to a controller 
 *
 */

@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping(value="/api")
public class ProductController {

	// POSTMAN - REST API Testing Tool

	/*Open PostMan, make a POST Request - http://localhost:8085/ims/api/products/
	    Select body -> raw -> JSON 
	  Insert JSON product object.*/
	
	@Autowired
	private ProductService pservice;
	
	@PostMapping("/products")
	public ResponseEntity<Product> saveProduct(@Validated @RequestBody Product product) {
		try {
			Product p=pservice.saveProduct(product);
			return ResponseEntity.status(HttpStatus.CREATED).body(p);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts() {
		try {
			List<Product> products = pservice.listAll();
			return ResponseEntity.ok(products);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	/* @PathVariable is a Spring Annotation which indicates that a method parameter should be bound to a URI template variable.
	 * @PathVariable annotation is used to read a URL template variable.
	 */
	
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable(value="id") Long pId)
	throws ResourceNotFoundException {
		Product p=pservice.getSingleProduct(pId).
				orElseThrow(() -> new ResourceNotFoundException("Product Not Found For This ID: "+pId));
		return ResponseEntity.ok().body(p);
	}
	
	// Update JSON product object with new values.
	
	@PutMapping("/products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable(value="id") Long pId,
			@Validated @RequestBody Product p)
	throws ResourceNotFoundException {
		Product product=pservice.getSingleProduct(pId).
				orElseThrow(() -> new ResourceNotFoundException("Product Not Found For This ID: "+pId));
		
		// update product with new values
		product.setBrand(p.getBrand());
		product.setMadeIn(p.getMadeIn());
		product.setName(p.getName());
		product.setPrice(p.getPrice());
		
		final Product updatedProduct=pservice.saveProduct(product);
		return ResponseEntity.ok().body(updatedProduct);
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteProduct(@PathVariable(value="id") Long pId)
		throws ResourceNotFoundException {
		pservice.getSingleProduct(pId).
		orElseThrow(() -> new ResourceNotFoundException("Product Not Found For This ID: "+pId));
		
		pservice.deleteProduct(pId);
		
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("Deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/products/search")
	public ResponseEntity<?> searchProductsByName(@RequestParam("name") String name) {
		try {
			List<Product> products = pservice.searchProductsByName(name);
			
			if(products.isEmpty()) {
				return new ResponseEntity<>("No Products found with the given name.", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(products, HttpStatus.OK);
		} catch(Exception ex) {
			// Database error
			return new ResponseEntity<>("Database Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/test")
	public String sayHello() {
		return "Hi!";
	}
}
