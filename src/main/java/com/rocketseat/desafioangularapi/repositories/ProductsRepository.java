package com.rocketseat.desafioangularapi.repositories;

import com.rocketseat.desafioangularapi.entitys.Products;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, Long> {

    @NotNull Page<Products> findAll(@NotNull Pageable pageable);
}
