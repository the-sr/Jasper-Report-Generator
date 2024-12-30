package jasper_report.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="product")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Product {

    @Id
    private Long id;
    
    private String productName;
    private Integer quantity;
    private Float rate;
    private Double amount;
    private Float discountRate;
    private Double discountAmount;
    private Double finalAmount;
    private Date createdDate;

}
