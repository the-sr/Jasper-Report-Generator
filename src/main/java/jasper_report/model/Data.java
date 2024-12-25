package jasper_report.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "data")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Data {

    @Id
    private Long id;
    private String companyName;
    private String address;
    private String contact;
    private String email;
    private Integer brokerNumber;
    private String pan;
    private String billDateAD;
    private String billDateBS;
    private String fiscalYear;
    private String billNumber;
    private String clientName;
    private String mobile;
    private String telNumber;
    private String tranactionNumber;
    private String script;
    private Double quantity;
    private Double rate;
    private Double amount;
    private Double sebCommission;
    private Double commissionRate;
    private Double commissionAmount;
    private Double capitalGainTax;
    private Double effectiveRate;
    private Double total;
    private Double closeoutQuantity;
    private Double closeoutAmount;
    
}
