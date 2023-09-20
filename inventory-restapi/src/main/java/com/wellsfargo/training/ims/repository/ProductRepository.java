package com.wellsfargo.training.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wellsfargo.training.ims.model.Product;

// Long is the data type of id field in Product class
public interface ProductRepository extends JpaRepository<Product, Long>{

	 /*
     * This interface has save(),findAll(),findById(),deleteById(),count()
       etc.. inbuilt methods of jpa repository for various database operations.
       This interface will be implemented by class automatically
    */
	
	/*
	 * @Query specifies that you're providing a custom JPQL query.
	 * We use the REPLACE function to remove spaces both from the p.name 
	 * field and from the provided :name, making them both single continuous strings with no spaces.
	 * JPQL query that selects products where the lowercase name 
	 * contains the lowercase input name with wildcards. (% - all products that start with the given name)
	 * @Param("name") is used to bind the name parameter from the 
	 * method signature to the :name placeholder in the query
	 */
	
	// Custom Query with parameters
	@Query("SELECT p FROM Product p WHERE LOWER(REPLACE(p.name,' ','')) LIKE " // Replacing space in p.name with no space
			+ "LOWER(CONCAT(REPLACE(:name,' ',''),'%'))") // Replacing space with no space in the name we provide
	List<Product> findProductsByNameContainingIgnoreCase(@Param("name") String name);
}
