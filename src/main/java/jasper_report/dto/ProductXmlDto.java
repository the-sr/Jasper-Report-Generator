package jasper_report.dto;

import java.util.Date;

public class ProductXmlDto extends XmlDto{

    public ProductXmlDto(){
        super();
        query="SELECT " + 
            "    product_name AS \"Product Name\", " + 
            "    quantity AS \"Quantity\", " + 
            "    rate AS \"Rate\", " + 
            "    amount AS \"Amount\", " + 
            "    discount_rate AS \"Discount Rate\", " + 
            "    discount_amount AS \"Discount Amount\", " +
            "    final_amount AS \"Final Amount\","+
            "    created_date AS \"Date\""+
            "FROM product";

        titles.add("PROUDUCT REPORT");
        titles.add("A SAMPLE REPORT ON JASPER REPORT DESIGN");
        
        rightSideHeaderFields.put("Date",new Date());

        secondaryHeaderText="As per your records, this is the product report";

        detailsFields.put("Product Name","");
        detailsFields.put("Quantity",0);
        detailsFields.put("Rate",0.0f);
        detailsFields.put("Amount",0.0);
        detailsFields.put("Discount Rate",0f);
        detailsFields.put("Discount Amount",0.0);
        detailsFields.put("Final Amount",0.0);

        expression.put("Quantity","sum");
        expression.put("Amount","sum");
        expression.put("Discount Amount","sum");
        expression.put("Final Amount","sum");
    }
    
}
