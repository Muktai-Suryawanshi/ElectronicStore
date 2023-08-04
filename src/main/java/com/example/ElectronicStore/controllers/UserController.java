 package com.example.ElectronicStore.controllers;

import com.example.ElectronicStore.dtos.ApiResponseMessage;
import com.example.ElectronicStore.dtos.ImageResponse;
import com.example.ElectronicStore.dtos.PageableResponse;
import com.example.ElectronicStore.dtos.UserDto;
import com.example.ElectronicStore.entities.User;
import com.example.ElectronicStore.services.FileService;
import com.example.ElectronicStore.services.UserService;
import com.example.ElectronicStore.services.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    @Autowired
    private FileService fileService;

    /**
     * @author Muktai Suryawanshi
     * @apiNote createUser
     * @param userDto
     * @return user
     */
    //CREATE
        @PostMapping("/")
        public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
            logger.info("Initiating request to create user");
            UserDto user = userService.createUser(userDto);
            logger.info("Completed request to create user");
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }


    /**
     * @author Muktai Suryawanshi
     * @param userId
     * @param userDto
     * @return updatedUserDto
     */
    //update
        @PutMapping("/{userId}")
        public ResponseEntity<UserDto> updateUser(@PathVariable("userId") String userId,@Valid @RequestBody UserDto userDto) {

            logger.info("Initiating request to update userID");
            UserDto updatedUserDto = userService.updateUser(userDto, userId);
            logger.info("Completed request of update user");
            return new ResponseEntity<>(updatedUserDto, HttpStatus.CREATED);
        }

    /**
     * @author Muktai Suryawanshi
     * @apiNote
     * @param userId
     * @return message
     */
    //delete
        @DeleteMapping("/{userId}")
        public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId){
            logger.info("Initiating  request to delete userId");
            userService.deleteUser(userId);
        ApiResponseMessage message = ApiResponseMessage
                .builder()
                .message("User is Deleted Successfully!!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
            logger.info("Completed request of delete userId");
            return new ResponseEntity(message, HttpStatus.OK);
    }

    /**
     * @author muktai suryawanshi
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return getAllUser
     */
        // get All
        @GetMapping
        public ResponseEntity<PageableResponse<UserDto>>getAllUsers(
                @RequestParam(value = "pageNumber", defaultValue = "1",required = false) int pageNumber,
                @RequestParam(value = "pageSize" , defaultValue = "10",required = false) int pageSize,
                @RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy,
                @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir

        ){
            logger.info("Initiating  request to  getAllUse");
            return new ResponseEntity<>(userService.getAllUser(pageNumber,pageSize,sortBy,sortDir), HttpStatus.OK);
        }

    /**
     * @author muktai suryawanshi
     * @param userId
     * @return
     */
    //get single
        @GetMapping("/{userId}")
        public ResponseEntity<UserDto> getUsers(@PathVariable String userId){
            logger.info("Initiating request to getUsers");
            return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
        }

    /**
     * author muktai suryawanshi
     * @param email
     * @return user
     */
    // get By email
        @GetMapping("/email/{email}")
        public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
            logger.info("Initiating request to get User by email");
            UserDto userByEmail = userService.getUserByEmail(email);
            logger.info("Completed request of get User by email");
            return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
        }


        //search user
        @GetMapping("/search/{keywords}")
        public ResponseEntity<List<UserDto>> getUserByKeyword (@PathVariable String keywords) {
            logger.info("Initiating request to search by keyword");
            List<UserDto> userDtoByKeyword = userService.searchUser(keywords);
            logger.info("Completed request of search by keyword");

            return new ResponseEntity<>(userDtoByKeyword, HttpStatus.OK);

        }

        //upload user image
        @PostMapping("/image/{userId}")
        public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage")MultipartFile Image, @PathVariable String userId) throws IOException {

            String imageName = fileService.uploadFile(Image, imageUploadPath);

            UserDto user = userService.getUserById(userId);

            user.setImageName(imageName);
            UserDto userDto = userService.updateUser(user, userId);
            ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).message("Image upload Successfully").success(true).status(HttpStatus.CREATED).build();
            return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);


        }

        //serve user image
        @GetMapping("/image/{userId}")
        public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {

            UserDto user = userService.getUserById(userId);
            logger.info("user image name : ",user.getImageName());
            InputStream resource = fileService.getResource(imageUploadPath,user.getImageName());

            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(resource,response.getOutputStream());
        }


    }

