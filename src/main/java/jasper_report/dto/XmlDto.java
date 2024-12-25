package jasper_report.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class XmlDto {
    private String query;
    private Map<String,Object> fields=new HashMap<>();
    private Map<String,String> expression=new HashMap<>();

    public XmlDto(){
        this.query = "select * from data where id=1";

        fields.put("companyName","");
        fields.put("address","");
        fields.put("contact","");
        fields.put("email","");
        fields.put("brokerNumber",0);
        fields.put("pan","");
        fields.put("billDateAD","");
        fields.put("billDateBS","");
        fields.put("fiscalYear","");
        fields.put("billNumber","");
        fields.put("clientName","");
        fields.put("mobile","");
        fields.put("telNumber","");
        fields.put("tranactionNumber","");
        fields.put("script","");
        fields.put("quantity",0.0);
        fields.put("rate",0.0);
        fields.put("amount",0.0);
        fields.put("sebCommission",0.0);
        fields.put("commissionRate",0.0);
        fields.put("commissionAmount",0.0);
        fields.put("capitalGainTax",0.0);
        fields.put("effectiveRate",0.0);
        fields.put("total",0.0);
        fields.put("closeoutQuantity",0);
        fields.put("closeoutAmount",0.0);
        fields.put("DpFee",0.0);
        fields.put("transactionDate","");
        fields.put("clearnaceDate","");
        fields.put("signature","");

    }

    
}
