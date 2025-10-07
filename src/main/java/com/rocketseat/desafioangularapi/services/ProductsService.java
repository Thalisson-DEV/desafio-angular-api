package com.rocketseat.desafioangularapi.services;

import com.rocketseat.desafioangularapi.dtos.ProductDTO;
import com.rocketseat.desafioangularapi.dtos.ProductRequestDTO;
import com.rocketseat.desafioangularapi.dtos.ProductsResponseDTO;
import com.rocketseat.desafioangularapi.entitys.Products;
import com.rocketseat.desafioangularapi.exceptions.ProductNotFoundException;
import com.rocketseat.desafioangularapi.mappers.ProductMapper;
import com.rocketseat.desafioangularapi.repositories.ProductsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductsService {
    private final ProductsRepository productsRepository;
    private final ProductMapper productMapper;

    public ProductsService(ProductsRepository productsRepository, ProductMapper productMapper) {
        this.productsRepository = productsRepository;
        this.productMapper = productMapper;
    }

    @Transactional
    public ProductDTO saveProduct(ProductRequestDTO productDTO) {
        Products productToSave = productMapper.toProductsEntity(productDTO);

        Products savedProduct = productsRepository.save(productToSave);

        return  productMapper.toProductDTO(savedProduct);
    }

    public ProductsResponseDTO findAllProducts() {
        List<Products> products = productsRepository.findAll();

        List<ProductDTO> productDTOs = products.stream()
                .map(productMapper::toProductDTO)
                .toList();

        return new ProductsResponseDTO(
                "Produtos listados com sucesso",
                productDTOs
        );
    }

    public ProductDTO findProductById(Long id) {
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado com o Id" + id));

        return productMapper.toProductDTO(product);
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ProductRequestDTO productDTO) {
        if (!productsRepository.existsById(id)) {
            throw new ProductNotFoundException("Produto não encontrado com o Id" + id);
        }

        Products productToUpdate = productMapper.toProductsEntity(productDTO);

        Products updatedProduct = productsRepository.save(productToUpdate);

        return productMapper.toProductDTO(updatedProduct);
    }

    public void deleteProduct(Long id) {
        if (!productsRepository.existsById(id)) {
            throw new ProductNotFoundException("Produto não encontrado com o Id" + id);
        }

        productsRepository.deleteById(id);
    }
}
