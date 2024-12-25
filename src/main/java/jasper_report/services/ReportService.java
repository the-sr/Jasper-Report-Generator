package jasper_report.services;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import jasper_report.dto.XmlDto;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;

@Service
public class ReportService {
    
    public String generateReport(){

        XmlDto dto=new XmlDto();

        JasperDesign jasperDesign =new JasperDesign();
        jasperDesign.setName("Jasper Report");
        jasperDesign.setPageHeight(545);
        jasperDesign.setPageWidth(842);
        jasperDesign.setColumnWidth(800);

        JRDesignQuery query=new JRDesignQuery();
        query.setText(dto.getQuery());
        jasperDesign.setQuery(query);

        Map<String,Object> fieldsMap=dto.getFields();
        Set<String> fieldsSet=fieldsMap.keySet();
        fieldsSet.forEach(field->{
            JRDesignField designField=new JRDesignField();
            designField.setName(field);
            designField.setValueClass(fieldsMap.get(field).getClass());
            try {
                jasperDesign.addField(designField);
            } catch (JRException e) {
                e.printStackTrace();
            }
        });

        return "Report genereted";
    }

}
