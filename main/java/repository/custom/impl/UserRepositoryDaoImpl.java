package repository.custom.impl;

import entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import repository.custom.UserRepositoryDao;
import util.HibernateUtil;

public class UserRepositoryDaoImpl implements UserRepositoryDao {

    @Override
    public boolean save(UserEntity user) {
        System.out.println(user);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.merge(user);
        session.getTransaction().commit();
        session.clear();
        return true;
    }

    @Override
    public UserEntity findUser(String email, String password) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();


        String hql = "FROM UserEntity WHERE email = :email";
        Query<UserEntity> query = session.createQuery(hql, UserEntity.class);
        query.setParameter("email", email);

        UserEntity user = query.uniqueResult();


        session.getTransaction().commit();
        session.clear();

        return user;
    }
}
