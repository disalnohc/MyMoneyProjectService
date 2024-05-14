package com.project.mymoney.MyMoneyProject.repository;

import com.project.mymoney.MyMoneyProject.model.StatementModel;
import com.project.mymoney.MyMoneyProject.model.UserModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    //Get all users
    @Transactional(Transactional.TxType.SUPPORTS)
    public ArrayList<UserModel> getAllUser() {
        String sql = "SELECT * FROM USER";

        Query query = entityManager.createNativeQuery(sql);

        ArrayList<Object[]> resultList = (ArrayList<Object[]>) query.getResultList();

        ArrayList<UserModel> userModels = new ArrayList<>();

        resultList.forEach(result -> {
            UserModel userModel = new UserModel();

            userModel.setId(((Integer) result[0]).longValue());
            userModel.setEmail((String) result[1]);
            userModel.setPassword((String) result[2]);
            userModel.setUsername((String) result[3]);
            userModel.setPhone((String) result[4]);
            userModel.setBalance((String) result[5]);

            userModels.add(userModel);
        });
        return userModels;
    }

    //Get user by Email
    @Transactional(Transactional.TxType.REQUIRED)
    public UserModel getUserByEmail(String email) {
        String sql = "SELECT * FROM USER " +
                "WHERE EMAIL = :email";

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("email", email);

        Object[] result = (Object[]) query.getSingleResult();

        UserModel userModel = new UserModel();

        userModel.setId(((Integer) result[0]).longValue());
        userModel.setEmail((String) result[1]);
        userModel.setPassword((String) result[2]);
        userModel.setUsername((String) result[3]);
        userModel.setPhone((String) result[4]);
        userModel.setBalance((String) result[5]);
        return userModel;

    }

    //Insert New Account
    @Transactional(Transactional.TxType.REQUIRED)
    public int insertNewUser(UserModel userModel) {
        String sql = "INSERT INTO USER " +
                "VALUES(:id , :email , :password , :username , :phone , :balance) ";

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("id" , userModel.getId());
        query.setParameter("email" , userModel.getEmail());
        query.setParameter("password" , userModel.getPassword());
        query.setParameter("username" , userModel.getUsername());
        query.setParameter("phone" , userModel.getPhone());
        query.setParameter("balance" , userModel.getBalance());

        return query.executeUpdate();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public int updateBalancePlus(String username , Integer amount) {
        String sql = "UPDATE USER " +
                "SET BALANCE = BALANCE + :amount " +
                "WHERE USERNAME = :username";

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("username" , username);
        query.setParameter("amount" , amount);

        return query.executeUpdate();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public int updateBalanceMinus(String username , Integer amount) {
        String sql = "UPDATE USER " +
                "SET BALANCE = BALANCE - :amount " +
                "WHERE USERNAME = :username";

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("username" , username);
        query.setParameter("amount" , amount);

        return query.executeUpdate();
    }
}
