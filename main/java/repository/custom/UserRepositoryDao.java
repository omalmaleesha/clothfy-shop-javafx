package repository.custom;

import entity.UserEntity;
import repository.CrudRepository;

public interface UserRepositoryDao extends CrudRepository<UserEntity> {
    UserEntity findUser(String email, String password);

    UserEntity findUserByEmail(String email);

    boolean updatePassword(String password,String email);

    UserEntity findUserByNameOrId(String text);

    boolean updateUser(UserEntity userEntity);

    Boolean deleteById(Integer userIDUpdate);
}
