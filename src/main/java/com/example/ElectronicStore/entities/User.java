package com.example.ElectronicStore.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="Users")
public class User {
    @Id
    @Column(name = "UserId")
    private String userId;
    @Column(name = "Name")
    private String name;
    @Column(name = "Email",unique = true)
    private String email;
    @Column(name = "Password",length = 10)
    private String password;
    @Column(name = "Gender")
    private String gender;
    @Column(name = "About",length = 1000)
    private String about;
    @Column(name="Image_name")
    private String imageName;

}
