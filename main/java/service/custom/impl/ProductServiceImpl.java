package service.custom.impl;

import dto.Product;
import entity.ProductEntity;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.SuperDao;
import repository.custom.ProductRepositoryDao;
import service.custom.ProductService;
import util.DaoType;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    @Override
    public boolean addProduct(Product product) {
        ProductEntity productEntity = new ModelMapper().map(product, ProductEntity.class);
        ProductRepositoryDao dao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);
        boolean save = dao.save(productEntity);
        return save;
    }

    @Override
    public List<Product> getProducts() {
        ProductRepositoryDao dao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);
        List<ProductEntity> list = dao.get();
        List<Product> products = new ArrayList<>();  // Initialize the products list

        for (int i = 0; i < list.size(); i++) {
            Product product = new ModelMapper().map(list.get(i), Product.class);
            products.add(product);  // Now this will work because products is initialized
        }

        return products;
    }

}
