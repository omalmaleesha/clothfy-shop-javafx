package service.custom;

import dto.User;
import service.SuperService;

public interface UserService extends SuperService {
    boolean addUser(User user);
    boolean conformUser(String email,String password);
}
