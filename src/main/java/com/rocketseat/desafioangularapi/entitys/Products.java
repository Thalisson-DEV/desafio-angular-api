package com.rocketseat.desafioangularapi.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "products")
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    @Column(columnDefinition = "TEXT")
    private String imageBase64;
}
