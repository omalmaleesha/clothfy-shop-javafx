package repository.custom;

import entity.AdminEntity;
import repository.CrudRepository;

public interface AdminRepositoryDao extends CrudRepository<AdminEntity> {

    AdminEntity findByEmail(String email);
}
