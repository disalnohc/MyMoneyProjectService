package com.project.mymoney.MyMoneyProject.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.mymoney.MyMoneyProject.model.*;
import com.project.mymoney.MyMoneyProject.repository.StatementRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.io.IOException;
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

    @GetMapping(value = "statement-getMonth")
    public ResponseEntity<List<IncomeExpenses>> getYearAndMonth() {
        List<IncomeExpenses> yearMonths = statementRepository.getYearandMonth();
        return ResponseEntity
                .ok()
                .body(yearMonths);
    }

    @GetMapping(value = "statement-getData/{monthString}")
    public ResponseEntity<ArrayList<StatementModel>> getStatementData(@PathVariable(name = "monthString") String monthString) {
        return ResponseEntity
                .ok()
                .body(statementRepository.getStatementData(monthString));
    }

}
