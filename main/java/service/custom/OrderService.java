package service.custom;

import dto.OrderDetails;
import dto.Orders;
import entity.OrderDetailsEntity;
import service.SuperService;

import java.util.List;

public interface OrderService extends SuperService {
    void placeOrder(Orders orders,int userid);
    Orders getById(String id);
    List<Object[]> getOrderDistributeData();

    List<OrderDetails> getOrderDetailsAll();

    List<OrderDetails> getOrderDetails(String text);
}

