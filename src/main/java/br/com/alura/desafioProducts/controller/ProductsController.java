package br.com.alura.desafioProducts.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.desafioProducts.model.Product;
import br.com.alura.desafioProducts.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	private ProductService productService;

	@GetMapping
	public ResponseEntity<List<Product>> findAllProducts() {
		return productService.findAll();
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> findProductById(@PathVariable("id") String id) {
		return productService.findById(id);
	}

	@PostMapping
	public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product, UriComponentsBuilder uriBuilder) {
		return productService.save(product, uriBuilder);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable("id") String id) {
		return productService.delete(id);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") String id, @RequestBody @Valid Product product) {
		return productService.update(product, id);
	}
	
	@GetMapping(path = "/search")
	public List<Product> search(
			@RequestParam(required = false) String q,
			@RequestParam(required = false) Double min_price,
			@RequestParam(required = false) Double max_price) {
		
		//Collection<Product> listProducts = productService.search(q, min_price, max_price);
		return productService.searchProducts(q, min_price, max_price);
	}

}
