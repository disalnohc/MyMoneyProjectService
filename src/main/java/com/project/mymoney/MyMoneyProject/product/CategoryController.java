package com.project.mymoney.MyMoneyProject.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.mymoney.MyMoneyProject.model.CategoryModel;
import com.project.mymoney.MyMoneyProject.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping(value = "category/{username}")
    public ResponseEntity<ArrayList<CategoryModel>> getUserCategory(@PathVariable(name = "username") String username) {
        return ResponseEntity
                .ok()
                .body(categoryRepository.getUserCategory(username));
    }

    // not insert image
    @PostMapping(value = "insert-category")
    public ResponseEntity<String> addNewCategory(@RequestBody CategoryModel categoryModel) {
            int result = categoryRepository.addNewCategory(categoryModel);

            if (result != 0) {
                return ResponseEntity
                        .ok()
                        .body("add new category successfully.");
            } else {
                return ResponseEntity
                        .badRequest()
                        .body("add new category does not successfully.");
            }
    }

    //update image
    @PutMapping(value = "update-image/{username}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateImage(@PathVariable(name = "username") String username ,
                                              @RequestParam(name = "image") MultipartFile image) {
        try {
            byte[] imageCategory = image.getBytes();

            int result = categoryRepository.updateImageCategory(username , imageCategory);

            if(result != 0) {
                return ResponseEntity
                        .ok()
                        .body("update image successfully.");
            } else {
                return ResponseEntity
                        .badRequest()
                        .body("update image does not successfully.");
            }
    } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Cannot update image");
        }
}
}