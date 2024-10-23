package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Suppliers {
    private String supplierID;
    private String name;
    private String company;
    private String email;
}
