package com.wellsfargo.training.pms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wellsfargo.training.pms.exception.ResourceNotFoundException;
import com.wellsfargo.training.pms.model.Product;
import com.wellsfargo.training.pms.service.ProductService;

/*Spring RestController annotation is used to create RESTful web services using Spring MVC. 
 * Spring RestController takes care of mapping request data to the defined request handler method. 
 * Once response body is generated from the handler method, it converts it to JSON or XML response. 
 * 
 * @RequestMapping - maps HTTP request with a path to a controller 
 * */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value="/api")
public class ProductController {

	@Autowired
	private ProductService pservice;

	
	/* ResponseEntity represents an HTTP response, including headers, body, and status.
	 *  @RequestBody annotation automatically deserializes the JSON into a Java type
	 *  */
	
	//Open PostMan, make a POST Request - http://localhost:8085/pms/api/products/
    //Select body -> raw -> JSON 
    //Insert JSON product object.
	
	@PostMapping("/products")
	public Product saveProduct(@Validated @RequestBody Product p) {
		Product p2 = pservice.saveProduct(p);
		return p2;
	}
	
	//Open PostMan, make a GET Request - http://localhost:8085/pms/api/products/
    //Select body -> raw -> JSON
	@GetMapping("/products")
	public List<Product> getAllProducts(){
		
		return pservice.listAll();
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable(value="id") Long pId) throws ResourceNotFoundException{
		Product p=pservice.getSingleProduct(pId).orElseThrow(() -> new ResourceNotFoundException("Product not found for this Id :"+pId));
		return ResponseEntity.ok().body(p);
	}
	
	@PutMapping("/products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable(value="id") Long pId, @Validated @RequestBody Product p) throws ResourceNotFoundException{
		Product product=pservice.getSingleProduct(pId).orElseThrow(() -> new ResourceNotFoundException("Product not found for this Id :"+pId));
		product.setBrand(p.getBrand());
		product.setMadein(p.getMadein());
		product.setName(p.getName());
		product.setPrice(p.getPrice());
		
		final Product updateProduct=pservice.saveProduct(product);
		return ResponseEntity.ok().body(updateProduct);
	}
	
	@DeleteMapping("/products/{id}")
	public Map<String, Boolean> deleteProduct(@PathVariable(value="id") Long pId) throws ResourceNotFoundException{
		Product p=pservice.getSingleProduct(pId).orElseThrow(() -> new ResourceNotFoundException("Product not found for this Id :"+pId));
		pservice.deleteProduct(pId);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", Boolean.TRUE);
		return response;
	
	}
	
}
