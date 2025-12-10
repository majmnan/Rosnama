package com.example.rosnama.Repository;

import com.example.rosnama.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    Category findCategoryById(Integer id);
}
