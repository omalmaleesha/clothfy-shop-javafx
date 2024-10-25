package repository.custom;

import entity.ProductEntity;
import entity.SupplierEntity;
import repository.CrudRepository;

public interface SupplierRepositoryDao extends CrudRepository<SupplierEntity> {
    boolean removeSupplier(String supplierID);
}
