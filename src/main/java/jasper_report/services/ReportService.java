package jasper_report.services;

import jasper_report.dto.XmlDto;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.VerticalTextAlignEnum;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ReportService {

    @Scheduled(cron = "*/20 * * * * *")
    public void generateReport() throws JRException, ClassNotFoundException, SQLException {

        JasperDesign jasperDesign = reportDesign();

        XmlDto dto = new XmlDto();
        JRDesignQuery query = new JRDesignQuery();
        query.setText(dto.getQuery());
        jasperDesign.setQuery(query);

        // --------------------------------BANDS-------------------------------

        JRDesignBand titleBand = titleBand(jasperDesign);
        jasperDesign.setTitle(titleBand);

        JRDesignBand headerBand = headerBand(jasperDesign);
        jasperDesign.setPageHeader(headerBand);

        JRDesignBand columnHeaderBand = columnHeaderBand(jasperDesign);
        jasperDesign.setColumnHeader(columnHeaderBand);

        JRDesignBand detailBand = detailBand(jasperDesign);
        ((JRDesignSection) jasperDesign.getDetailSection()).addBand(detailBand);

        // JRDesignBand columnFooterBand=columnFooterBand(jasperDesign);
        // jasperDesign.setColumnFooter(columnFooterBand);

        // _____REPORT_GENERATION_________
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        Connection con;
        Class.forName("org.postgresql.Driver");
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jasper_demo?user=postgres&password=root");
        Map<String, Object> parameters = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, con);
        JasperExportManager.exportReportToPdfFile(jasperPrint, "Report.pdf");
        System.out.println("Report generated successfully!");
    }

    private JasperDesign reportDesign() {
        JasperDesign jasperDesign = new JasperDesign();
        jasperDesign.setName("Report");
        jasperDesign.setPageHeight(595);
        jasperDesign.setPageWidth(1100);
        jasperDesign.setColumnWidth(1040);
        return jasperDesign;
    }

    private JRDesignBand titleBand(JasperDesign design) {
        JRDesignBand titleBand = new JRDesignBand();
        titleBand.setHeight(30);

        return titleBand;
    }

    private JRDesignBand headerBand(JasperDesign design) {

        JRDesignBand band = new JRDesignBand();
        band.setHeight(200);

        JRDesignStaticText title1 = staticText(0, 0, 1040, 20);
        title1.setText("TITLE");
        band.addElement(title1);

        JRDesignStaticText title2 = staticText(0, 20, 1040, 20);
        title2.setText("TITLE TITLE TITLE TITLE TITLE");
        band.addElement(title2);

        JRDesignStaticText title3 = staticText(0, 40, 1040, 20);
        title3.setText("TITLE TITLE TITLE TITLE TITLE TITLE TITLE TITLE TITLE TITLE");
        band.addElement(title3);

        XmlDto dto = new XmlDto();

        Map<String, Object> fieldsMap = dto.getPageHeaderFields();
        Set<String> fieldsSet = fieldsMap.keySet();

        AtomicInteger x = new AtomicInteger(0);
        AtomicInteger y = new AtomicInteger(60);
        AtomicInteger fieldCounter = new AtomicInteger();
        fieldsSet.forEach(f -> {
            JRDesignField field = new JRDesignField();
            field.setName(f);
            field.setValueClass(fieldsMap.get(f).getClass());
            try {
                design.addField(field);
            } catch (JRException e) {
                throw new RuntimeException(e);
            }
            if (f.equalsIgnoreCase("contact") || f.equalsIgnoreCase("email") || f.equalsIgnoreCase("broker_number")) {
                JRDesignStaticText text = new JRDesignStaticText();
                text.setText(f + ":");
                text.setX(x.get());
                text.setY(y.get());
                text.setWidth(50);
                text.setHeight(20);
                band.addElement(text);
                x.getAndAdd(50);
            }
            if (fieldCounter.get() == 0) {
                JRDesignExpression expression = new JRDesignExpression();
                expression.setText("$F{" + f + "}");

                JRDesignTextField textField = new JRDesignTextField();
                textField.setExpression(expression);
                textField.setX(x.get());
                textField.setY(y.get());
                textField.setHeight(20);
                textField.setWidth(500);
                textField.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
                band.addElement(textField);
                x.getAndAdd(500);
                fieldCounter.set(1);
            } else {
                JRDesignExpression expression = new JRDesignExpression();
                expression.setText("$F{" + f + "}");

                JRDesignTextField textField = new JRDesignTextField();
                textField.setExpression(expression);
                textField.setX(x.get());
                textField.setY(y.get());
                textField.setHeight(20);
                textField.setWidth(500);
                textField.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
                band.addElement(textField);

                x.set(0);
                y.set(y.get() + 20);
                fieldCounter.set(0);
            }

        });

        band.addElement(designLine(x.get(), y.get(), 1040, 0));

        x.set(0);
        y.set(0);

        Map<String, Object> clientFieldsMap = dto.getClientFields();
        Set<String> fieldSet = clientFieldsMap.keySet();
        fieldSet.forEach(f -> {
            JRDesignField field = new JRDesignField();
            field.setName(f);
            field.setValueClass(clientFieldsMap.get(f).getClass());
            try {
                design.addField(field);
            } catch (JRException e) {
                e.printStackTrace();
            }

            JRDesignExpression expression = new JRDesignExpression();
            expression.setText("$F{" + f + "}");

            JRDesignTextField textField = new JRDesignTextField();
            textField.setExpression(expression);
            textField.setX(x.get());
            textField.setY(y.get());
            textField.setWidth(80);
            textField.setHeight(20);
            band.addElement(textField);
        });

        return band;
    }

    private JRDesignBand columnHeaderBand(JasperDesign design) {
        JRDesignBand band = new JRDesignBand();
        band.setHeight(20);

        band.addElement(designLine(0, 0, 1040, 0));

        XmlDto dto = new XmlDto();
        Map<String, Object> fieldsMap = dto.getDetailsFields();
        Set<String> fieldsSet = fieldsMap.keySet();
        AtomicInteger x = new AtomicInteger(0);
        fieldsSet.forEach(f -> {
            JRDesignStaticText text = new JRDesignStaticText();
            text.setText(f);
            text.setX(x.get());
            text.setY(0);
            text.setWidth(80);
            text.setHeight(20);
            text.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
            band.addElement(text);

            band.addElement(designLine(x.get(), 0, 0, 20));

            x.getAndAdd(80);
        });
        band.addElement(designLine(x.get(), 0, 0, 20));
        return band;
    }

    private JRDesignBand detailBand(JasperDesign design) {

        JRDesignBand band = new JRDesignBand();
        band.setHeight(20);

        XmlDto dto = new XmlDto();
        Map<String, Object> fieldsMap = dto.getDetailsFields();
        Set<String> fieldsSet = fieldsMap.keySet();

        band.addElement(designLine(0, 0, 1040, 0));

        AtomicInteger x = new AtomicInteger();
        fieldsSet.forEach(f -> {
            JRDesignField field = new JRDesignField();
            field.setName(f);
            field.setValueClass(fieldsMap.get(f).getClass());
            try {
                design.addField(field);
            } catch (JRException e) {
                throw new RuntimeException(e);
            }

            JRDesignExpression expression = new JRDesignExpression();
            expression.setText("$F{" + f + "}");

            JRDesignTextField textField = new JRDesignTextField();
            textField.setExpression(expression);
            textField.setX(x.get());
            textField.setY(0);
            textField.setWidth(80);
            textField.setHeight(20);
            textField.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
            textField.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
            band.addElement(textField);

            band.addElement(designLine(x.get(), 0, 0, 20));

            x.getAndAdd(80);
            band.addElement(designLine(x.get(), 0, 0, 20));

        });
        band.addElement(designLine(0, 19, 1040, 0));

        return band;
    }

    private JRDesignBand columnFooterBand(JasperDesign design) {

        JRDesignBand band = new JRDesignBand();
        band.setHeight(20);

        JRDesignStaticText text = new JRDesignStaticText();
        text.setText("Total");
        text.setX(80);
        text.setY(0);
        text.setWidth(80);
        text.setHeight(20);
        text.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        text.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
        band.addElement(text);

        return band;

    }

    private JRDesignLine designLine(int x, int y, int width, int height) {
        JRDesignLine line = new JRDesignLine();
        line.setX(x);
        line.setY(y);
        line.setWidth(width);
        line.setHeight(height);
        return line;
    }

    private JRDesignStaticText staticText(int x, int y, int width, int height) {
        JRDesignStaticText text = new JRDesignStaticText();
        text.setX(x);
        text.setY(y);
        text.setHeight(height);
        text.setWidth(width);
        text.setBold(true);
        text.setFontSize(12f);
        text.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        text.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
        return text;
    }

}
