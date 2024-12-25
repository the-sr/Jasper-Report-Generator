package jasper_report.services;

import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.design.JasperDesign;

@Service
public class ReportService {
    
    public String generateReport(){

        JasperDesign jasperDesign =new JasperDesign();
        jasperDesign.setName("Jasper Report");
        jasperDesign.setPageHeight(545);
        jasperDesign.setPageWidth(842);
        jasperDesign.setColumnWidth(800);

        return "Report genereted";
    }

}
