package com.rocketseat.desafioangularapi.repositories;

import com.rocketseat.desafioangularapi.entitys.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, Long> {
}
