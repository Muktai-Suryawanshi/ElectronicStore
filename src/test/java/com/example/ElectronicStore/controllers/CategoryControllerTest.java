package com.example.ElectronicStore.controllers;

import com.example.ElectronicStore.dtos.CategoryDto;
import com.example.ElectronicStore.dtos.PageableResponse;
import com.example.ElectronicStore.dtos.UserDto;
import com.example.ElectronicStore.entities.Category;
import com.example.ElectronicStore.services.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    Category category,category1,category2;

    CategoryDto categoryDto;

    @BeforeEach
    public void init(){

        category=Category.builder()
                .categoryId("abc123")
                .categoryTitle("Laptop")
                .categoryDescription("All Laptops are available ")
                .coverImage("lapy.jpg").build();

        category1=Category.builder()
                .categoryId("mob123")
                .categoryTitle( "Mobile")
                .categoryDescription("All Mobiles are available")
                .coverImage("mob.jpg").build();

        category2=Category.builder()
                .categoryId("comp999")
                .categoryTitle("Computer")
                .categoryDescription("All Computers Available")
                .coverImage("comp.jpg").build();
    }

    @Test
    public void createCategoryTest() throws Exception{

        CategoryDto dto = modelMapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.create(Mockito.any())).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

   @Test
    public void updateCategoryTest() throws Exception {
        String categoryId="abc123";
        CategoryDto dto = this.modelMapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.update(Mockito.any(),Mockito.anyString())).thenReturn(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/categories/"+categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryTitle").exists());


    }


    private String convertObjectToJsonString(Object category) {
        try {
            return new ObjectMapper().writeValueAsString(category);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void getAllCategoryTest() throws Exception {

        CategoryDto object1 = CategoryDto.builder().categoryTitle("Laptop").categoryDescription("Laptops are available").coverImage("lapy.jpg").build();
        CategoryDto object2 = CategoryDto.builder().categoryTitle("Mobile").categoryDescription("Mobiles are available").coverImage("mob.jpg").build();
        CategoryDto object3 = CategoryDto.builder().categoryTitle("Computer").categoryDescription("Computers are available ").coverImage("comp.jpg").build();
        PageableResponse<CategoryDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(object1, object2, object3));
        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElements(1000);
        Mockito.when(categoryService.getAll(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/categories/getAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

}
