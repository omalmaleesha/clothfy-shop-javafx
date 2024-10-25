package service.custom.impl;

import dto.OrderDetails;
import dto.Orders;
import entity.OrderDetailsEntity;
import entity.OrderEntity;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.SuperDao;
import repository.custom.impl.OrderDetailsRepositoryDaoImpl;
import repository.custom.impl.OrderRepositoryDaoImpl;
import repository.custom.impl.ProductRepositoryDaoImpl;
import service.custom.OrderService;
import util.DaoType;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    @Override
    public void placeOrder(Orders orders,int userid) {
        OrderEntity map = new ModelMapper().map(orders, OrderEntity.class);
        OrderRepositoryDaoImpl dao = DaoFactory.getInstance().getDao(DaoType.ORDER);
        boolean save = dao.saveOrders(map,userid);
        if(save){
            addOrderDetails(orders);
        }
    }

    @Override
    public Orders getById(String id) {
        OrderRepositoryDaoImpl dao = DaoFactory.getInstance().getDao(DaoType.ORDER);
        OrderEntity byId = dao.findById(id);
        Orders map = new ModelMapper().map(byId, Orders.class);
        return map;
    }

    @Override
    public List<Object[]> getOrderDistributeData() {
        OrderRepositoryDaoImpl dao = DaoFactory.getInstance().getDao(DaoType.ORDER);
        return dao.getOrderDistribution();
    }

    @Override
    public List<OrderDetails> getOrderDetailsAll() {
        OrderDetailsRepositoryDaoImpl dao = DaoFactory.getInstance().getDao(DaoType.ORDERDETAILS);
        ObservableList<OrderDetailsEntity> all = dao.getAll();
        List<OrderDetails> orderDetails = new ArrayList<>();
        all.forEach(obj->{
            orderDetails.add(new ModelMapper().map(obj,OrderDetails.class));
        });
        return orderDetails;
    }

    @Override
    public List<OrderDetails> getOrderDetails(String orderId) {
        OrderDetailsRepositoryDaoImpl dao = DaoFactory.getInstance().getDao(DaoType.ORDERDETAILS);

        List<OrderDetailsEntity> orderDetailsEntities = dao.findByOrderId(orderId);
        List<OrderDetails> orderDetailsList = new ArrayList<>();

        for (OrderDetailsEntity entity : orderDetailsEntities) {
            OrderDetails orderDetails = new ModelMapper().map(entity, OrderDetails.class);
            orderDetailsList.add(orderDetails);
        }
        return orderDetailsList;
    }


    private void addOrderDetails(Orders orders){
        List<OrderDetails> productList = orders.getProductList();
        productList.forEach(obj->{
            OrderDetailsEntity map = new ModelMapper().map(obj, OrderDetailsEntity.class);
            OrderDetailsRepositoryDaoImpl dao = DaoFactory.getInstance().getDao(DaoType.ORDERDETAILS);
            boolean saved = dao.save(map);
            if(saved){
                updateStock(obj);
            }
        });


    }

    private void updateStock(OrderDetails obj) {
        ProductRepositoryDaoImpl dao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);
        boolean b = dao.updateProductById(obj.getProductID(), obj.getQuantity());
        if(b){
            new Alert(Alert.AlertType.INFORMATION,"Done").show();
        }

    }
}
