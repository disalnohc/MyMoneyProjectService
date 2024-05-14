package com.project.mymoney.MyMoneyProject.repository;

import com.project.mymoney.MyMoneyProject.model.CategoryModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class CategoryRepository {

    @PersistenceContext
    private EntityManager entityManager;


    // not get Image
    @Transactional(Transactional.TxType.REQUIRED)
    public int addNewCategory(CategoryModel categoryModel) {
        String sql = "INSERT INTO CATEGORY " +
                "VALUES(:id , :username , :name , :bgcolor , :type , :image) ";

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("id" , categoryModel.getId());
        query.setParameter("username" , categoryModel.getUsername());
        query.setParameter("name" , categoryModel.getName());
        query.setParameter("bgcolor" , categoryModel.getBgcolor());
        query.setParameter("type" , categoryModel.getType());
        query.setParameter("image" , categoryModel.getImage());

        return query.executeUpdate();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public int updateImageCategory(String username , byte[] image) {
        String sql = "UPDATE CATEGORY " +
                "SET IMAGE = :image " +
                "WHERE USERNAME = :username ORDER BY ID DESC LIMIT 1;";

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("username" , username);
        query.setParameter("image" , image);

        return query.executeUpdate();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public ArrayList<CategoryModel> getUserCategory(String username) {
        String sql = "SELECT * FROM CATEGORY " +
                "WHERE USERNAME = :username ";

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("username" , username);

        ArrayList<Object[]> resultList = (ArrayList<Object[]>) query.getResultList();

        ArrayList<CategoryModel> categoryModels = new ArrayList<>();

        resultList.forEach(result -> {
            CategoryModel categoryModel = new CategoryModel();

            categoryModel.setId(((Integer) result[0]).longValue());
            categoryModel.setUsername((String) result[1]);
            categoryModel.setName((String) result[2]);
            categoryModel.setBgcolor((String) result[3]);
            categoryModel.setType((String) result[4]);
            categoryModel.setImage((byte[]) result[5]);

            categoryModels.add(categoryModel);
        });

        return categoryModels;
    }

}
