package dev.rafaelreis.rest.domain;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.rafaelreis.rest.interfaces.outcoming.GMapsService;

@Component
public class TravelService {
	
	private static final int MAX_TRAVEL_TIME= 600;
	
	@Autowired
	private TravelRequestRepository travelRequestRepository;
	
	@Autowired
	private GMapsService gMapsService;
	
	public TravelRequest saveTravelRequest(TravelRequest travelRequest) {
		travelRequest.setStatus(TravelRequestStatus.CREATED);
		travelRequest.setCreateDate(new Date());
		return travelRequestRepository.save(travelRequest);
	}
	
	public List<TravelRequest> listNearbyTravelRequests(String currentAddress) {
		List<TravelRequest> requests = travelRequestRepository.findByStatus(TravelRequestStatus.CREATED);
		
		return requests.stream()
				.filter(tr -> gMapsService.getDistanceBetweenAndresses(currentAddress, tr.getOrigin()) < MAX_TRAVEL_TIME)
				.collect(Collectors.toList());
	}
}
