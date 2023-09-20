package com.ewellsfargo.training.helloworld.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ewellsfargo.training.helloworld.model.Employee;

@RestController
public class HelloWorldController {

	@GetMapping("/")
	public String sayHello() {
		return "Hello World From Spring Boot";
	}
	
	@GetMapping("/mads")
	public String Hello() {
		return "Hello Mads";
	}
	
	@GetMapping("/employees")
	public List<Employee> getEmployees(){
		Employee e1 = new Employee(101, "Rod", "Johnson", "rod@spring.com");
		Employee e2 = new Employee(102, "James", "Gosling", "james@spring.com");
		Employee e3 = new Employee(103, "Raj", "Gs", "raj@spring.com");
		
		List<Employee> empList = new ArrayList<Employee>();
		
		empList.add(e1);
		empList.add(e2);
		empList.add(e3);
		
		return empList;
	}

}
