package com.group.libraryapp.service.user;

import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.repository.user.UserJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceV1 {
    private final UserJdbcRepository userRepository;
    public UserServiceV1(UserJdbcRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void saveUser(String name, int age){
        userRepository.saveUserName(name, age);
    }

    public List<UserResponse> getUsers(){
        return userRepository.getUsersName();
    }

    public void updateUser(UserUpdateRequest request ){
        if(userRepository.isUserNotExist( request.getId())){
            throw new IllegalArgumentException();
        }
        userRepository.updateUserName( request.getName(), request.getId());
    }

    public void deleteUser(String name){
        if(userRepository.isUserNotExist(name)){
            throw new IllegalArgumentException();
        }
        userRepository.deleteUserName(name);
    }
}
