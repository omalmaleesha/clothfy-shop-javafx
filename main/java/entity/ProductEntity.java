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
    private String name;
    private String category;
    private String size;
    private Double qtyOnHand;
    @ManyToOne
    @JoinColumn(name = "supplierID", referencedColumnName = "supplierID")
    private SupplierEntity supplier;
    private Double unitPrice;
    @Lob
    @Column(name = "image", columnDefinition = "BLOB")
    private byte[] image;
}
