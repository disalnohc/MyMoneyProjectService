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

    @Transactional(Transactional.TxType.REQUIRED)
    public int updateUser(String username, String newUsername , String password, String email, String phone) {
        String sql1 = "UPDATE CATEGORY SET USERNAME = :newUsername WHERE USERNAME = :username";
        String sql2 = "UPDATE HISTORY SET USERNAME = :newUsername WHERE USERNAME = :username";
        String sql3 = "UPDATE REGULAR SET USERNAME = :newUsername WHERE USERNAME = :username";
        String sql4 = "UPDATE STATEMENT SET USERNAME = :newUsername WHERE USERNAME = :username";
        String sql5 = "UPDATE USER SET EMAIL = :email, USERNAME = :newUsername, PASSWORD = :password, PHONE = :phone WHERE USERNAME = :username";

        Query query1 = entityManager.createNativeQuery(sql1);
        query1.setParameter("username", username);
        query1.setParameter("newUsername", newUsername);
        int result1 = query1.executeUpdate();

        Query query2 = entityManager.createNativeQuery(sql2);
        query2.setParameter("username", username);
        query2.setParameter("newUsername", newUsername);
        int result2 = query2.executeUpdate();

        Query query3 = entityManager.createNativeQuery(sql3);
        query3.setParameter("username", username);
        query3.setParameter("newUsername", newUsername);
        int result3 = query3.executeUpdate();

        Query query4 = entityManager.createNativeQuery(sql4);
        query4.setParameter("username", username);
        query4.setParameter("newUsername", newUsername);
        int result4 = query4.executeUpdate();

        Query query5 = entityManager.createNativeQuery(sql5);
        query5.setParameter("username", username);
        query5.setParameter("newUsername", newUsername);
        query5.setParameter("email", email);
        query5.setParameter("password", password);
        query5.setParameter("phone", phone);
        int result5 = query5.executeUpdate();

        return result1 + result2 + result3 + result4 + result5;
    }

    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM USER WHERE USERNAME = :username";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("username", username);
        long count = ((Number) query.getSingleResult()).longValue();
        return count > 0;
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM USER WHERE EMAIL = :email";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("email", email);
        long count = ((Number) query.getSingleResult()).longValue();
        return count > 0;
    }

    public boolean findEmailandPhone(String email , String phone) {
        String sql = "SELECT COUNT(*) FROM USER WHERE EMAIL = :email AND PHONE = :phone";

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("email", email);
        query.setParameter("phone" , phone);

        long count = ((Number) query.getSingleResult()).longValue();
        return count > 0;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public int changePassword(String password , String email) {
        String sql = """
                UPDATE USER
                SET PASSWORD = :password
                WHERE EMAIL = :email
                """;

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("password" , password);
        query.setParameter("email" , email);

        return query.executeUpdate();
    }

}
