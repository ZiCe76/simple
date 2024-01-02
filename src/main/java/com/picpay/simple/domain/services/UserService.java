package com.picpay.simple.domain.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.picpay.simple.domain.DTOs.UserDTO;
import com.picpay.simple.domain.user.User;
import com.picpay.simple.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findById(Long id) throws Exception{
        return userRepository.findById(id).orElseThrow(() -> new Exception("usuario nao encontrado"));
    }

    public User createUser(UserDTO data) {
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }

    private boolean userExists(String document, String email) {
        return userRepository.findByDocumentOrEmail(document, email).isPresent();
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }
}
