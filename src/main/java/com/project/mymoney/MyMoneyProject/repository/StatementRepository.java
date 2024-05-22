package com.project.mymoney.MyMoneyProject.repository;

import com.project.mymoney.MyMoneyProject.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StatementRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional(Transactional.TxType.REQUIRED)
    public int insertStatement(StatementModel statementModel) {
        String sql = "INSERT INTO STATEMENT " +
                "VALUES(:id , :username , :amount , :description , :type , :category , :date) ";

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("id" , statementModel.getId());
        query.setParameter("username" , statementModel.getUsername());
        query.setParameter("amount" , statementModel.getAmount());
        query.setParameter("description" , statementModel.getDescription());
        query.setParameter("type" , statementModel.getType());
        query.setParameter("category" , statementModel.getCategory());
        query.setParameter("date" , statementModel.getDate());

        return query.executeUpdate();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public List<IncomeExpenses> getYearandMonth(String username) {
        String sql = "SELECT" +
                " DATE_FORMAT(DATE, '%Y-%m') AS 'year_month', " +
                " SUM(CASE WHEN TYPE = 'income' THEN amount ELSE 0 END) AS income, " +
                " SUM(CASE WHEN TYPE = 'expenses' THEN amount ELSE 0 END) AS expenses " +
                "FROM STATEMENT " +
                "WHERE USERNAME = :username " +
                "GROUP BY DATE_FORMAT(DATE, '%Y-%m') " +
                "ORDER BY  DATE_FORMAT(DATE, '%Y-%m') DESC;";

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("username" , username);

        List<Object[]> resultList = query.getResultList();
        List<IncomeExpenses> incomeExpenses = new ArrayList<>();

        for (Object[] row : resultList) {
            IncomeExpenses inx = new IncomeExpenses();
            inx.setYearMonth((String) row[0]);
            inx.setIncome((Double) row[1]);
            inx.setExpenses((Double) row[2]);
            incomeExpenses.add(inx);
        }

        return incomeExpenses;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public ArrayList<StatementModel> getStatementData(String monthString , String username) {
        String sql = "SELECT * " +
                "FROM STATEMENT " +
                "WHERE DATE_FORMAT(DATE, '%Y-%m') = :monthString AND USERNAME = :username " +
                "ORDER BY DATE DESC;";

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("monthString" , monthString);
        query.setParameter("username" , username);

        ArrayList<Object[]> resultList = (ArrayList<Object[]>) query.getResultList();

        ArrayList<StatementModel> statementModels = new ArrayList<>();

        resultList.forEach(result -> {
            StatementModel statementModel = new StatementModel();

            statementModel.setId(((Integer) result[0]).longValue());
            statementModel.setUsername((String) result[1]);
            statementModel.setAmount((String) result[2]);
            statementModel.setDescription((String) result[3]);
            statementModel.setType((String) result[4]);
            statementModel.setCategory((String) result[5]);
            statementModel.setDate((String) result[6]);

            statementModels.add(statementModel);
        });
        return statementModels;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public ArrayList<SummarizeCategory> getSummarizeCategory(String username) {
        String sql = "SELECT " +
                "category, " +
                "type, " +
                "DATE_FORMAT(date, '%Y-%m') AS yearMonth, " +
                "SUM(AMOUNT) AS total_amount " +
                "FROM STATEMENT " +
                "WHERE USERNAME = :username " +
                "GROUP BY category, type, yearMonth;";
        
        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("username" , username);

        ArrayList<Object[]> resultList = (ArrayList<Object[]>) query.getResultList();

        ArrayList<SummarizeCategory> summarizeCategories = new ArrayList<>();

        resultList.forEach(result -> {
            SummarizeCategory summarizeCategory = new SummarizeCategory();

            summarizeCategory.setCategory((String) result[0]);
            summarizeCategory.setType((String) result[1]);
            summarizeCategory.setDate((String) result[2]);
            summarizeCategory.setAmount((Double) result[3]);

            summarizeCategories.add(summarizeCategory);
        });
        return summarizeCategories;
    }
}
