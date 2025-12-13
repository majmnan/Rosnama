package com.example.rosnama.Service;

import com.example.rosnama.Api.ApiException;
import com.example.rosnama.Model.User;
import com.example.rosnama.Repository.ExternalEventRepository;
import com.example.rosnama.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ExternalEventRepository externalEventRepository;


    public List<User>getAllUsers(){
        return userRepository.findAll();
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public void updateUser(Integer id , User user){
        User old = userRepository.findUserById(id);
        if(old == null){
            throw new ApiException("User not Exists");
        }
        old.setAge(user.getAge());
        old.setEmail(user.getEmail());
        old.setPassword(user.getPassword());
        old.setPhoneNumber(user.getPhoneNumber());
        old.setUsername(user.getUsername());
        userRepository.save(old);
    }

    public void deleteUser(Integer id) {
        User user =   userRepository.findUserById(id);
        if(user == null){
            throw new ApiException("User not found");
        }
        userRepository.delete(user);
    }

    public void addBalance(Integer id, Double balance){
        User user = userRepository.findUserById(id);
        if(user == null)
            throw new ApiException("user not found");

        user.setBalance(user.getBalance()+balance);
        userRepository.save(user);
    }
}
