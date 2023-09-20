package com.wellsfargo.training.ims.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellsfargo.training.ims.model.Dealer;
import com.wellsfargo.training.ims.model.DealerAndAddressProjection;
import com.wellsfargo.training.ims.repository.DealerRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DealerService {
	
	@Autowired //DI
	private DealerRepository drepo;
	
	public Dealer registerDealer(Dealer d) {
		return drepo.save(d); // save the dealer object using save() method of JPA repo
	}
	
	public Optional<Dealer> loginDealer(String email) {
		return drepo.findByEmail(email); // Invoked Custom Method
	}
	
	public List<DealerAndAddressProjection> getDealerInfo() {
		return drepo.findSelectedFieldsFromDealerAndAddress();
	}

}
