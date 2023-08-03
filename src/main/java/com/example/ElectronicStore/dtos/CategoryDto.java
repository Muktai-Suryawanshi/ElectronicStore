package com.example.ElectronicStore.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private String categoryId;

    @NotBlank
    @Size(min =  4, message = "Title must be of minimum 4 characters !!")
    private String categoryTitle;

    @NotBlank(message = "Description is required !!")
    private String categoryDescription;

    private String coverImage;

}
