package com.example.ElectronicStore.services;

import com.example.ElectronicStore.dtos.CategoryDto;
import com.example.ElectronicStore.dtos.PageableResponse;
import com.example.ElectronicStore.dtos.UserDto;
import com.example.ElectronicStore.entities.Category;
import com.example.ElectronicStore.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.Table;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CategoryServiceTest {
    @MockBean
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ModelMapper modelMapper;

    Category category;

    @BeforeEach
    public void init(){
        category = Category.builder()
                .categoryTitle("Mobile")
                .categoryDescription("This is new launched mob")
                .coverImage("xyz.png")
                .build();
    }

    @Test
    public void createCategoryTest(){
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        CategoryDto categoryDto = categoryService.create(modelMapper.map(category, CategoryDto.class));
        System.out.println(categoryDto.getCategoryTitle());
        Assertions.assertEquals("Mobile",categoryDto.getCategoryTitle());

    }

    @Test
    public void updateCategoryTest(){
        String categoryId = "a1b2";
        CategoryDto categoryDto = CategoryDto.builder()
                .categoryTitle("Mobile Phones")
                .categoryDescription("This is new launched mob in 2023")
                .coverImage("xyz.png")
                .build();

        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);

        CategoryDto updatedCategory = categoryService.update(categoryDto,categoryId);
        System.out.println(updatedCategory.getCategoryTitle());
        Assertions.assertEquals("Mobile Phones",updatedCategory.getCategoryTitle());

    }

    @Test
    public void deleteCategoryTest(){
        String categoryId="abc123";
        Mockito.when(categoryRepository.findById("abc123")).thenReturn(Optional.of(category));
        categoryService.delete(categoryId);
        Mockito.verify(categoryRepository,Mockito.times(1)).delete(category);
    }

    @Test
    public void getAllTest(){
        Category category1 = Category.builder()
                .categoryTitle("Mobile Phones")
                .categoryDescription("This is new launched mob in 2023")
                .coverImage("xyz.png")
                .build();

        Category category2= Category.builder()
                .categoryTitle("Laptop")
                .categoryDescription("This is new launched laptop in 2023")
                .coverImage("abc.png")
                .build();

        Category category3 = Category.builder()
                .categoryTitle("Computers")
                .categoryDescription("This is new launched computers in 2023")
                .coverImage("ggg.png")
                .build();

        List<Category> list = Arrays.asList(category1,category2,category3);
        Page<Category> page = new PageImpl<>(list);
        Mockito.when(categoryRepository.findAll((Pageable)Mockito.any())).thenReturn(page);
        PageableResponse<CategoryDto> allCategory = categoryService.getAll(1, 1, "categoryTitle", "asc");
        Assertions.assertEquals(3,allCategory.getContent().size());
    }

    @Test
    public void get(){

        String categoryId="a1b2c3d4";
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        CategoryDto categoryDto = categoryService.get(categoryId);
        Assertions.assertEquals(category.getCategoryTitle(),categoryDto.getCategoryTitle(),"Title not matched");

    }
}
