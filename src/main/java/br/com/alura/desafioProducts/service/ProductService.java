package br.com.alura.desafioProducts.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.desafioProducts.model.Product;
import br.com.alura.desafioProducts.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public ResponseEntity<List<Product>> findAll() {
		return ResponseEntity.ok().body(productRepository.findAll());
	}
	
	public List<Product> searchProducts(String description, Double min_price, Double max_price) {

		
		
		return productRepository.search(description, min_price, max_price);
	}


	public ResponseEntity<Product> findById(String id) {

		return ResponseEntity.of(productRepository.findById(id));
	}

	public ResponseEntity<Product> save(Product product, UriComponentsBuilder uriBuilder) {
		productRepository.save(product);
		URI uri = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();
		return ResponseEntity.created(uri).body(product);
	}

	public ResponseEntity<Product> update(Product product, String id) {
		Optional<Product> productDb = productRepository.findById(id);
		if (productDb.isEmpty()) {
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		}
		product.setId(id);

		productRepository.save(product);
		return ResponseEntity.ok().body(product);
	}

	public ResponseEntity<Product> delete(String id) {
		try {
			productRepository.deleteById(id);
			return ResponseEntity.ok().build();
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
