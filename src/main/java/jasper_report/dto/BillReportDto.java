package jasper_report.dto;

public class BillReportDto extends XmlDto {

    public BillReportDto() {

        super();

        query = "SELECT \n" + //
                "    company_name AS \"Company Name\",\n" + //
                "    address AS \"Address\",\n" + //
                "    contact AS \"Contact\",\n" + //
                "    email AS \"Email\",\n" + //
                "    broker_number AS \"Broker Number\",\n" + //
                "    pan AS \"PAN\",\n" + //
                "    bill_datead AS \"Bill Date (AD)\",\n" + //
                "    bill_datebs AS \"Bill Date (BS)\",\n" + //
                "    fiscal_year AS \"Fiscal Year\",\n" + //
                "    bill_number AS \"Bill Number\",\n" + //
                "    client_name AS \"Client Name\",\n" + //
                "    mobile AS \"Mobile\",\n" + //
                "    tel_number AS \"Telephone Number\",\n" + //
                "    tranaction_number AS \"Transaction Number\",\n" + //
                "    script AS \"Script\",\n" + //
                "    quantity AS \"Quantity\",\n" + //
                "    rate AS \"Rate\",\n" + //
                "    amount AS \"Amount\",\n" + //
                "    seb_commission AS \"SEB Commission\",\n" + //
                "    commission_rate AS \"Commission Rate\",\n" + //
                "    commission_amount AS \"Commission Amount\",\n" + //
                "    capital_gain_tax AS \"Capital Gain Tax\",\n" + //
                "    effective_rate AS \"Effective Rate\",\n" + //
                "    total AS \"Total\",\n" + //
                "    closeout_quantity AS \"Closeout Quantity\",\n" + //
                "    closeout_amount AS \"Closeout Amount\",\n" + //
                "    dp_fee AS \"DP Fee\",\n" + //
                "    transaction_date AS \"Transaction Date\",\n" + //
                "    clearnace_date AS \"Clearance Date\",\n" + //
                "    signature AS \"Signature\"\n" + //
                "FROM data;\n" + //
                "";

        titles.add("TITLE");
        titles.add("TITLE TITLE TITLE");
        titles.add("TITLE TITLE TITLE TITLE TITLE");

        secondaryHeaderText = "As per your order, we have sold these under-noted stocks";

        leftSideHeaderFields.put("Company Name", "");
        leftSideHeaderFields.put("Address", "");
        leftSideHeaderFields.put("Contact", "");
        leftSideHeaderFields.put("Email", "");
        leftSideHeaderFields.put("PAN", "");
        leftSideHeaderFields.put("Broker Number", 0);

        rightSideHeaderFields.put("Bill Date (AD)", "");
        rightSideHeaderFields.put("Bill Date (BS)", "");
        rightSideHeaderFields.put("Fiscal Year", "");
        rightSideHeaderFields.put("Bill Number", "");

        secondaryHeadersFields.put("Client Name", "");
        secondaryHeadersFields.put("Mobile", "");
        secondaryHeadersFields.put("Telephone Number", "");

        detailsFields.put("Transaction Number", "");
        detailsFields.put("Script", "");
        detailsFields.put("Quantity", 0.0);
        detailsFields.put("Rate", 0.0);
        detailsFields.put("Amount", 0.0);
        detailsFields.put("Seb Commission", 0.0);
        detailsFields.put("Commission Rate", 0.0);
        detailsFields.put("Commission Amount", 0.0);
        detailsFields.put("Capital Gain Tax", 0.0);
        detailsFields.put("Effective Rate", 0.0);
        detailsFields.put("Total", 0.0);
        detailsFields.put("Closeout Quantity", 0);
        detailsFields.put("Closeout Amount", 0.0);

        summaryFields.put("DP Fee", 0.0);
        summaryFields.put("Transaction Date", "");
        summaryFields.put("Clearnace Date", "");
        summaryFields.put("Signature", "");

        expression.put("Quantity", "sum");
        expression.put("Rate", "sum");
        expression.put("Amount", "sum");
        expression.put("Seb Commission", "sum");
        expression.put("Commission Rate", "sum");
        expression.put("Commission Amount", "sum");
        expression.put("Capital Gain Tax", "sum");
        expression.put("Effective Rate", "sum");
        expression.put("Total", "sum");

    }

}
