package service.custom.impl;

import dto.Suppliers;
import entity.SupplierEntity;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.SupplierRepositoryDao;
import service.custom.SupplierService;
import util.DaoType;

import java.util.ArrayList;
import java.util.List;

public class SupplierServiceImpl implements SupplierService {
    @Override
    public boolean addSupplier(Suppliers suppliers) {
        SupplierRepositoryDao dao = DaoFactory.getInstance().getDao(DaoType.SUPPLIER);
        SupplierEntity map = new ModelMapper().map(suppliers, SupplierEntity.class);
        return dao.save(map);
    }

    @Override
    public List<Suppliers> getAll() {
        SupplierRepositoryDao dao = DaoFactory.getInstance().getDao(DaoType.SUPPLIER);
        ObservableList<SupplierEntity> all = dao.getAll();
        List<Suppliers> suppliers = new ArrayList<>();
        all.forEach(obj-> suppliers.add(new ModelMapper().map(obj, Suppliers.class)));
        return suppliers;
    }

    @Override
    public boolean removeSupplier(String supplierID) {
        SupplierRepositoryDao dao = DaoFactory.getInstance().getDao(DaoType.SUPPLIER);
        return dao.removeSupplier(supplierID);
    }
}
