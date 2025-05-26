package com.alphasolutions.patisserie.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30)
    private String username;

    @Column(length = 100)
    private String password;

    @Column(name = "user_code",length = 4, unique = true)
    private String userCode;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

}
