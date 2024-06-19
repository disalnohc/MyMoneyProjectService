package com.project.mymoney.MyMoneyProject.repository;

import com.project.mymoney.MyMoneyProject.model.RegularModel;
import com.project.mymoney.MyMoneyProject.model.RegularNextPay;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContexts;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;

@Repository
public class RegularRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Regular
    @Transactional(Transactional.TxType.REQUIRED)
    public int addNewRegular(RegularModel regularModel) {
        String sql = "INSERT INTO REGULAR " +
                "VALUES(:id , :username , :name , :type , :price , :cycle , :start , :end , :currentPay) ";
        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("id" , regularModel.getId());
        query.setParameter("username" , regularModel.getUsername());
        query.setParameter("name" , regularModel.getName());
        query.setParameter("price" , regularModel.getPrice());
        query.setParameter("start" , regularModel.getStart());
        query.setParameter("end" , regularModel.getEnd());
        query.setParameter("cycle" ,regularModel.getCycle());
        query.setParameter("type" , regularModel.getType());
        query.setParameter("currentPay" , regularModel.getCurrentPay());

        return query.executeUpdate();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public ArrayList<RegularModel> getUserRegular(String username) {
        String sql = "SELECT * FROM REGULAR " +
                "WHERE username = :username";

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("username" , username);

        ArrayList<Object[]> resultList = (ArrayList<Object[]>) query.getResultList();

        ArrayList<RegularModel> regularModels = new ArrayList<>();

        resultList.forEach(result -> {
            RegularModel regularModel = new RegularModel();

            regularModel.setId(((Integer) result[0]).longValue());
            regularModel.setUsername((String) result[1]);
            regularModel.setName((String) result[2]);
            regularModel.setType((String) result[3]);
            regularModel.setPrice((Double) result[4]);
            regularModel.setCycle((String) result[5]);
            regularModel.setStart((String) result[6]);
            regularModel.setEnd((String) result[7]);
            regularModel.setCurrentPay((String) result[8]);

            regularModels.add(regularModel);
        });

        return regularModels;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public int deleteRegular(String username , Integer id){
        String sql = "DELETE FROM REGULAR " +
                "WHERE USERNAME = :username and ID = :id";
        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("username" , username);
        query.setParameter("id" , id);

        return query.executeUpdate();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public ArrayList<RegularNextPay> getNextPayRegular(String username) {
        String sql = """
        
                SELECT
            id,
            username,
            name,
            type,
            price,
            STARTDATE,
            cycle,
            endate,
            currentPay,
            DATE_FORMAT(
                    DATE_ADD(
                            CASE
                                WHEN cycle LIKE '%day%' THEN DATE_ADD(DATE_SUB(currentPay, INTERVAL 543 YEAR), INTERVAL CAST(SUBSTRING_INDEX(cycle, ' ', 1) AS UNSIGNED) DAY)
                                WHEN cycle LIKE '%month%' THEN DATE_ADD(DATE_SUB(currentPay, INTERVAL 543 YEAR), INTERVAL CAST(SUBSTRING_INDEX(cycle, ' ', 1) AS UNSIGNED) MONTH)
                                WHEN cycle LIKE '%year%' THEN DATE_ADD(DATE_SUB(currentPay, INTERVAL 543 YEAR), INTERVAL CAST(SUBSTRING_INDEX(cycle, ' ', 1) AS UNSIGNED) YEAR)
                                END, INTERVAL 543 YEAR
                    ), '%Y-%m-%d'
            ) AS nextPay,
            CASE
                WHEN `endate` = 'Never' THEN 'Yes'
                WHEN `endate` REGEXP '^[0-9]{4}-[0-9]{2}-[0-9]{2}$' THEN
                    CASE
                        WHEN
                            CASE
                                WHEN cycle LIKE '%day%' THEN DATE_ADD(DATE_SUB(currentPay, INTERVAL 543 YEAR), INTERVAL CAST(SUBSTRING_INDEX(cycle, ' ', 1) AS UNSIGNED) DAY)
                                WHEN cycle LIKE '%month%' THEN DATE_ADD(DATE_SUB(currentPay, INTERVAL 543 YEAR), INTERVAL CAST(SUBSTRING_INDEX(cycle, ' ', 1) AS UNSIGNED) MONTH)
                                WHEN cycle LIKE '%year%' THEN DATE_ADD(DATE_SUB(currentPay, INTERVAL 543 YEAR), INTERVAL CAST(SUBSTRING_INDEX(cycle, ' ', 1) AS UNSIGNED) YEAR)
                                END <= DATE_SUB(STR_TO_DATE(`endate`, '%Y-%m-%d'), INTERVAL 543 YEAR)
                            THEN 'Yes'
                        ELSE 'No'
                        END
                END AS isNextPay
        FROM Regular
        WHERE
            username = :username AND
            CASE
                WHEN cycle LIKE '%day%' THEN DATE_ADD(DATE_SUB(currentPay, INTERVAL 543 YEAR), INTERVAL CAST(SUBSTRING_INDEX(cycle, ' ', 1) AS UNSIGNED) DAY)
                WHEN cycle LIKE '%month%' THEN DATE_ADD(DATE_SUB(currentPay, INTERVAL 543 YEAR), INTERVAL CAST(SUBSTRING_INDEX(cycle, ' ', 1) AS UNSIGNED) MONTH)
                WHEN cycle LIKE '%year%' THEN DATE_ADD(DATE_SUB(currentPay, INTERVAL 543 YEAR), INTERVAL CAST(SUBSTRING_INDEX(cycle, ' ', 1) AS UNSIGNED) YEAR)
                END BETWEEN DATE_FORMAT(CURDATE(), '%Y-%m-01') AND LAST_DAY(
                CURDATE());
        
        """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("username", username);

        ArrayList<Object[]> resultList = (ArrayList<Object[]>) query.getResultList();
        ArrayList<RegularNextPay> regularNextPays = new ArrayList<>();

        for (Object[] result : resultList) {
            RegularNextPay regularNextPay = new RegularNextPay();

            regularNextPay.setId(((Number) result[0]).longValue());
            regularNextPay.setUsername((String) result[1]);
            regularNextPay.setName((String) result[2]);
            regularNextPay.setType((String) result[3]);
            regularNextPay.setPrice(result[4] instanceof Number ? ((Number) result[4]).doubleValue() : Double.valueOf((String) result[4]));
            regularNextPay.setStart(result[5].toString());
            regularNextPay.setEnd(result[6].toString());
            regularNextPay.setCycle((String) result[7]);
            regularNextPay.setCurrentPay((String) result[8]);
            regularNextPay.setNextPay(result[8] != null ? result[9].toString() : null);
            regularNextPay.setPay(result[9] != null ? result[10].toString() : null);

            regularNextPays.add(regularNextPay);
        }
        return regularNextPays;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public int updateCurrent(String id , String date) {
        String sql = """
                UPDATE REGULAR
                SET currentPay = :date
                WHERE ID = :id
                """;

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("id" , id);
        query.setParameter("date" , date);

        return query.executeUpdate();
    }
}
