package service.custom;

import service.SuperService;

public interface AdminService extends SuperService {
    boolean conformAdmin(String email, String password);
}
