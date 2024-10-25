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

    @Override
    public List<Object[]> getProductQuantitiesBySupplier() {
        try (Session session = HibernateUtil.getSession()) {
            String hql = "SELECT s.supplierID, SUM(p.qtyOnHand) " +
                    "FROM ProductEntity p " +
                    "JOIN p.supplier s " +
                    "GROUP BY s.supplierID";
            Query query = session.createQuery(hql);
            return query.getResultList();
        }
    }

    @Override
    public ProductEntity findByIdOrName(String text) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        ProductEntity product = null;

        try {
            String hql = "FROM ProductEntity WHERE productID = :text OR name = :text";
            Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
            query.setParameter("text", text);
            product = query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return product;
    }

    @Override
    public boolean updateProduct(ProductEntity productEntity) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        boolean isUpdated = false;

        try {
            session.merge(productEntity);
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
    public Boolean deleteProduct(String productId) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        boolean isDeleted = false;

        try {
            String deleteOrderDetailsHQL = "DELETE FROM OrderDetailsEntity WHERE product.productID = :productId";
            Query<?> deleteOrderDetailsQuery = session.createQuery(deleteOrderDetailsHQL);
            deleteOrderDetailsQuery.setParameter("productId", productId);
            deleteOrderDetailsQuery.executeUpdate();
            ProductEntity product = session.get(ProductEntity.class, productId);
            if (product != null) {
                session.delete(product);
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
