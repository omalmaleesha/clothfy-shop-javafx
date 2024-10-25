package repository.custom;

import entity.OrderEntity;
import repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<OrderEntity> {
    List<Object[]> getOrderDistribution();
    boolean saveOrders(OrderEntity orderEntity,int userId);
}
