package com.example.rosnama.service;

import com.example.rosnama.apiresponse.ApiException;
import com.example.rosnama.model.User;
import com.example.rosnama.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public List<User>getAllUsers(){
       return userRepository.findAll();
    }


    public void addUser(User user){
        User user1 = userRepository.findUserByEmail(user.getEmail());
        User user2 = userRepository.findUserByUsername(user.getUsername());

       if(user1 !=null){
           throw new ApiException("Email Already exsit");
       }

       if(user2 != null){
           throw new ApiException("UserName Already exsit");
       }

       userRepository.save(user);

    }


    public void updateUser(Integer id , User user){

        User user1 = userRepository.findUserById(id);
       List <User> users =  userRepository.findAll();

        if(user1 == null){
            throw new ApiException("User not Exists");
        }



    }
}
