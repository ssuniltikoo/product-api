package com.project.products.productapi.dtos;

import com.project.products.productapi.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private CategoryDto category;

    public static ProductDto from(Product product) {
        if (product == null) {
            return null;
        }
        
        ProductDto dto = new ProductDto();
        dto.id = product.getId();
        dto.name = product.getName();
        dto.description = product.getDescription();
        dto.imageUrl = product.getImageUrl();
        dto.price = product.getPrice();
        
        if (product.getCategory() != null) {
            dto.category = new CategoryDto(
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategory().getDescription()
            );
        }
        
        return dto;
    }
}
