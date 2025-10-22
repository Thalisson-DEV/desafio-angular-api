package com.rocketseat.desafioangularapi.mappers;

import com.rocketseat.desafioangularapi.dtos.ProductDTO;
import com.rocketseat.desafioangularapi.dtos.ProductRequestDTO;
import com.rocketseat.desafioangularapi.dtos.ProductsResponseDTO;
import com.rocketseat.desafioangularapi.enums.ProductStatus;
import com.rocketseat.desafioangularapi.entitys.Products;
import org.springframework.data.domain.Page;
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

    public Products toProductSavedEntity(ProductRequestDTO dto) {
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

    public Products toProductUpdatedEntity(Products product, ProductRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        product.setTitle(dto.title());
        product.setPrice(dto.price());
        product.setDescription(dto.description());
        product.setCategory(dto.category());
        product.setImageBase64(dto.imageBase64());
        product.setStatus(dto.status());

        return product;
    }

    public ProductsResponseDTO toProductsResponseDTO(Page<ProductDTO> productsPage, String message) {
        if (productsPage == null) {
            return null;
        }

        return new ProductsResponseDTO(
                message,
                productsPage.getContent(),
                productsPage.getNumber(),
                productsPage.getSize(),
                productsPage.getTotalElements(),
                productsPage.getTotalPages()
        );
    }
}
