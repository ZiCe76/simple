package com.picpay.simple.domain.DTOs;

import com.picpay.simple.domain.user.UserType;

public record UserDTO(Long id, String fullName, String document, String email, Double balance,String password,UserType userType) {
}
