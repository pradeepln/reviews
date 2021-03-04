package com.training.dal;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.training.domain.Review;

public interface ReviewRepository extends CrudRepository<Review, Integer>{
	public List<Review> findByProductId(int pid);
}
