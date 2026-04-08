package com.rocketseat.desafioangularapi.controlllers;

import com.rocketseat.desafioangularapi.dtos.ProductDTO;
import com.rocketseat.desafioangularapi.dtos.ProductRequestDTO;
import com.rocketseat.desafioangularapi.dtos.ProductsResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

@Tag(name = "Products", description = "Operations pertaining to products management")
public interface ProductsControllerDocs {

    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request (invalid input)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    ResponseEntity<ProductDTO> saveProduct(ProductRequestDTO productRequest);

    @Operation(summary = "Paginated list of all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paged listing of products retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductsResponseDTO.class)))
    })
    ResponseEntity<ProductsResponseDTO> findAllProducts(Pageable pageable);

    @Operation(summary = "Find a product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    ResponseEntity<ProductDTO> findProductById(Long id);

    @Operation(summary = "Update an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "400", description = "Bad request (invalid input)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    ResponseEntity<ProductDTO> updateProduct(Long id, ProductRequestDTO productRequest);

    @Operation(summary = "Delete a product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    ResponseEntity<Void> deleteProduct(Long id);
}
