package com.alphasolutions.patisserie.model.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20, nullable = false)
    private String name = "roll";

    @Column(length = 10, nullable = false)
    private String category = "cake";

    @Column(nullable = false)
    private Double price = 0.0;

    @Column(name = "image_path", length = 30)
    private String imagePath;


}
