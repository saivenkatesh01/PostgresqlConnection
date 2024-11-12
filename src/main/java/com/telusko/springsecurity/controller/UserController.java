package com.telusko.springsecurity.controller;


import com.telusko.springsecurity.model.Users;
import com.telusko.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String greet(){
        return "hello";
    }

//    private List<User> users = new ArrayList<>
//            (List.of(
//                    new User(1,"venki",4141),
//                    new User(2,"padma", 9030)
//    ));

    @PostMapping("/user")
    public Users addUser(@RequestBody Users user){
        userRepository.save(user);
        return user;
    }
    @GetMapping("/user")
    public List<Users> getUser()
    {
        return userRepository.findAll();
    }
    @PutMapping("/user/{id}")
    public ResponseEntity<Users> updateUser(@RequestBody Users user,
                                     @PathVariable int id){
        Optional<Users> user1 = userRepository.findById(id);
        if(user1.isPresent()){
            Users user2=user1.get();
            user2.setUsername(user.getUsername());
            user2.setPhone(user.getPhone());
            return new ResponseEntity<>(userRepository.save(user2), HttpStatus.OK);
        }
        else {
            throw new RuntimeException("user not found");
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id){
        Optional<Users> user1 = userRepository.findById(id);
        if(user1.isPresent()){
            userRepository.delete(user1.get());
            return new ResponseEntity<>("User deleted", HttpStatus.OK);
        }
        else return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
}
