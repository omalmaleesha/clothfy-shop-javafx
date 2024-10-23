package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity  
@Table(name = "OrderDetails")
public class OrderDetailsEntity {
    @EmbeddedId
    private OrderDetailsKey id;

    @ManyToOne
    @MapsId("orderID")
    @JoinColumn(name = "orderID")
    private OrderEntity order;

    @ManyToOne
    @MapsId("productID")
    @JoinColumn(name = "productID")
    private ProductEntity product;

    private Double quantity;
}
