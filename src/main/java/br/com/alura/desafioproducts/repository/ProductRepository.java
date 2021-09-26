package br.com.alura.desafioproducts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.desafioproducts.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

	@Query(value = "SELECT p.id, p.name, p.description, p.price FROM Product p WHERE (UPPER(:q) IS NULL OR (UPPER(p.name) LIKE UPPER( CONCAT('%', :q, '%') ) OR UPPER(p.description) LIKE UPPER( CONCAT('%', :q, '%') )))"
			+ "AND (:minPrice IS NULL OR p.price >= :minPrice)"
			+ "AND (:maxPrice IS NULL OR p.price <= :maxPrice)", nativeQuery = true)
	List<Product> search(String q, Double minPrice, Double maxPrice);

}
