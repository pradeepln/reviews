package com.training.web;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@FeignClient(name = "productservice",fallback = FallBackProductService.class)
public interface ProductService {
	
	@GetMapping("/products/{id}")
	public Product getProductById(@PathVariable("id") int id);

}
