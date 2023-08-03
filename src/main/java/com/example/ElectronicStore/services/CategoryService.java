package com.example.ElectronicStore.services;

import com.example.ElectronicStore.dtos.CategoryDto;
import com.example.ElectronicStore.dtos.PageableResponse;

public interface CategoryService {

    //create
    CategoryDto create(CategoryDto categoryDto);

    //update
    CategoryDto update(CategoryDto categoryDto, String categoryId);

    //delete
    void delete(String categoryId);

    //getAll
    PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    //Get single category detail
    CategoryDto get(String categoryId);

}
