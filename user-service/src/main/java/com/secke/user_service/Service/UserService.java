package com.secke.user_service.Service;


import com.secke.user_service.Error.ResourceNotFoundException;
import com.secke.user_service.Feign.UserInterface;
import com.secke.user_service.Model.Product;
import com.secke.user_service.Model.User;
import com.secke.user_service.Model.UserCustomize;
import com.secke.user_service.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepo;

    @Autowired
    UserInterface userInterface;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
//    @Autowired
//    PasswordEncoder passwordEncoder;

    public List<UserCustomize> getAllUsers() {
        return userRepo.findAll().stream().map(UserCustomize::new).toList();
//        try {
//        } catch (ForbiddenException fex) {
//            throw new ForbiddenException("Access denied for this user");
//        }
    }

    public User addUser(User user) {
        if (user.getPassword() != null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
      return  userRepo.save(user);
    }

    public User getUserById(String id) {
        return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

    }

    public void deleteUser(String id) {
        User user = userRepo.findById(id).orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        } else {
            userRepo.deleteById(id);
        }
    }

    public User updateUser(String id, User userDetails) {
        User user = getUserById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        user.setEmail(userDetails.getEmail());
        user.setName(userDetails.getName());
        user.setRole(userDetails.getRole());
        if (userDetails.getPassword() != null){
            user.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        }
        return userRepo.save(user);
//        try {
//
//        }
    }

    public List<Product> getUserProducts(String userId) {
        return userInterface.getProductByUserId(userId).getBody();
    }

//    private boolean validUser(User user) {
//        if (user.getName() == null || user.getName().trim().isEmpty()
//                || product.getPrice() == null || product.getPrice() < 0
//        ){
//            return false;
//        }
//        return true;
//    }


}
