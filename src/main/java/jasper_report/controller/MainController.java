package jasper_report.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jasper_report.model.Data;
import jasper_report.repository.DataRepo;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final DataRepo dataRepo;

    @PostMapping("/save")
    public String save(){
        for(long i=1;i<=10;i++){
            Data data=Data.builder()
            .id(i)
            .companyName("Company Name Pvt Ltd")
            .address("Opp to someone's house")
            .contact(null)
            .email("gmail@email.com")
            .brokerNumber(10)
            .pan("98562314")
            .billDateAD("2024-12-24")
            .billDateBS("2081-09-09")
            .fiscalYear("2081/82")
            .billNumber("KTM-S8182-0026830")
            .clientName("Client Name")
            .mobile("9865324174")
            .telNumber(null)
            .tranactionNumber("202412240"+i)
            .script("Script Bank Limited [SBL]")
            .quantity(100.0+i)
            .rate(3000.0+i)
            .amount((100.0+i)*(3000.0+i))
            .sebCommission(51.56)
            .commissionRate(0.33)
            .commissionAmount(1000.0)
            .capitalGainTax(0.00+i)
            .effectiveRate(3000.0)
            .total(10000.0)
            .closeoutQuantity(0.00)
            .closeoutAmount(0.00)
            .DpFee(100.0)
            .transactionDate("2024-12-24")
            .clearnaceDate("2024-12-24")
            .signature("Signature")
            .build();

            dataRepo.save(data);
        }
        return "Users saved";
    }
    
}
