package service.custom.impl;

import entity.UserEntity;
import dto.User;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.UserRepositoryDao;
import service.custom.UserService;
import util.DaoType;

public class UserServiceImpl implements UserService {
    @Override
    public boolean addUser(User user) {
        UserEntity userEntity = new ModelMapper().map(user, UserEntity.class);
        UserRepositoryDao dao = DaoFactory.getInstance().getDao(DaoType.USER);
        dao.save(userEntity);
        return true;
    }

    @Override
    public boolean conformUser(String email, String password) {
        UserRepositoryDao dao = DaoFactory.getInstance().getDao(DaoType.USER);
        UserEntity user = dao.findUser(email, password);
        System.out.println(user);
        if(user.getEmail().equals(email) && user.getPassword().equals(password)){
            return true;
        }

        return false;
    }
}
