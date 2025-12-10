package com.example.rosnama.Service;

import com.example.rosnama.Api.ApiException;
import com.example.rosnama.Model.Admin;
import com.example.rosnama.Model.Category;
import com.example.rosnama.Repository.AdminRepository;
import com.example.rosnama.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final AdminRepository adminRepository;


    public List<Category>getAllCategories(){
        return categoryRepository.findAll();
    }

    public void addCategory(Integer adminId, Category category){
       Admin admin =  adminRepository.findAdminById(adminId);

       if(admin == null){
           throw new ApiException("Admin not found");
       }
        categoryRepository.save(category);
    }

    public void updateCategory(Integer adminId,Integer categoryId , Category category){
        Category oldcategory=categoryRepository.findCategoryById(categoryId);
        Admin admin =  adminRepository.findAdminById(adminId);
        if(oldcategory == null){
            throw new ApiException("No Category Found");
        }
        if(admin == null){
            throw new ApiException("No Admin Found");
        }
        oldcategory.setCategoryName(category.getCategoryName());
        categoryRepository.save(oldcategory);
    }

    public void deleteCategory(Integer adminId , Integer categoryId ){
        Category category =categoryRepository.findCategoryById(categoryId);
        Admin admin = adminRepository.findAdminById(adminId);

        if(category == null){
            throw new ApiException("Category not found");
        }
        if(admin == null){
            throw new ApiException("Admin not found");
        }
        categoryRepository.delete(category);

    }
}
