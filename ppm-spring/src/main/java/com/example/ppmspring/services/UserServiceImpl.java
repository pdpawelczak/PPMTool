package com.example.ppmspring.services;

import com.example.ppmspring.domain.User;
import com.example.ppmspring.exceptions.UsernameAlreadyExistsException;
import com.example.ppmspring.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }catch (Exception e){
            throw new UsernameAlreadyExistsException("Username: " + user.getUsername() + " already exists!");
        }

    }
}
