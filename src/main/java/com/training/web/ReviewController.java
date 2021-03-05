package com.training.web;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.training.dal.ReviewRepository;
import com.training.domain.Review;

@RestController
public class ReviewController {

	@Autowired
	ReviewRepository dao;

	@Autowired
	DiscoveryClient dc;
	
	@Autowired
	ProductService service;
	
	// add a review to a product
	@PostMapping("/reviews")
	public ResponseEntity<Review> addReview(@RequestBody Review r) {
		
		int pid = r.getProductId();
		
		try {
			Product p = service.getProductById(pid);
			if(p.getId() == -1) {
				System.out.println("Looks like circuit broken............. Falling back...........");
				r.setVerified(false);
			}else {
				r.setVerified(true);
			}
			Review added = dao.save(r);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create("/reviews/"+added.getId()));
			return new ResponseEntity<>(added, headers, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// retrieve reviews of a a product
	@GetMapping("/reviews") // /reviews?pid=1
	public List<Review> getReviewsForProductId(@RequestParam("pid") int pid){
		return dao.findByProductIdAndVerified(pid,true);
	}
	
}
