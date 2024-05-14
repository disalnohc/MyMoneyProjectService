package com.project.mymoney.MyMoneyProject.product;

import com.project.mymoney.MyMoneyProject.model.HistoryModel;
import com.project.mymoney.MyMoneyProject.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class HistoryController {

    @Autowired
    HistoryRepository historyRepository;

    @PostMapping(value = "insert-history")
    public ResponseEntity<String> addNewHistory(@RequestBody HistoryModel historyModel) {
        int result = historyRepository.addNewHistory(historyModel);

        if (result != 0) {
            return ResponseEntity
                    .ok()
                    .body("add new history successfully.");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("add new history does not successfully.");
        }
    }

    @GetMapping(value = "history/{username}")
    public ResponseEntity<ArrayList<HistoryModel>> getUserHistory(@PathVariable(name = "username") String username ) {
        return ResponseEntity
                .ok()
                .body(historyRepository.getUserHistory(username));
    }
}
