package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCart {
    private String productID;
    private String name;
    private Double unitPrice;
    private Double qty;
    private Double total;
    private Double discount;


}
