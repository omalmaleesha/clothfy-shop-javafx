package repository;

import repository.custom.impl.OrderDetailsRepositoryDaoImpl;
import repository.custom.impl.OrderRepositoryDaoImpl;
import repository.custom.impl.ProductRepositoryDaoImpl;
import repository.custom.impl.UserRepositoryDaoImpl;
import util.DaoType;

public class DaoFactory {
    private static DaoFactory instance;
    private DaoFactory(){

    }

    public static DaoFactory getInstance() {
        return instance==null?instance=new DaoFactory():instance;
    }

    public  <T extends SuperDao>T getDao(DaoType type) {
        switch (type){
            case USER:return (T) new UserRepositoryDaoImpl();
            case PRODUCT:return (T) new ProductRepositoryDaoImpl();
            case ORDER:return (T) new OrderRepositoryDaoImpl();
            case ORDERDETAILS:return (T) new OrderDetailsRepositoryDaoImpl();
        }
        return null;
    }
}
