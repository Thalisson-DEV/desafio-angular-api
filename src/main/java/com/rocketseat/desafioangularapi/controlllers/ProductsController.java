package com.rocketseat.desafioangularapi.controlllers;

import com.rocketseat.desafioangularapi.dtos.ProductDTO;
import com.rocketseat.desafioangularapi.dtos.ProductRequestDTO;
import com.rocketseat.desafioangularapi.dtos.ProductsResponseDTO;
import com.rocketseat.desafioangularapi.services.ProductsService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/products")
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }


    @PostMapping("/new-product")
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody @Valid ProductRequestDTO productRequest) {
        ProductDTO productDTO = productsService.saveProduct(productRequest);
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping()
    public ResponseEntity<ProductsResponseDTO> findAllProducts(Pageable pageable) {
        ProductsResponseDTO productsResponseDTO = productsService.findAllProducts(pageable);
        return ResponseEntity.ok(productsResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findProductById(@PathVariable Long id) {
        ProductDTO productDTO = productsService.findProductById(id);
        return ResponseEntity.ok(productDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequestDTO productRequest) {
        ProductDTO productDTO = productsService.updateProduct(id, productRequest);
        return ResponseEntity.ok(productDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productsService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
