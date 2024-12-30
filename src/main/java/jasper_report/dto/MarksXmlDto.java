package jasper_report.dto;

public class MarksXmlDto extends XmlDto{
    public MarksXmlDto(){
        super();
        query="SELECT " + 
            "    m.subject AS \"Subject\", " + 
            "    m.term AS \"Term\", " + 
            "    m.full_marks AS \"Full Marks\", " + 
            "    m.pass_marks AS \"Pass Marks\", " + 
            "    m.obtained_marks AS \"Obtained Marks\" " + 
            "FROM marks m";

        titles.add("MARKS REPORT");
        titles.add("A SAMPLE REPORT ON JASPER REPORT DESIGN");

        secondaryHeaderText="As per your records, this is the marks report";

        detailsFields.put("Subject","");
        detailsFields.put("Term","");
        detailsFields.put("Full Marks",0.0f);
        detailsFields.put("Pass Marks",0.0f);
        detailsFields.put("Obtained Marks",0f);


        expression.put("Full Marks","sum");
        expression.put("Pass Marks","sum");
        expression.put("Obtained Marks","sum");

        leftSideSummaryFields.put("Total Full Marks", 0.0);
        leftSideSummaryFields.put("Total Pass Marks", 0.0);
        leftSideSummaryFields.put("Total Obtained Marks", 0.0);

    }
    
}
