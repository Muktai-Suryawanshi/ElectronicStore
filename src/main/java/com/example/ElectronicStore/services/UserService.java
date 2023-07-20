package com.example.ElectronicStore.services;

import com.example.ElectronicStore.dtos.PageableResponse;
import com.example.ElectronicStore.dtos.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    //Create
    UserDto createUser(UserDto userDto);

    //Update
    UserDto updateUser(UserDto userDto, String userId);

    //Delete
    void deleteUser(String userId);

    //getalluser
    PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get single user by id
    UserDto getUserById(String userId);

    //get single user by email
    UserDto getUserByEmail(String email);

    //SearchUser
    List<UserDto> searchUser(String keyword);

}
