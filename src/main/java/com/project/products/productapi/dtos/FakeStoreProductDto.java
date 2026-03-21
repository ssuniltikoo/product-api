package com.project.products.productapi.dtos;

import com.project.products.productapi.model.Category;
import com.project.products.productapi.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FakeStoreProductDto {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;

    public Product toProduct() {
        Product product = new Product();
        product.setId(this.id);
        product.setName(this.title);
        product.setPrice(this.price);
        product.setDescription(this.description);
        product.setImageUrl(this.image);
        
        // Only create category if category name is provided
        if (this.category != null && !this.category.isEmpty()) {
            Category categoryObj = new Category();
            categoryObj.setName(this.category);
            product.setCategory(categoryObj);
        }
        
        return product;
    }

}
