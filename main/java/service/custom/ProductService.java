package service.custom;

import dto.Products;
import javafx.collections.ObservableList;
import service.SuperService;

import java.util.List;

public interface ProductService extends SuperService {
    boolean addProduct(Products product);

    List<Products> getProducts();
    ObservableList<String> getIds();

    Products findProductById(String newValue);

    List<Object[]> getProductCategoryData();

    Products getProduct(String productID);
}
