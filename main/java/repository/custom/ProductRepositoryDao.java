package repository.custom;

import entity.ProductEntity;
import entity.UserEntity;
import repository.CrudRepository;

import java.util.List;

public interface ProductRepositoryDao extends CrudRepository<ProductEntity> {
    List<ProductEntity> get();
}
