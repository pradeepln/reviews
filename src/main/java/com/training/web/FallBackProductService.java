package com.training.web;

import org.springframework.stereotype.Service;

@Service
public class FallBackProductService implements ProductService {

	@Override
	public Product getProductById(int id) {
		Product p = new Product();
		p.setId(-1);
		return p;
	}

}
