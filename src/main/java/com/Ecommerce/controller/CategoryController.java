package com.Ecommerce.controller;

import com.Ecommerce.exception.NotFoundException;
import com.Ecommerce.model.Category;
import com.Ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CategoryController {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping("/category")
	public List<Category> getAllCategorys() {
		return categoryRepository.findAll();
	}
	
	@GetMapping("/category/{id}")
	public Category getCategoryById(@PathVariable Long id) {
		Optional<Category> optCategory = categoryRepository.findById(id);
		if (optCategory.isPresent()) {
			return optCategory.get();
		}else {
			throw new NotFoundException("Category not found with id "+id);
		}
	}

	@PostMapping("/category")
	public Category addCategory(@Valid @RequestBody Category category) {
		return categoryRepository.save(category);
	}
	
	@PutMapping("/category/{id}")
	public Category updateCategory(@PathVariable Long id, @Valid @RequestBody Category categoryUpdate) {
		return categoryRepository.findById(id)
				.map(category -> {
					category.setName(categoryUpdate.getName());
					return categoryRepository.save(category);
				}).orElseThrow(() -> new NotFoundException("Category not found with id"+id));

	}
	
	@DeleteMapping("/category/{id}")
    public String deleteCategory(@PathVariable Long id) {
        return categoryRepository.findById(id)
                .map(category -> {
                	categoryRepository.delete(category);
                    return "Delete Successfully!";
                }).orElseThrow(() -> new NotFoundException("Category not found with id " + id));
    }
}
