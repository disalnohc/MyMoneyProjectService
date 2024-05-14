package com.project.mymoney.MyMoneyProject.product;

import com.project.mymoney.MyMoneyProject.model.UserModel;
import com.project.mymoney.MyMoneyProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "users")
    public ResponseEntity<ArrayList<UserModel>> getAllUser() {
        return ResponseEntity
                .ok()
                .body(userRepository.getAllUser());
    }

    @GetMapping(value = "users/{email}")
    public ResponseEntity<UserModel> getCustomerByID(@PathVariable(name = "email") String email ) {
        UserModel user = userRepository.getUserByEmail(email);
        if (user != null) {
            return ResponseEntity
                    .ok()
                    .body(user);
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @PostMapping(value = "insert-user")
    public ResponseEntity<String> addNewCustomer(@RequestBody UserModel userModel) {
        int result = userRepository.insertNewUser(userModel);

        if(result != 0) {
            return ResponseEntity
                    .ok()
                    .body("add new customer successfully.");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("add new customer does not successfully.");
        }
    }

    @PutMapping(value = "update-PlusBalance/{username}")
    public ResponseEntity<String> updatePlusBalance(@PathVariable(name = "username") String username ,
                                                    @RequestParam Integer amount) {
        int result = userRepository.updateBalancePlus(username , amount);

        if(result != 0) {
            return ResponseEntity
                    .ok()
                    .body("update balance successfully.");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("update balance not successfully.");
        }
    }

    @PutMapping(value = "update-MinusBalance/{username}")
    public ResponseEntity<String> updateMinusBalance(@PathVariable(name = "username") String username ,
                                                     @RequestParam Integer amount) {
        int result = userRepository.updateBalanceMinus(username , amount);

        if(result != 0) {
            return ResponseEntity
                    .ok()
                    .body("update balance successfully.");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("update balance not successfully.");
        }
    }
}
