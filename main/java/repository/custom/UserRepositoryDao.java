package repository.custom;

import entity.UserEntity;
import repository.CrudRepository;

public interface UserRepositoryDao extends CrudRepository<UserEntity> {
    UserEntity findUser(String email, String password);


    UserEntity findUserByEmail(String email);

    boolean updatePassword(String text,String email);
}
