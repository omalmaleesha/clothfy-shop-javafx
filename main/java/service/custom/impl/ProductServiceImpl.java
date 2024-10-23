package service.custom.impl;

import dto.Products;
import entity.ProductEntity;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.ProductRepositoryDao;
import repository.custom.impl.ProductRepositoryDaoImpl;
import service.custom.ProductService;
import util.DaoType;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    @Override
    public boolean addProduct(Products product) {
        ProductEntity productEntity = new ModelMapper().map(product, ProductEntity.class);
        ProductRepositoryDao dao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);
        boolean save = dao.save(productEntity);
        return save;
    }

    @Override
    public List<Products> getProducts() {
        ProductRepositoryDao dao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);
        List<ProductEntity> list = dao.get();
        List<Products> products = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            Products product = new ModelMapper().map(list.get(i), Products.class);
            products.add(product);
        }

        return products;
    }

    @Override
    public ObservableList<String> getIds() {
        ProductRepositoryDao dao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);
        return dao.getStringIds();
    }

    @Override
    public Products findProductById(String newValue) {
        ProductRepositoryDaoImpl dao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);
        ProductEntity productById = dao.findProductById(newValue);
        Products mapped = new ModelMapper().map(productById, Products.class);
        return mapped;
    }

    @Override
    public List<Object[]> getProductCategoryData(){
        ProductRepositoryDaoImpl dao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);
        return dao.getCategoryCount();
    }

    @Override
    public Products getProduct(String productID) {
        return null;
    }

}
