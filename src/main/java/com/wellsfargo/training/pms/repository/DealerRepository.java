package com.wellsfargo.training.pms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wellsfargo.training.pms.model.Dealer;

public interface DealerRepository extends JpaRepository<Dealer, Long> {
	
	// Custom method to fetch record/object based on email field
	public Optional<Dealer> findByEmail(String email);

}
