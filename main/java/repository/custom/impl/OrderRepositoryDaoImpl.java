package repository.custom.impl;

import entity.OrderDetailsEntity;
import entity.OrderEntity;
import entity.UserEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;
import repository.custom.OrderRepository;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryDaoImpl implements OrderRepository {
    @Override
    public boolean saveOrders(OrderEntity orderEntity,int userId) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        UserEntity user = session.get(UserEntity.class, userId);
        orderEntity.setUser(user);
        session.merge(orderEntity);
        session.getTransaction().commit();
        session.clear();
        return true;
    }


    @Override
    public boolean save(OrderEntity orderEntity) {
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
    public ObservableList<OrderEntity> getAll() {
        ObservableList<OrderEntity> list = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            String hql = "FROM OrderEntity";
            Query<OrderEntity> query = session.createQuery(hql, OrderEntity.class);
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
    public OrderEntity findById(String id) {
        OrderEntity order = null;
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            order = session.get(OrderEntity.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return order;
    }


    @Override
    public List<Object[]> getOrderDistribution() {
        List<Object[]> list = new ArrayList<>();
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();

            String hql = "SELECT o.user.userID, COUNT(o) FROM OrderEntity o GROUP BY o.user.userID";
            Query<Object[]> query = session.createQuery(hql, Object[].class);

            list = query.getResultList();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            session.clear();
        }
        return list;
    }

}
