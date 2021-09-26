package br.com.alura.desafioproducts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.desafioproducts.model.Product;
import br.com.alura.desafioproducts.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public List<Product> searchProducts(String description, Double min_price, Double max_price) {
		return productRepository.search(description, min_price, max_price);
	}

	public Product findById(String id) {
		return productRepository.findById(id).orElseThrow();
	}

	public Product save(Product product) {
		productRepository.save(product);
		return product;
	}

	public Product update(Product product, String id) {
		productRepository.findById(id).orElseThrow();
		product.setId(id);
		productRepository.save(product);
		return product;
	}

	public void delete(String id) {
		productRepository.deleteById(id);
	}

}
