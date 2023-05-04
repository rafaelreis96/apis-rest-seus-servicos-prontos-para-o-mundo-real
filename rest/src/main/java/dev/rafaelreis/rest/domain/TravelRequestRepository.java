package dev.rafaelreis.rest.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelRequestRepository extends JpaRepository<TravelRequest, Long>{
	
	public List<TravelRequest> findByStatus(TravelRequestStatus status);

}
