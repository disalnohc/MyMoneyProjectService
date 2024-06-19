package com.project.mymoney.MyMoneyProject.product;

import com.project.mymoney.MyMoneyProject.model.UserModel;
import com.project.mymoney.MyMoneyProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

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
    public ResponseEntity<UserModel> getCustomerByID(@PathVariable(name = "email") String email) {
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

        if (result != 0) {
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
    public ResponseEntity<String> updatePlusBalance(@PathVariable(name = "username") String username,
                                                    @RequestParam Integer amount) {
        int result = userRepository.updateBalancePlus(username, amount);

        if (result != 0) {
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
    public ResponseEntity<String> updateMinusBalance(@PathVariable(name = "username") String username,
                                                     @RequestParam Integer amount) {
        int result = userRepository.updateBalanceMinus(username, amount);

        if (result != 0) {
            return ResponseEntity
                    .ok()
                    .body("update balance successfully.");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("update balance not successfully.");
        }
    }

    @PutMapping(value = "update-password")
    public ResponseEntity<String> updatePassword(@RequestBody Map<String, String> userData) {
        String username = userData.get("username");
        String newUsername = userData.get("newUsername");
        String password = userData.get("password");
        String email = userData.get("email");
        String phone = userData.get("phone");

        int result = userRepository.updateUser(username, newUsername, password, email, phone);

        if (result != 0) {
            return ResponseEntity.ok().body("User updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("User update failed.");
        }
    }

    @PostMapping(value = "check-user")
    public ResponseEntity<String> checkUserExists(@RequestBody Map<String, String> userDetails) {
        String username = userDetails.get("username");
        String email = userDetails.get("email");

        boolean usernameExists = userRepository.existsByUsername(username);
        boolean emailExists = userRepository.existsByEmail(email);

        if (usernameExists && emailExists) {
            return ResponseEntity.ok().body("Username and Email already exist.");
        } else if (usernameExists) {
            return ResponseEntity.ok().body("Username already exists.");
        } else if (emailExists) {
            return ResponseEntity.ok().body("Email already exists.");
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "findemail-phone")
    public ResponseEntity<String> findEmailPhone(@RequestBody Map<String, String> userDetails) {
        String phone = userDetails.get("phone");
        String email = userDetails.get("email");

        boolean exists = userRepository.findEmailandPhone(email,phone);

        if (exists) {
            return ResponseEntity.ok().body("Exist.");
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "updatepassword")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> userDetails) {
        String password = userDetails.get("password");
        String email = userDetails.get("email");

        int result = userRepository.changePassword(password,email);

        if (result != 0) {
            return ResponseEntity.ok().body("change password success.");
        } else {
            return ResponseEntity.badRequest().body("change password failed.");
        }
    }
}