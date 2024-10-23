package repository.custom.impl;

import entity.ProductEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.custom.ProductRepositoryDao;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryDaoImpl implements ProductRepositoryDao {
    @Override
    public boolean save(ProductEntity productEntity) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.merge(productEntity);
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
        List<ProductEntity> list = get();
        ObservableList<String> ids = FXCollections.observableArrayList();
        for (ProductEntity product : list){
            ids.add(String.valueOf(product.getProductID()));
        }
        return ids;
    }

    @Override
    public ObservableList<ProductEntity> getAll() {
        return null;
    }

    @Override
    public ProductEntity findById(String id) {
        Session session = HibernateUtil.getSession();
        ProductEntity product = null;

        try {
            String hql = "FROM ProductEntity WHERE productID = :id";
            Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
            query.setParameter("id", id);
            product = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return product;
    }

    @Override
    public List<ProductEntity> get() {
        List<ProductEntity> list = new ArrayList<>();
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            String hql = "FROM ProductEntity";
            Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.getTransaction().commit();
            session.clear();
        }
        return list;
    }

    @Override
    public List<Object[]> getCategoryCount() {
        List<Object[]> list = new ArrayList<>();
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            String hql = "SELECT p.category, COUNT(p) FROM ProductEntity p GROUP BY p.category";
            Query<Object[]> query = session.createQuery(hql, Object[].class);
            list = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.getTransaction().commit();
            session.clear();
        }
        return list;
    }

    @Override
    public ProductEntity findProductById(String value) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        ProductEntity productEntity = session.get(ProductEntity.class, value);
        session.getTransaction().commit();
        session.clear();
        return productEntity;
    }

    @Override
    public boolean updateProductById(String id,Double reduceValue) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        boolean isUpdated = false;

        try {
            transaction = session.beginTransaction();

            ProductEntity product = session.get(ProductEntity.class, id);

            if (product != null && product.getQtyOnHand() >= reduceValue) {
                product.setQtyOnHand(product.getQtyOnHand() - reduceValue);
                session.update(product);
                transaction.commit();
                isUpdated = true;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return isUpdated;
    }


}
