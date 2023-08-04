package com.example.ElectronicStore.services;

import com.example.ElectronicStore.dtos.PageableResponse;
import com.example.ElectronicStore.dtos.UserDto;
import com.example.ElectronicStore.entities.User;
import com.example.ElectronicStore.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTest {

    //Create User
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    User user;
    @BeforeEach
    public void init(){
        user = User.builder()
                .name("Muktai")
                .email("muktai@gmail.com")
                .password("abcd12")
                .gender("Female")
                .about("Muktai is Java Developer")
                .imageName("abc.png")
                .build();
    }
    @Test
    public void createUserTest(){
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto user1 = userService.createUser(modelMapper.map(user, UserDto.class));
        System.out.println(user1.getName());
        Assertions.assertNotNull(user1);
        Assertions.assertEquals("Muktai",user1.getName());
    }

    @Test
    public void updateUserTest(){
        String userId = "dszxcdldfhjk";
        UserDto userDto=UserDto.builder()
                .name("Muktai Dhanaji Suryawanshi")
                .email("muktai@gmail.com")
                .password("abcd12")
                .gender("Female")
                .about("Muktai is Java Developer and Tester also")
                .imageName("abc.png")
                .build();

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto updatedUser = userService.updateUser(userDto,userId);
        System.out.println(updatedUser.getName());
        Assertions.assertEquals("Muktai Dhanaji Suryawanshi",updatedUser.getName());

    }
    @Test
    public void deleteUserTest(){
        String userid="abcdefgh";
        Mockito.when(userRepository.findById("abcdefgh")).thenReturn(Optional.of(user));
        userService.deleteUser((userid));
        Mockito.verify(userRepository,Mockito.times(1)).delete(user);
    }
    @Test
    public void getAllUsersTest(){

        User user1 = User.builder()
                .name("Ankita")
                .email("muktai@gmail.com")
                .password("abcd12")
                .gender("Female")
                .about("Muktai is Java Developer")
                .imageName("abc.png")
                .build();

        User user2 = User.builder()
                .name("Rutuja")
                .email("muktai@gmail.com")
                .password("abcd12")
                .gender("Female")
                .about("Muktai is Java Developer")
                .imageName("abc.png")
                .build();

        List<User> userList = Arrays.asList(user,user1,user2);

        Page<User> page =new PageImpl<>(userList);

        Mockito.when(userRepository.findAll((Pageable)Mockito.any())).thenReturn(page);
        PageableResponse<UserDto> allUser = userService.getAllUser(1, 2, "name", "asc");
        Assertions.assertEquals(3,allUser.getContent().size());
    }

    @Test
    public void getUserById(){

        String userId = "asdfghjkl";
        Mockito.when((userRepository.findById(userId))).thenReturn(Optional.of(user));

        UserDto userDto= userService.getUserById(userId);
        Assertions.assertEquals(user.getName(),userDto.getName(),"Name not matched !!");
    }

    @Test
    public void getUserByEmail(){
        String emailid = "muktai69@gmail.com";

        Mockito.when(userRepository.findByEmail(emailid)).thenReturn(Optional.of(user));
        UserDto userDto = userService.getUserByEmail(emailid);
        Assertions.assertEquals(user.getEmail(),userDto.getEmail(),"Email not matched");

    }

    @Test
    public void searchUserTest(){
        User user1 = User.builder()
                .name("Prasad")
                .email("muktai@gmail.com")
                .password("abcd12")
                .gender("Female")
                .about("Muktai is Java Developer")
                .imageName("abc.png")
                .build();

        User user2 = User.builder()
                .name("Arjun")
                .email("muktai@gmail.com")
                .password("abcd12")
                .gender("Female")
                .about("Muktai is Java Developer")
                .imageName("abc.png")
                .build();

        User user3 = User.builder()
                .name("Dnyanu")
                .email("muktai@gmail.com")
                .password("abcd12")
                .gender("Female")
                .about("Muktai is Java Developer")
                .imageName("abc.png")
                .build();


        String keyword = "Arjun";
        Mockito.when(userRepository.findByNameContaining(keyword)).thenReturn(Arrays.asList(user1,user2,user3));
        List<UserDto> userDtos = userService.searchUser(keyword);
        Assertions.assertEquals(3,userDtos.size(),"Size not matched");

    }



}
