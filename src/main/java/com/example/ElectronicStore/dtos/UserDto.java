package com.example.ElectronicStore.dtos;

import com.example.ElectronicStore.validate.ImageNameValid;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String userId;

    @Size(min=3, max = 25, message = "Invalid Name !!")
    private String name;

    //@Email(message = "Invalid EmailId")
    //@Pattern(regexp = "^[a-z0-9][-a-z0-9,_]+@([-a-z0-9]+\\.)[a-z]{2,5}$",message = "Invalid Email")
    //@NotBlank(message = "Email is required !!")
    private String email;

    @NotBlank(message = "Password is required !!")
    private String password;

    @Size(min = 3,max = 6,message = "Invalid gender !!")
    private String gender;

    private String about;
    @ImageNameValid
    private String imageName;

}
