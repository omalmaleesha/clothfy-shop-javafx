package repository;

import repository.custom.impl.*;
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
            case SUPPLIER:return (T) new SupplierRepositoryDaoImpl();
            case ADMIN:return (T) new AdminRepositoryDaoImpl();
        }
        return null;
    }
}
