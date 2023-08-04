package com.example.ElectronicStore.services;

import com.example.ElectronicStore.dtos.PageableResponse;
import com.example.ElectronicStore.dtos.ProductDto;

import java.util.List;

public interface  ProductService {

    //Create
    ProductDto create(ProductDto productDto);

    //Update
    ProductDto update(ProductDto productDto,String productId);

    //Delete
    void delete(String productId);

    //GetSingle
    ProductDto get(String productId);

    //GetAll
    PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    //GetAll Live
    PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir);

    //Search Product
    PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber, int pageSize, String sortBy, String sortDir);

    //Create product with category
    ProductDto createproductwithCategory(ProductDto productDto, String categoryId);

    //Update category of product
    ProductDto updatecategoryofProduct(String productId,String categoryId);

    PageableResponse<ProductDto> getAllOfCategory(String categoryId,int pageNumber,int pageSize,String sortBy, String sortDir);

}
