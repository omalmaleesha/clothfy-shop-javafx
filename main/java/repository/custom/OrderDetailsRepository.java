package repository.custom;

import entity.OrderDetailsEntity;
import repository.CrudRepository;

import java.util.List;

public interface OrderDetailsRepository  extends CrudRepository<OrderDetailsEntity> {
    List<OrderDetailsEntity> findByOrderId(String orderId);
}
