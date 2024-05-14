package com.project.mymoney.MyMoneyProject.repository;

import com.project.mymoney.MyMoneyProject.model.RegularModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class RegularRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Regular
    @Transactional(Transactional.TxType.REQUIRED)
    public int addNewRegular(RegularModel regularModel) {
        String sql = "INSERT INTO Subscribe " +
                "VALUES(:id , :username , :name , :price , :start , :end , :image , :type) ";
        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("id" , regularModel.getId());
        query.setParameter("username" , regularModel.getUsername());
        query.setParameter("name" , regularModel.getName());
        query.setParameter("price" , regularModel.getPrice());
        query.setParameter("start" , regularModel.getStart());
        query.setParameter("end" , regularModel.getEnd());
        query.setParameter("image" ,regularModel.getImage());
        query.setParameter("type" , regularModel.getType());

        return query.executeUpdate();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public ArrayList<RegularModel> getUserRegular(String username) {
        String sql = "SELECT * FROM Subscribe " +
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
            regularModel.setPrice((String) result[3]);
            regularModel.setStart((String) result[4]);
            regularModel.setEnd((String) result[5]);
            regularModel.setImage((String) result[6]);
            regularModel.setType((String) result[7]);

            regularModels.add(regularModel);
        });

        return regularModels;
    }
}
