package com.example.ElectronicStore.controllers;

import com.example.ElectronicStore.dtos.ApiResponseMessage;
import com.example.ElectronicStore.dtos.CategoryDto;
import com.example.ElectronicStore.dtos.PageableResponse;
import com.example.ElectronicStore.dtos.ProductDto;
import com.example.ElectronicStore.services.CategoryService;
import com.example.ElectronicStore.services.FileService;
import com.example.ElectronicStore.services.ProductService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
@Slf4j
public class CategoryController {

    private Logger logger = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;
    /**
     * @author Muktai Suryawanshi
     * @apiNote createUser
     * @param categoryDto
     * @return categoryDto1
     */
    //create
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        logger.info("Initiating request to create category");
        //call service to save object
        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        logger.info("Completed request to create category");
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    /**
     * @author Muktai Suryawanshi
     * @apiNote updateCategory
     * @param categoryId
     * @param categoryDto
     * @return updatedCategory
     */
    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable String categoryId, @RequestBody CategoryDto categoryDto){
        logger.info("Initiating request to update categoryId");
        CategoryDto updatedCategory = categoryService.update(categoryDto, categoryId);
        logger.info("Completed request of update category");
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }

    /**
     * @author Muktai Suryawanshi
     * @apiNote deleteCategory
     * @param categoryId
     * @return message
     */
    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId){
        logger.info("Initiating  request to delete categoryId");
        categoryService.delete(categoryId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Category is deleted successfully ").status(HttpStatus.OK).success(true).build();
        logger.info("Completed request of delete categoryId");
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    /**
     * @author muktai suryawanshi
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return getAll
     */
    //getall
    @GetMapping("/getall")
    public ResponseEntity<PageableResponse<CategoryDto>> getAll(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "categoryTitle",required = false)String sortBy,
            @RequestParam(value ="sortDir", defaultValue = "asc",required = false) String sortDir
    ){
        logger.info("Initiating  request to  getAllCategory");
        PageableResponse<CategoryDto> pageableResponse = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    /**
     * @author muktai suryawanshi
     * @apiNote getSinglecategory
     * @param categoryId
     * @return categoryDto
     */
    //getsingle
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingle(@PathVariable String categoryId){
        logger.info("Initiating request to get single category");
        CategoryDto categoryDto = categoryService.get(categoryId);
        logger.info("Completed request of get category by categoryId");
        return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
    }

    //Create product with category
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto>createProductWithCategory(
            @PathVariable("categoryId") String categoryId,
            @RequestBody ProductDto dto){
        ProductDto productWithCategory = productService.createproductwithCategory(dto, categoryId);
        return new ResponseEntity<>(productWithCategory,HttpStatus.CREATED);
    }

    //Update category of product
    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> updatecategoryofProduct(
            @PathVariable String categoryId,
            @PathVariable String productId){
        ProductDto productDto = productService.updatecategoryofProduct(productId,categoryId);
        return new ResponseEntity<>(productDto,HttpStatus.CREATED);
    }

    //Get products of categories
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageableResponse<ProductDto>> getAllOfCategory(
            @PathVariable String categoryId,
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title",required = false)String sortBy,
            @RequestParam(value ="sortDir", defaultValue = "asc",required = false) String sortDir)
    {
        PageableResponse<ProductDto> response = productService.getAllOfCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
