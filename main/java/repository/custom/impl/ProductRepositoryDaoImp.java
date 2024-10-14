package repository.custom.impl;

import entity.ProductEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import repository.custom.ProductRepositoryDao;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryDaoImp implements ProductRepositoryDao {
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

}
