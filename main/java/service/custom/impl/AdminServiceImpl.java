package service.custom.impl;

import entity.AdminEntity;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.SuperDao;
import repository.custom.AdminRepositoryDao;
import service.custom.AdminService;
import util.DaoType;

public class AdminServiceImpl implements AdminService {
    @Override
    public boolean conformAdmin(String email, String password) {
        AdminRepositoryDao dao = DaoFactory.getInstance().getDao(DaoType.ADMIN);
        AdminEntity byEmail = dao.findByEmail(email);
        return checkPassword(password,byEmail.getPassword());
    }

    private String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    private boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

}
