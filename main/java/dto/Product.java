package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String productID;
    private String name;
    private String category;
    private String size;
    private Double qtyOnHand;
    private String supplierID;
    private Double unitPrice;
    private byte[] image;

}
