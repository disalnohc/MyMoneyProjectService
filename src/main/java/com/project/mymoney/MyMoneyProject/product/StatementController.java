package com.project.mymoney.MyMoneyProject.product;

import com.project.mymoney.MyMoneyProject.model.*;
import com.project.mymoney.MyMoneyProject.repository.StatementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StatementController {

    @Autowired
    StatementRepository statementRepository;

    @PostMapping(value = "insert-statement")
    public ResponseEntity<String> addStatement(@RequestBody StatementModel statementModel) {
        int result = statementRepository.insertStatement(statementModel);

        if (result != 0) {
            return ResponseEntity
                    .ok()
                    .body("add new statement successfully.");
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("add new statement does not successfully.");
        }
    }

    @GetMapping(value = "hello-test")
    public ResponseEntity<String> test(){
        return ResponseEntity
                .ok()
                .body("OK");
    }

    @GetMapping(value = "statement-getMonth/{username}")
    public ResponseEntity<List<IncomeExpenses>> getYearAndMonth(@PathVariable(name = "username") String username) {
        List<IncomeExpenses> yearMonths = statementRepository.getYearandMonth(username);
        return ResponseEntity
                .ok()
                .body(yearMonths);
    }

    @GetMapping(value = "statement-getData/{username}/{monthString}")
    public ResponseEntity<ArrayList<StatementModel>> getStatementData(@PathVariable(name = "monthString") String monthString ,
                                                                      @PathVariable(name = "username") String username) {
        return ResponseEntity
                .ok()
                .body(statementRepository.getStatementData(monthString , username));
    }

    @GetMapping(value = "statement-getSummarize/{username}")
    public ResponseEntity<ArrayList<SummarizeCategory>> getStatementSummarize(@PathVariable(name = "username") String username) {
        return ResponseEntity
                .ok()
                .body(statementRepository.getSummarizeCategory(username));
    }
}
