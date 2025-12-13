package com.example.rosnama.Controller;

import com.example.rosnama.Api.ApiResponse;
import com.example.rosnama.Model.Category;
import com.example.rosnama.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // get
    @GetMapping("/get")
    public ResponseEntity<List<Category>>getAllCategory(){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAllCategories());
    }

    // add
    @PostMapping("/add/{adminId}")
        public ResponseEntity<ApiResponse>addCategory(@PathVariable Integer adminId, @RequestBody @Valid Category category){
            categoryService.addCategory(adminId, category);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Category Add successfully"));
        }
    @PutMapping("/update/{adminId}/{categoryId}")
        public ResponseEntity<ApiResponse>updateCategory(@PathVariable Integer adminId,@PathVariable Integer categoryId ,@RequestBody @Valid Category category){
            categoryService.updateCategory(adminId, categoryId, category);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Category updated successfully"));
        }
    @DeleteMapping("/delete/{adminId}/{categoryId}")
        public ResponseEntity<ApiResponse>deleteCategory(@PathVariable Integer adminId ,@PathVariable Integer categoryId ){
            categoryService.deleteCategory(adminId, categoryId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Category deleted successfully"));
        }


}
