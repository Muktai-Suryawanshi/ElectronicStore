package com.example.ElectronicStore.services.impl;

import com.example.ElectronicStore.dtos.CategoryDto;
import com.example.ElectronicStore.dtos.PageableResponse;
import com.example.ElectronicStore.entities.Category;
import com.example.ElectronicStore.entities.User;
import com.example.ElectronicStore.exceptions.ResourceNotFoundException;
import com.example.ElectronicStore.helper.Helper;
import com.example.ElectronicStore.repositories.CategoryRepository;
import com.example.ElectronicStore.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        logger.info("Inside createCategory");

        //Generate Unique id in string format
        String categoryId = UUID.randomUUID().toString();
        logger.info("Generate unique Id:"+categoryId);
        categoryDto.setCategoryId(categoryId);

        Category category = modelMapper.map(categoryDto, Category.class);
        logger.info("Saving the Category:"+category);
        Category savedCategory = categoryRepository.save(category);
        logger.info("Saved Category:"+savedCategory);
        return modelMapper.map(savedCategory,CategoryDto.class);

    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        logger.info("Inside the update Category with categoryId"+categoryId);

        //get category of given id
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found exceptiom !!"));

        //update category details
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatedCategory = categoryRepository.save(category);
        logger.info("Updated category : "+updatedCategory);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        logger.info("Inside delete category with categoryId: "+categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found exceptiom !!"));
        logger.info("Deleted Category: "+category);
        //Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found exceptiom !!"));
        categoryRepository.delete(category);
    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        log.info("Enter in Service");
        Sort sort =(sortDir.equalsIgnoreCase("desc")) ?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        log.info("After Sorting");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        log.info("Pageable response");
        Page<Category> page = categoryRepository.findAll(pageable);
        log.info("find aal category");
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
        log.info("Send the request in helper class");
        return pageableResponse;
    }

    @Override
    public CategoryDto get(String categoryId) {
        logger.info("Inside get with categoryId : "+categoryId);
      //  Category user = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("user not found by given id"));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category is not avilable"));
        logger.info("User with id"+category);
        //Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found exceptiom !!"));
        return modelMapper.map(category,CategoryDto.class);
    }
}
