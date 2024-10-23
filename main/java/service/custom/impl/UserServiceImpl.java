package service.custom.impl;

import entity.UserEntity;
import dto.Users;
import javafx.collections.ObservableList;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.UserRepositoryDao;
import repository.custom.impl.UserRepositoryDaoImpl;
import service.custom.UserService;
import util.DaoType;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserServiceImpl implements UserService {
    @Override
    public boolean addUser(Users users) {
        UserEntity userEntity = new ModelMapper().map(users, UserEntity.class);
        UserRepositoryDao dao = DaoFactory.getInstance().getDao(DaoType.USER);
        dao.save(userEntity);
        return true;
    }

    @Override
    public boolean conformUser(String email, String password) {
        UserRepositoryDaoImpl dao = DaoFactory.getInstance().getDao(DaoType.USER);
        UserEntity user = dao.findUserByEmail(email);
        Users map = new ModelMapper().map(user, Users.class);
        if(map.getPassword().equals(password)){
            return true;
        }
        return false;
    }


    @Override
    public ObservableList<Integer> getIds() {
        UserRepositoryDao dao = DaoFactory.getInstance().getDao(DaoType.USER);
        ObservableList<Integer> ids = dao.getIds();
        return ids;
    }

    @Override
    public String encryptPassword(String text) {
        return "";
    }

    @Override
    public boolean updatePassword(String text,String email) {
        UserRepositoryDao dao = DaoFactory.getInstance().getDao(DaoType.USER);
        return dao.updatePassword(text,email);
    }
}
