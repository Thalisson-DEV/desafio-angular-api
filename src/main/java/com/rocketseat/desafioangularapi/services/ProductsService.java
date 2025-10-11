package com.rocketseat.desafioangularapi.services;

import com.rocketseat.desafioangularapi.dtos.ProductDTO;
import com.rocketseat.desafioangularapi.dtos.ProductRequestDTO;
import com.rocketseat.desafioangularapi.dtos.ProductsResponseDTO;
import com.rocketseat.desafioangularapi.entitys.Products;
import com.rocketseat.desafioangularapi.exceptions.NoProductsFoundException;
import com.rocketseat.desafioangularapi.exceptions.ProductNotFoundException;
import com.rocketseat.desafioangularapi.mappers.ProductMapper;
import com.rocketseat.desafioangularapi.repositories.ProductsRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductsService {
    private final ProductsRepository productsRepository;
    private final ProductMapper productMapper;

    public ProductsService(ProductsRepository productsRepository, ProductMapper productMapper) {
        this.productsRepository = productsRepository;
        this.productMapper = productMapper;
    }

    @Transactional
    @Caching(put = {
            @CachePut(value = "product-item", key = "#result.id")
    }, evict = {
            @CacheEvict(value = "product-list", allEntries = true)
    })
    public ProductDTO saveProduct(ProductRequestDTO productDTO) {
        Products productToSave = productMapper.toProductSavedEntity(productDTO);

        Products savedProduct = productsRepository.save(productToSave);

        return productMapper.toProductDTO(savedProduct);
    }

    @Cacheable(
            value = "product-list",
            key = "#pageable.pageNumber + '_' + #pageable.pageSize + '_' + #pageable.sort.toString()"
    )
    public ProductsResponseDTO findAllProducts(Pageable pageable) {
        Page<Products> products = productsRepository.findAll(pageable);

        if (products.isEmpty()) {
            throw new NoProductsFoundException("Nenhum produto encontrado, verifique a base de dados.");
        }

        Page<ProductDTO> productDTOs = products
                .map(productMapper::toProductDTO);

        return productMapper.toProductsResponseDTO(productDTOs, "Produtos listados com sucesso.");
    }

    @Cacheable(value = "product-item", key = "#id")
    public ProductDTO findProductById(Long id) {
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado com o Id" + id));

        return productMapper.toProductDTO(product);
    }

    @Transactional
    @Caching(put = {
            @CachePut(value = "product-item", key = "#id")
    }, evict = {
            @CacheEvict(value = "product-list", allEntries = true)
    })
    public ProductDTO updateProduct(Long id, ProductRequestDTO productDTO) {
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado com o Id" + id));

        Products productToUpdate = productMapper.toProductUpdatedEntity(product, productDTO);

        Products updatedProduct = productsRepository.save(productToUpdate);

        return productMapper.toProductDTO(updatedProduct);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "product-item", key = "#id"),
            @CacheEvict(value = "product-list", allEntries = true)
    })
    public void deleteProduct(Long id) {
        if (!productsRepository.existsById(id)) {
            throw new ProductNotFoundException("Produto não encontrado com o Id" + id);
        }

        productsRepository.deleteById(id);
    }
}
