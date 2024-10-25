package repository.custom.impl;

import entity.AdminEntity;
import entity.UserEntity;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;
import repository.custom.AdminRepositoryDao;
import util.HibernateUtil;

public class AdminRepositoryDaoImpl implements AdminRepositoryDao {
    @Override
    public boolean save(AdminEntity adminEntity) {
        return false;
    }

    @Override
    public ObservableList<Integer> getIds() {
        return null;
    }

    @Override
    public ObservableList<String> getStringIds() {
        return null;
    }

    @Override
    public ObservableList<AdminEntity> getAll() {
        return null;
    }

    @Override
    public AdminEntity findById(String id) {
        return null;
    }

    @Override
    public AdminEntity findByEmail(String email) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        AdminEntity user = null;

        try {
            String hql = "FROM AdminEntity WHERE email = :email";
            Query<AdminEntity> query = session.createQuery(hql, AdminEntity.class);
            query.setParameter("email", email);
            user = query.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return user;
    }

}
