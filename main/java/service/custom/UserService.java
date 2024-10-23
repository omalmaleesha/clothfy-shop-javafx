package service.custom;

import dto.Users;
import javafx.collections.ObservableList;
import service.SuperService;

public interface UserService extends SuperService {
    boolean addUser(Users users);
    boolean conformUser(String email,String password);
    ObservableList<Integer> getIds();

    String encryptPassword(String text);

    boolean updatePassword(String text,String email);
}

