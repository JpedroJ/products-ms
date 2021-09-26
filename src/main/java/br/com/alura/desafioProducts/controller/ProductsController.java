package br.com.alura.desafioproducts.controller;

import java.net.URI;
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

import br.com.alura.desafioproducts.model.Product;
import br.com.alura.desafioproducts.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	private ProductService productService;

	@GetMapping
	public ResponseEntity<List<Product>> findAllProducts() {
		return ResponseEntity.ok().body(productService.findAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> findProductById(@PathVariable("id") String id) {
		return ResponseEntity.ok(productService.findById(id));
	}

	@PostMapping
	public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product, UriComponentsBuilder uriBuilder) {
		productService.save(product);
		URI uri = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();
		return ResponseEntity.created(uri).body(product);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable("id") String id) {
		productService.delete(id);
		return ResponseEntity.ok().build();

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") String id, @RequestBody @Valid Product product) {
		productService.update(product, id);
		return ResponseEntity.ok(product);
	}

	@GetMapping(path = "/search")
	public List<Product> search(@RequestParam(required = false, name = "q") String description,
			@RequestParam(required = false, name = "min_price") Double minPrice,
			@RequestParam(required = false, name = "max_price") Double maxPrice) {
		return productService.searchProducts(description, minPrice, maxPrice);
	}

}
