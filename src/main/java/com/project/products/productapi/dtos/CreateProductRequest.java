package com.project.products.productapi.dtos;

import com.project.products.productapi.model.Category;
import com.project.products.productapi.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequest {
    private Long id;
    private String title;
    private String description;
    private double price;
    private String image;
    private String category;

    public Product toProduct() {
        Product product = new Product();
        product.setId(this.id);
        product.setName(this.title);
        product.setDescription(this.description);
        product.setPrice(this.price);
        product.setImageUrl(this.image);

        if (this.category != null && !this.category.isEmpty()) {
            Category categoryObj = new Category();
            categoryObj.setName(this.category);
            product.setCategory(categoryObj);
        }

        return product;
    }

}
