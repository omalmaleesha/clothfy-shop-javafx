package service;

import service.custom.impl.*;
import util.ServiceType;

public class ServiceFactory {
    private static ServiceFactory instance;
    private ServiceFactory(){

    }

    public static ServiceFactory getInstance() {
        return instance==null?instance=new ServiceFactory():instance;
    }

    public  <T extends SuperService>T getService(ServiceType type) {
        switch (type){
            case USER:return (T) new UserServiceImpl();
            case PRODUCT:return (T) new ProductServiceImpl();
            case ORDER:return (T) new OrderServiceImpl();
            case SUPPLIER:return (T) new SupplierServiceImpl();
            case ADMIN:return (T) new AdminServiceImpl();
        }
        return null;
    }
}
