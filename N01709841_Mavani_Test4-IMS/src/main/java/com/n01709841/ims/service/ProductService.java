package com.n01709841.ims.service;

import com.n01709841.ims.entity.Product;
import com.n01709841.ims.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Retrieve all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Retrieve a single product by ID
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    // Add a new product
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // Update product details
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existing = getProductById(id);
        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setPrice(updatedProduct.getPrice());
        existing.setStock(updatedProduct.getStock());
        return productRepository.save(existing);
    }

    // Delete a product
    public void deleteProduct(Long id) {
        getProductById(id); // validates existence before deleting
        productRepository.deleteById(id);
    }

    // Update stock level only
    public Product updateStock(Long id, Integer newStock) {
        Product existing = getProductById(id);
        existing.setStock(newStock);
        return productRepository.save(existing);
    }
}
