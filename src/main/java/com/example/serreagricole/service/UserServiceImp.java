package com.example.serreagricole.service;

import com.example.serreagricole.entitie.Role;
import com.example.serreagricole.entitie.Serre;
import com.example.serreagricole.entitie.User;
import com.example.serreagricole.repository.SerreRepository;
import com.example.serreagricole.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SerreRepository serreRepository;

    public User getUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    public User addUser(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUser(){
        return (List<User>) userRepository.findAll();
    }

//    public List<Serre> getSerresByUserId(int userId) {
////        User user = getUserById(userId);
////        return user.getSerres();
//        return serreRepository.findSerresByUserId(userId);
//    }


    public String getUserName(int userId) {
        User user = getUserById(userId);
        return user.getNom() + " " + user.getPrenom();
    }
    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }

}
