package com.project.mymoney.MyMoneyProject.product;

import com.project.mymoney.MyMoneyProject.model.RegularModel;
import com.project.mymoney.MyMoneyProject.model.RegularNextPay;
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

    @DeleteMapping(value = "regular-delete/{username}/{id}")
    public ResponseEntity<String> deleteRegular(@PathVariable(name = "username") String username ,
                                                @PathVariable(name = "id") Integer id) {

        int result = regularRepository.deleteRegular(username,id);

        if (result != 0) {
            return ResponseEntity
                    .ok()
                    .body("delete regular successfully.");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("delete regular does not successfully.");
        }
    }

    @GetMapping(value = "regular-currentPay/{username}")
    public ResponseEntity<ArrayList<RegularNextPay>> getCurrentPay(@PathVariable(name = "username") String username) {
        return ResponseEntity
                .ok()
                .body(regularRepository.getNextPayRegular(username));
    }

    @PostMapping(value = "update-currentPay/{id}/{date}")
    public ResponseEntity<String> updateCurrent(@PathVariable(name = "id") String id ,
                                                @PathVariable(name = "date") String date) {
        int result = regularRepository.updateCurrent(id,date);

        if (result != 0) {
            return ResponseEntity
                    .ok()
                    .body("Update Current Success.");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("Update Current not Success.");
        }
    }
}
