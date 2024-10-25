package service.custom;

import dto.Users;
import javafx.collections.ObservableList;
import service.SuperService;

import java.util.List;

public interface UserService extends SuperService {
    boolean addUser(Users users);
    boolean conformUser(String email,String password);
    ObservableList<Integer> getIds();


    boolean updatePassword(String text,String email);

    Users findByNameOrId(String text);

    boolean updateUser(Users users);

    Boolean deleteUserById(Integer userIDUpdate);

    List<Users> getAll();
}

