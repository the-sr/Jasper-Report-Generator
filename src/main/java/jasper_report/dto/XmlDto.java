package jasper_report.dto;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class XmlDto {
    private String query;
    private Map<String, Object> pageHeaderFields = new LinkedHashMap<>();
    private Map<String, Object> clientFields = new LinkedHashMap<>();
    private Map<String, Object> detailsFields = new LinkedHashMap<>();
    private Map<String, Object> summaryFields = new LinkedHashMap<>();

    public XmlDto() {
        this.query = "select * from data where id=1";

        pageHeaderFields.put("company_name", "");
        pageHeaderFields.put("bill_datead", "");

        pageHeaderFields.put("address", "");
        pageHeaderFields.put("bill_datebs", "");

        pageHeaderFields.put("contact", "");
        pageHeaderFields.put("fiscal_year", "");

        pageHeaderFields.put("email", "");
        pageHeaderFields.put("bill_number", "");

        pageHeaderFields.put("broker_number", 0);
        pageHeaderFields.put("pan", "");

        clientFields.put("client_name", "");
        clientFields.put("mobile", "");
        clientFields.put("tel_number", "");

        detailsFields.put("tranaction_number", "");
        detailsFields.put("script", "");
        detailsFields.put("quantity", 0.0);
        detailsFields.put("rate", 0.0);
        detailsFields.put("amount", 0.0);
        detailsFields.put("seb_commission", 0.0);
        detailsFields.put("commission_rate", 0.0);
        detailsFields.put("commission_amount", 0.0);
        detailsFields.put("capital_gain_tax", 0.0);
        detailsFields.put("effective_rate", 0.0);
        detailsFields.put("total", 0.0);
        detailsFields.put("closeout_quantity", 0);
        detailsFields.put("closeout_amount", 0.0);

        summaryFields.put("dp_fee", 0.0);
        summaryFields.put("transaction_date", "");
        summaryFields.put("clearnace_date", "");
        summaryFields.put("signature", "");

    }

}
