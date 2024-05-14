package com.project.mymoney.MyMoneyProject.product;

import com.project.mymoney.MyMoneyProject.model.RegularModel;
import com.project.mymoney.MyMoneyProject.repository.RegularRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class RegularController {

    @Autowired
    RegularRepository regularRepository;

    @PostMapping(value = "insert-regular")
    public ResponseEntity<String> addNewRegular(@RequestBody RegularModel regularModel) {
        int result = regularRepository.addNewRegular(regularModel);

        if (result != 0) {
            return ResponseEntity
                    .ok()
                    .body("add new regular successfully.");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("add new regular does not successfully.");
        }
    }

    @GetMapping(value = "regular/{username}")
    public ResponseEntity<ArrayList<RegularModel>> getUserRegular(@PathVariable(name = "username") String username) {
        return ResponseEntity
                .ok()
                .body(regularRepository.getUserRegular(username));
    }

}
