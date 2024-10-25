package service.custom;

import dto.Suppliers;
import service.SuperService;

import java.util.List;

public interface SupplierService extends SuperService {
    boolean addSupplier(Suppliers suppliers);
    List<Suppliers> getAll();

    boolean removeSupplier(String supplierID);
}

