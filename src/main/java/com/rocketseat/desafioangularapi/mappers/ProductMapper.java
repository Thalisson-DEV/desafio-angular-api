package com.rocketseat.desafioangularapi.mappers;

import com.rocketseat.desafioangularapi.dtos.ProductDTO;
import com.rocketseat.desafioangularapi.dtos.ProductRequestDTO;
import com.rocketseat.desafioangularapi.entitys.ProductStatus;
import com.rocketseat.desafioangularapi.entitys.Products;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDTO toProductDTO(Products products) {
        if (products == null) {
            return null;
        }

        return new ProductDTO(
                products.getId(),
                products.getTitle(),
                products.getPrice(),
                products.getDescription(),
                products.getCategory(),
                products.getStatus(),
                products.getImageBase64()
        );
    }

    public Products toProductsEntity(ProductRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Products products = new Products();
        products.setTitle(dto.title());
        products.setPrice(dto.price());
        products.setDescription(dto.description());
        products.setCategory(dto.category());
        products.setImageBase64(dto.imageBase64());

        products.setStatus(ProductStatus.ANUNCIADO);

        return products;
    }
}
