package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    private String orderID;
    private Integer employeeID;
    private String paymentType;
    private Double totalCost;
    private LocalTime orderTime;
    private LocalDate orderDate;
    private List<OrderDetails> productList;
}
