package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ProductEntity")
public class ProductEntity {
    @Id
    private String productID;
    @Column(name = "name")
    private String name;
    @Column(name = "category")
    private String category;
    @Column(name = "size")
    private String size;
    @Column(name = "qtyOnHand")
    private Double qtyOnHand;
    @Column(name = "supplierID")
    private String supplierID;
    @Column(name = "unitPrice")
    private Double unitPrice;
    @Lob
    @Column(name = "image", columnDefinition = "BLOB")
    private byte[] image;
}
