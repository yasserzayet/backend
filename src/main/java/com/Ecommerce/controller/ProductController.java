package com.Ecommerce.controller;

import com.Ecommerce.exception.NotFoundException;
import com.Ecommerce.model.Product;
import com.Ecommerce.repository.CategoryRepository;
import com.Ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping("/product")
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	@GetMapping("/category/{categoryId}/product")
	public List<Product> getProductByCategoryId(@PathVariable Long categoryId) {
    	
        if(!categoryRepository.existsById(categoryId)) {
            throw new NotFoundException("Category not found!");
        }

    	return productRepository.findByCategoryId(categoryId);
    }
	
	@PostMapping("/category/{categoryId}/product")
	public Product addProduct(@PathVariable Long categoryId,
                          @Valid @RequestBody Product product) {
    	return categoryRepository.findById(categoryId)
            .map(category -> {
                product.setCategory(category);
                return productRepository.save(product);
            }).orElseThrow(() -> new NotFoundException("Category not found!"));
}

    @GetMapping("/category/{categoryId}/product/{productId}")
    public Product getProduct(@PathVariable Long categoryId,
                                    @PathVariable Long productId,
                                    @Valid @RequestBody Product productUpdated) {

        if(!categoryRepository.existsById(categoryId)) {
            throw new NotFoundException("Category not found!");
        }

        return productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found!"));
    }

	@PutMapping("/category/{categoryId}/product/{productId}")
	public Product updateAssignment(@PathVariable Long categoryId,
									@PathVariable Long productId,
                                @Valid @RequestBody Product productUpdated) {
	
		if(!categoryRepository.existsById(categoryId)) {
			throw new NotFoundException("Category not found!");
		}

		return productRepository.findById(productId)
            .map(product -> {
            	product.setName(productUpdated.getName());
            	product.setDescription(productUpdated.getDescription());
            	product.setPrice(productUpdated.getPrice());
                return productRepository.save(product);
            }).orElseThrow(() -> new NotFoundException("Product not found!"));
    }

	@DeleteMapping("/category/{categoryId}/product/{productId}")
	public String deleteAssignment(@PathVariable Long categoryId,
								   @PathVariable Long productId) {

		if(!categoryRepository.existsById(categoryId)) {
			throw new NotFoundException("Category not found!");
		}

		return productRepository.findById(productId)
            .map(product -> {
            	productRepository.delete(product);
                return "Deleted Successfully!";
            }).orElseThrow(() -> new NotFoundException("Product not found!"));
}
}
