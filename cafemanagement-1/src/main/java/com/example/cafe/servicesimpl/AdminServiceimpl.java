package com.example.cafe.servicesimpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cafe.dtos.CategoryDto;
import com.example.cafe.dtos.ProductDto;
import com.example.cafe.entities.Category;
import com.example.cafe.entities.Product;
import com.example.cafe.repositery.CategoryRepository;
import com.example.cafe.repositery.ProductRepository;
import com.example.cafe.services.AdminService;



@Service

public class AdminServiceimpl implements AdminService{
	
	@Autowired
     CategoryRepository categoryRepository;

	@Autowired
	ProductRepository productRepository;
    @Override

    public CategoryDto postCategory(CategoryDto categoryDto) throws IOException {
        
    	Category category = new Category();
        
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setImg(categoryDto.getImg().getBytes());
        Category createdCategory = categoryRepository.save(category);
        CategoryDto createdCategoryDto = new CategoryDto();
        createdCategoryDto.setId(createdCategory.getId());
        
        
        return createdCategoryDto;
    }
    
    @Override
	public List<CategoryDto> getAllCategories() {
		
		return categoryRepository.findAll().stream().map(Category::getCategoryDto).collect(Collectors.toList());
	}

    @Override
	public List<CategoryDto> getAllCategoriesTitle(String title) {
		
		return categoryRepository.findAllByNameContaining(title).stream().map(Category::getCategoryDto).collect(Collectors.toList());
	}

	@Override
	 public ProductDto postProduct(ProductDto productDto) throws IOException {
        // Fetch the category using categoryId from the ProductDto
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow();

        // Create a new Product entity and set the Category
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setCategory(category);  // Set the category
        product.setImg(productDto.getImg().getBytes());
        // Save the product to the database
        product = productRepository.save(product);

        // Convert the Product entity back to ProductDto to return
        ProductDto createdProductDto = new ProductDto();
        createdProductDto.setCategoryId(product.getCategory().getId());
        createdProductDto.setName(product.getName());
        createdProductDto.setDescription(product.getDescription());
        createdProductDto.setPrice(product.getPrice());
        // Set other fields as needed

        return createdProductDto;
    }

	@Override
	public List<ProductDto> getAllProductsByCategory() {
	
		return productRepository.findAll().stream().map(Product::getProductDto).collect(Collectors.toList());
	}

	@Override
	public List<ProductDto> getAllProductsByCategoryId(Long categoryId) {
		// TODO Auto-generated method stub
		 return productRepository.findAllByCategoryId(categoryId).stream().map(Product::getProductDto).collect(Collectors.toList());
	}

	

	@Override
	public List<ProductDto> getAllProductsByTitle(String title) {
		 return productRepository.findAllByNameContaining(title).stream().map(Product::getProductDto).collect(Collectors.toList());
	}

	@Override
	public void deleteproduct(Long productId) {
	    Optional<Product> optionalProduct = productRepository.findById(productId); // Corrected variable name capitalization
	    if (optionalProduct.isPresent()) {
	        productRepository.deleteById(productId);
	    } else {
	        throw new IllegalArgumentException("Product with id: " + productId + " not found");
	        }
	}

	@Override
	public ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException {
		Optional<Product>optional=productRepository.findById(productId);
		if(optional.isPresent()) {
			Product product=optional.get();
			product.setName(productDto.getName());
			product.setDescription(productDto.getDescription());
			product.setPrice(productDto.getPrice());
			if (productDto.getImg()!=null) {
				product.setImg(productDto.getImg().getBytes());
			}
			Product update=productRepository.save(product);
			ProductDto updateDto=new ProductDto();
			updateDto.setId(update.getId());
			return updateDto;
			
		}
		return null;
	}
	
	

}