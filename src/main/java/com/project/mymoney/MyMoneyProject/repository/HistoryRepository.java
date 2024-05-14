package com.project.mymoney.MyMoneyProject.repository;

import com.project.mymoney.MyMoneyProject.model.HistoryModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class HistoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(Transactional.TxType.REQUIRED)
    public int addNewHistory(HistoryModel historyModel) {
        String sql = "INSERT INTO HISTORY " +
                "VALUES(:id , :username , :date , :income , :expenses)";

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("id" , historyModel.getId());
        query.setParameter("username" , historyModel.getUsername());
        query.setParameter("date" , historyModel.getDate());
        query.setParameter("income" , historyModel.getIncome());
        query.setParameter("expenses" , historyModel.getExpenses());

        return query.executeUpdate();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public ArrayList<HistoryModel> getUserHistory(String username) {
        String sql = "SELECT * FROM HISTORY " +
                "WHERE username = :username";

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("username" , username);

        ArrayList<Object[]> resultList = (ArrayList<Object[]>) query.getResultList();

        ArrayList<HistoryModel> historyModels = new ArrayList<>();

        resultList.forEach(result -> {
            HistoryModel historyModel = new HistoryModel();

            historyModel.setId(((Integer) result[0]).longValue());
            historyModel.setUsername((String) result[1]);
            historyModel.setDate((String) result[2]);
            historyModel.setIncome((String) result[3]);
            historyModel.setExpenses((String) result[4]);

            historyModels.add(historyModel);
        });

        return historyModels;
    }
}
