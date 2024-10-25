package repository.custom;

import entity.ProductEntity;
import repository.CrudRepository;

import java.util.List;

public interface ProductRepositoryDao extends CrudRepository<ProductEntity> {
    List<ProductEntity> get();
    List<Object[]> getCategoryCount();
    ProductEntity findProductById(String value);
    boolean updateProductById(String id,Double reduceValue);

    List<Object[]> getProductQuantitiesBySupplier();

    ProductEntity findByIdOrName(String text);

    boolean updateProduct(ProductEntity productEntity);

    Boolean deleteProduct(String text);

}


