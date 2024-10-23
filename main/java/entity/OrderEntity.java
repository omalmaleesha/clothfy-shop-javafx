package entity;

import dto.OrderDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OrderEntity")
public class OrderEntity {
    @Id
    private String orderID;

    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "UserID")
    private UserEntity user;

    private String paymentType;
    private Double totalCost;
    private LocalTime orderTime;
    private LocalDate orderDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderDetailsEntity> productList;
}
