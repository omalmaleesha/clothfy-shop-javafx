package repository.custom.impl;

import entity.OrderDetailsEntity;
import entity.UserEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;
import repository.custom.OrderDetailsRepository;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsRepositoryDaoImpl implements OrderDetailsRepository {
    @Override
    public boolean save(OrderDetailsEntity orderDetailsEntity) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.merge(orderDetailsEntity);
        session.getTransaction().commit();
        session.clear();
        return true;
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
    public ObservableList<OrderDetailsEntity> getAll() {
        ObservableList<OrderDetailsEntity> list = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            String hql = "FROM OrderDetailsEntity";
            Query<OrderDetailsEntity> query = session.createQuery(hql, OrderDetailsEntity.class);
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
    public OrderDetailsEntity findById(String id) {
        OrderDetailsEntity orderDetails = null;
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            orderDetails = session.get(OrderDetailsEntity.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return orderDetails;
    }

    @Override
    public List<OrderDetailsEntity> findByOrderId(String orderId) {
        List<OrderDetailsEntity> orderDetailsList = new ArrayList<>();
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();

            String hql = "FROM OrderDetailsEntity od WHERE od.order.orderID = :orderId";
            Query<OrderDetailsEntity> query = session.createQuery(hql, OrderDetailsEntity.class);
            query.setParameter("orderId", orderId);

            orderDetailsList = query.getResultList();

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return orderDetailsList;
    }

}
