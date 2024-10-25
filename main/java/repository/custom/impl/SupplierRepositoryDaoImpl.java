package repository.custom.impl;

import dto.Suppliers;
import entity.ProductEntity;
import entity.SupplierEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.custom.SupplierRepositoryDao;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class SupplierRepositoryDaoImpl implements SupplierRepositoryDao {
    @Override
    public boolean save(SupplierEntity supplierEntity) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.merge(supplierEntity);
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
    public ObservableList<SupplierEntity> getAll() {
        ObservableList<SupplierEntity> list = FXCollections.observableArrayList();
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            String hql = "FROM SupplierEntity";
            Query<SupplierEntity> query = session.createQuery(hql, SupplierEntity.class);
            list.addAll(query.getResultList());
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
    }


    @Override
    public SupplierEntity findById(String id) {
        return null;
    }

    @Override
    public boolean removeSupplier(String supplierID) {
            Session session = HibernateUtil.getSession();
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                SupplierEntity supplier = session.get(SupplierEntity.class, supplierID);
                if (supplier != null) {
                    session.delete(supplier);
                    transaction.commit();
                    return true;
                } else {
                    System.out.println("Supplier not found with ID: " + supplierID);
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
}
