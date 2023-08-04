package com.example.ElectronicStore.services.impl;

import com.example.ElectronicStore.dtos.PageableResponse;
import com.example.ElectronicStore.dtos.UserDto;
import com.example.ElectronicStore.entities.User;
import com.example.ElectronicStore.exceptions.ResourceNotFoundException;
import com.example.ElectronicStore.helper.Helper;
import com.example.ElectronicStore.repositories.UserRepository;
import com.example.ElectronicStore.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    @Value("${user.profile.image.path}")
    private String imagePath;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public UserDto createUser(UserDto userDto) {
        logger.info("Inside createUser()");
        //Generate unique id in string format
        String userId= UUID.randomUUID().toString();
        logger.info("Generate unique Id: "+ userId);
        userDto.setUserId(userId);

        //dto->entity
        User user = dtoToEntity(userDto);
        logger.info("Saving the User: "+user);
        User savedUser = userRepository.save(user);
        logger.info("saved User "+savedUser);

        //entity->dto
        UserDto newDto = entityToDto(savedUser);
        logger.info("Conversion Successful");
        return newDto;

    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        logger.info("Inside updateUser() with User Id "+userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(("user not found with given id !")));
        user.setName(userDto.getName());
        //email update
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());
        logger.info("Updated User: "+user);

        //save Date
        User updatedUser = userRepository.save(user);
        UserDto updatedDto = entityToDto(updatedUser);
        logger.info("User updated successfully "+updatedDto);
        return updatedDto;

    }

    @Override
    public void deleteUser(String userId) {
        logger.info("Inside deleteUser() with id "+userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(("user not found with given id !")));

        String fullPath = imagePath + user.getImageName();

        try{
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch (NoSuchFileException ex){

            logger.info("User image is not found in folder");
            ex.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        logger.info("Deleted User: "+user);
        userRepository.delete(user);

    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = Sort.by(sortBy);
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        logger.info("Inside getAllUser");
        Page<User> page = userRepository.findAll(pageable);
        PageableResponse<UserDto> response = Helper.getPageableResponse(page,UserDto.class);
        logger.info("List of User: "+response);
        return response;
    }

    @Override
    public UserDto getUserById(String userId) {
        logger.info("Inside getUserById() with User Id: "+userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found by given id"));
        logger.info("User with Id: "+user);
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.info("Inside getUserByEmail() with Email: "+email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with this email and password"));
        logger.info("User By email: "+user);
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        logger.info("Inside searchUser() with keyword: "+keyword);
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList= (List<UserDto>) users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        logger.info("User details :"+dtoList);
        return dtoList;
    }

    private UserDto entityToDto(User savedUser) {
       /* UserDto userDto= UserDto.builder()
                .userId(savedUser.getUserId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .password(savedUser.getName())
                .about(savedUser.getAbout())
                .gender(savedUser.getGender())
                .imageName(savedUser.getImageName())
                .build();
        return userDto;*/
        return mapper.map(savedUser, UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {
       /* User user= User.builder()
                .userId(userDto.getUserId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getName())
                .about(userDto.getAbout())
                .gender(userDto.getGender())
                .imageName(userDto.getImageName())
                .build();
        return user;*/
        return mapper.map(userDto, User.class);
    }

}
