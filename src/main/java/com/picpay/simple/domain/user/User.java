package com.picpay.simple.domain.user;

import com.picpay.simple.domain.DTOs.UserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity(name="users")
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of="id")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    private String fullName;

    @Column(unique=true)
    private String document;

    @Column(unique=true)
    private String email;

    private String password;

    private Double balance;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User(){}

    public User(UserDTO data){
        this.fullName = data.fullName();
        this.document = data.document();
        this.balance = data.balance();
        this.email = data.email();
        this.password = data.password();
        this.userType = data.userType();
    }
}
