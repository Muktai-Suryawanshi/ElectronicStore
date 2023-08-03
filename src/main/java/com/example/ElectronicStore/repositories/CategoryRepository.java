package com.example.ElectronicStore.repositories;

import com.example.ElectronicStore.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CategoryRepository extends JpaRepository<Category, String> {


}
