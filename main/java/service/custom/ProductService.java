package service.custom;

import dto.Product;
import entity.ProductEntity;
import service.SuperService;

import java.util.List;

public interface ProductService extends SuperService {
    boolean addProduct(Product product);

    List<Product> getProducts();
}
