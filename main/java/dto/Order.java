package dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class Order {
    private String orderID;
    private String employeeID;
    private String paymentType;
    private Double totalCost;
    private LocalTime orderTime;
    private LocalDate orderDate;
}
