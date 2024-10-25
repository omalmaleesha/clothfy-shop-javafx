package repository.custom.impl;

import entity.ProductEntity;
import entity.UserEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.custom.UserRepositoryDao;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryDaoImpl implements UserRepositoryDao {

    @Override
    public boolean save(UserEntity user) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.merge(user);
        session.getTransaction().commit();
        session.clear();
        return true;
    }


    @Override
    public ObservableList<Integer> getIds() {
        ObservableList<UserEntity> all = getAll();
        ObservableList<Integer> ids = FXCollections.observableArrayList();
        all.forEach(obj->{
            ids.add(obj.getUserID());
        });
        return ids;
    }


    @Override
    public ObservableList<String> getStringIds() {
        return null;
    }


    @Override
    public ObservableList<UserEntity> getAll() {
        ObservableList<UserEntity> list = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            String hql = "FROM UserEntity";
            Query<UserEntity> query = session.createQuery(hql, UserEntity.class);
            list.addAll(query.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.getTransaction().commit();
            session.clear();
        }
        return list;
    }


    @Override
    public UserEntity findById(String id) {
        return null;
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


    @Override
    public UserEntity findUserByEmail(String email) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        UserEntity user = null;

        try {
            String hql = "FROM UserEntity WHERE email = :email";
            Query<UserEntity> query = session.createQuery(hql, UserEntity.class);
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


    @Override
    public boolean updatePassword(String password, String email) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            UserEntity user = (UserEntity) session.createQuery("FROM UserEntity WHERE email = :email")
                    .setParameter("email", email)
                    .uniqueResult();

            if (user != null) {
                user.setPassword(password);
                session.update(user);

                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }


    @Override
    public UserEntity findUserByNameOrId(String text) {
        Session session = HibernateUtil.getSession();
        UserEntity user = null;

        try {
            boolean isNumeric = text.matches("\\d+");

            String hql;
            Query<UserEntity> query;

            if (isNumeric) {
                hql = "FROM UserEntity WHERE userID = :userId";
                query = session.createQuery(hql, UserEntity.class);
                query.setParameter("userId", Integer.parseInt(text));
            } else {
                hql = "FROM UserEntity WHERE name = :name";
                query = session.createQuery(hql, UserEntity.class);
                query.setParameter("name", text);
            }

            user = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;
    }



    @Override
    public boolean updateUser(UserEntity userEntity) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        boolean isUpdated = false;

        try {
            session.merge(userEntity);
            transaction.commit();
            isUpdated = true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return isUpdated;
    }


    @Override
    public Boolean deleteById(Integer userIDUpdate) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        boolean isDeleted = false;

        try {
            UserEntity userEntity = session.get(UserEntity.class, userIDUpdate);
            if (userEntity != null) {
                String deleteOrderDetailsHQL = "DELETE FROM OrderDetailsEntity WHERE order.user.userID = :userId";
                Query<?> deleteOrderDetailsQuery = session.createQuery(deleteOrderDetailsHQL);
                deleteOrderDetailsQuery.setParameter("userId", userIDUpdate);
                deleteOrderDetailsQuery.executeUpdate();

                String deleteOrdersHQL = "DELETE FROM OrderEntity WHERE user.userID = :userId";
                Query<?> deleteOrdersQuery = session.createQuery(deleteOrdersHQL);
                deleteOrdersQuery.setParameter("userId", userIDUpdate);
                deleteOrdersQuery.executeUpdate();

                session.delete(userEntity);
                transaction.commit();
                isDeleted = true;
            } else {
                transaction.rollback();
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return isDeleted;
    }

}
