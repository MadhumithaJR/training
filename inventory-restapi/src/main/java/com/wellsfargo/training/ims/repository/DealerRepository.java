package com.wellsfargo.training.ims.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wellsfargo.training.ims.model.Dealer;
import com.wellsfargo.training.ims.model.DealerAndAddressProjection;

/**
 * 
 * @author rajgs
 * JPA Repository is mainly used for managing the data in a Spring Boot Application. 
 * JpaRepository is particularly a JPA specific extension for Repository.
 * Jpa Repository contains the APIs for basic CRUD operations, the APIS for 
 * pagination, and the APIs for sorting.
 * This Layer interacts with Database
*/

public interface DealerRepository extends JpaRepository<Dealer, Long>{

	// Optional is used to deal with Null Pointer Exception
	// Custom method to fetch record/object based on email field - non id field
	
	public Optional<Dealer> findByEmail(String email);
	
	/*Some time case arises, where we need a custom query to fulfil one test case like  you use more than 2-3 
	 * query parameters or need to define multiple joins to other entities. 
	* We can use @Query annotation to specify a query within a repository.
	* In these situations, you better use Spring Data JPA’s @Query annotation to 
	* specify a custom JPQL or native SQL query.
		* 
	*  */
	
	@Query("SELECT new com.wellsfargo.training.ims.model.DealerAndAddressProjection"
			+ "(d.id, d.fname, d.lname, d.phoneNo, d.email,"
			+ "a.street, a.city, a.pincode)"
			+ "FROM Dealer d JOIN d.address a")
	List<DealerAndAddressProjection> findSelectedFieldsFromDealerAndAddress();
		
}
