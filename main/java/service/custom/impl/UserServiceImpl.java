package service.custom.impl;

import dto.Products;
import entity.UserEntity;
import dto.Users;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
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
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    @Override
    public boolean addUser(Users users) {
        UserEntity userEntity = new ModelMapper().map(users, UserEntity.class);
        userEntity.setPassword(encryptPassword(userEntity.getPassword()));
        UserRepositoryDao dao = DaoFactory.getInstance().getDao(DaoType.USER);
        dao.save(userEntity);
        return true;
    }

    @Override
    public boolean conformUser(String email, String password) {
        UserRepositoryDaoImpl dao = DaoFactory.getInstance().getDao(DaoType.USER);
        UserEntity user = dao.findUserByEmail(email);
        if(checkPassword(password, user.getPassword())){
            Users map = new ModelMapper().map(user, Users.class);
            return true;
        }else {
            new Alert(Alert.AlertType.WARNING,"Password Incorrect").show();
            return false;
        }


    }


    @Override
    public ObservableList<Integer> getIds() {
        UserRepositoryDao dao = DaoFactory.getInstance().getDao(DaoType.USER);
        ObservableList<Integer> ids = dao.getIds();
        return ids;
    }


    private String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }


    @Override
    public boolean updatePassword(String password,String email) {
        UserRepositoryDao dao = DaoFactory.getInstance().getDao(DaoType.USER);
        return dao.updatePassword(encryptPassword(password),email);
    }

    @Override
    public Users findByNameOrId(String text) {
        UserRepositoryDao dao = DaoFactory.getInstance().getDao(DaoType.USER);
        return new ModelMapper().map((dao.findUserByNameOrId(text)),Users.class);
    }

    @Override
    public boolean updateUser(Users users) {
        UserEntity userEntity = new ModelMapper().map(users, UserEntity.class);
        userEntity.setPassword(encryptPassword(userEntity.getPassword()));
        UserRepositoryDao dao = DaoFactory.getInstance().getDao(DaoType.USER);
        return dao.updateUser(userEntity);
    }

    @Override
    public Boolean deleteUserById(Integer userIDUpdate) {
        UserRepositoryDao dao = DaoFactory.getInstance().getDao(DaoType.USER);
        return dao.deleteById(userIDUpdate);
    }

    @Override
    public List<Users> getAll() {
        UserRepositoryDao dao = DaoFactory.getInstance().getDao(DaoType.USER);
        ObservableList<UserEntity> all = dao.getAll();
        List<Users> users = all.stream().map(userEntity -> new ModelMapper().map(userEntity, Users.class)).collect(Collectors.toList());
        return users;
    }
}
