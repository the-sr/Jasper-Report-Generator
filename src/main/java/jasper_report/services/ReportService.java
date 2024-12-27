package jasper_report.services;

import jasper_report.dto.BillReportDto;
import jasper_report.dto.XmlDto;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.CalculationEnum;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.VerticalTextAlignEnum;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ReportService {

    @Scheduled(cron = "*/5 * * * * *")
    public void generateReport() throws JRException, ClassNotFoundException, SQLException {

        JasperDesign jasperDesign = reportDesign();

        BillReportDto dto = new BillReportDto();
        JRDesignQuery query = new JRDesignQuery();
        query.setText(dto.getQuery());
        jasperDesign.setQuery(query);

        // --------------------------------BANDS-------------------------------

        // JRDesignBand titleBand = titleBand(jasperDesign);
        // jasperDesign.setTitle(titleBand);

        JRDesignBand headerBand = headerBand(jasperDesign,dto);
        jasperDesign.setPageHeader(headerBand);

        JRDesignBand columnHeaderBand = columnHeaderBand(jasperDesign,dto);
        jasperDesign.setColumnHeader(columnHeaderBand);

        JRDesignBand detailBand = detailBand(jasperDesign,dto);
        ((JRDesignSection) jasperDesign.getDetailSection()).addBand(detailBand);

        JRDesignBand summaryBand = summaryBand(jasperDesign,dto);
        jasperDesign.setSummary(summaryBand);

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
        jasperDesign.setPageWidth(1200);
        jasperDesign.setColumnWidth(1150);
        jasperDesign.setLeftMargin(25);
        jasperDesign.setRightMargin(25);
        return jasperDesign;
    }

    private JRDesignBand titleBand(JasperDesign design) {
        JRDesignBand titleBand = new JRDesignBand();
        titleBand.setHeight(30);

        return titleBand;
    }

    private JRDesignBand headerBand(JasperDesign design,XmlDto dto) {

        JRDesignBand band = new JRDesignBand();
        band.setHeight(205);

        JRDesignStaticText title1 = staticText(0, 0, design.getPageWidth(), 20);
        title1.setText("TITLE");
        band.addElement(title1);

        JRDesignStaticText title2 = staticText(0, 20, design.getPageWidth(), 20);
        title2.setText("TITLE TITLE TITLE TITLE TITLE");
        band.addElement(title2);

        JRDesignStaticText title3 = staticText(0, 40, design.getPageWidth(), 20);
        title3.setText("TITLE TITLE TITLE TITLE TITLE TITLE TITLE TITLE TITLE TITLE");
        band.addElement(title3);

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
                text.setWidth(80);
                text.setHeight(20);
                text.setFontSize(12f);
                band.addElement(text);
                x.getAndAdd(80);
            }
            if (fieldCounter.get() == 0) {
                JRDesignExpression expression = new JRDesignExpression();
                expression.setText("$F{" + f + "}");

                JRDesignTextField textField = new JRDesignTextField();
                textField.setExpression(expression);
                textField.setX(x.get());
                textField.setY(y.get());
                textField.setHeight(20);
                textField.setWidth(design.getColumnWidth() / 2);
                textField.setFontSize(12f);
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
                textField.setWidth(design.getColumnWidth() / 2);
                textField.setFontSize(12f);
                textField.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
                band.addElement(textField);

                x.set(0);
                y.set(y.get() + 20);
                fieldCounter.set(0);
            }

        });

        band.addElement(designLine(0, y.get(), design.getColumnWidth(), 0, Color.BLACK));

        y.set(y.getAndAdd(10));

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
            textField.setFontSize(12f);
            band.addElement(textField);
            x.getAndAdd(80);
        });

        JRDesignStaticText text = staticText(0, 180, 1040, 20);
        text.setText("As per your order, we have sold these udernoted stocks");
        text.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
        band.addElement(text);

        band.addElement(designLine(0, 200, design.getColumnWidth(), 1, Color.BLACK));
        return band;
    }

    private JRDesignBand columnHeaderBand(JasperDesign design, XmlDto dto) {
        JRDesignBand band = new JRDesignBand();
        band.setHeight(20);

        Map<String, Object> fieldsMap = dto.getDetailsFields();
        Set<String> fieldsSet = fieldsMap.keySet();
        int width = design.getColumnWidth() / fieldsSet.size();
        AtomicInteger x = new AtomicInteger(0);
        fieldsSet.forEach(f -> {

            JRDesignStaticText text = new JRDesignStaticText();
            text.setText(f);
            text.setX(x.get());
            text.setY(0);
            text.setWidth(width - 1);
            text.setHeight(20);
            text.setFontSize(12f);
            text.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
            text.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
            text.setForecolor(Color.WHITE);
            text.setBackcolor(Color.BLACK);
            text.setMode(ModeEnum.OPAQUE);
            band.addElement(text);

            x.getAndAdd(width);

            band.addElement(designLine(x.get(), 0, 0, 20, Color.WHITE));
        });
        return band;
    }

    private JRDesignBand detailBand(JasperDesign design,XmlDto dto) {

        JRDesignBand band = new JRDesignBand();
        band.setHeight(20);

        Map<String, Object> fieldsMap = dto.getDetailsFields();
        Set<String> fieldsSet = fieldsMap.keySet();
        int width = design.getColumnWidth() / fieldsSet.size();

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
            textField.setWidth(width);
            textField.setHeight(20);
            textField.setFontSize(12f);
            textField.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
            textField.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
            band.addElement(textField);

            band.addElement(designLine(x.get(), 0, 0, 20, Color.black));
            band.addElement(designLine(x.get(), 19, width, 0, Color.black));

            x.getAndAdd(width);
            band.addElement(designLine(x.get(), 0, 0, 20, Color.black));

        });
        return band;
    }

    private JRDesignBand summaryBand(JasperDesign design,XmlDto dto) {

        JRDesignBand band = new JRDesignBand();
        band.setHeight(20);

        int width = design.getColumnWidth() / dto.getDetailsFields().keySet().size();

        JRDesignStaticText text = new JRDesignStaticText();
        text.setText("Total");
        text.setX(width);
        text.setY(0);
        text.setWidth(width - 1);
        text.setHeight(20);
        text.setFontSize(12f);
        text.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        text.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
        text.setForecolor(Color.WHITE);
        text.setBackcolor(Color.BLACK);
        text.setMode(ModeEnum.OPAQUE);
        band.addElement(text);

        Map<String, String> operationMap = dto.getExpression();
        Set<String> operation = operationMap.keySet();
        AtomicInteger x = new AtomicInteger(width * 2);
        operation.forEach(o -> {

            // variables
            JRDesignVariable variable = new JRDesignVariable();
            variable.setName("total" + o);
            variable.setValueClass(Double.class);
            variable.setCalculation(
                    operationMap.get(o).toUpperCase().equals("SUM") ? CalculationEnum.SUM : CalculationEnum.SYSTEM);

            JRDesignExpression expression = new JRDesignExpression();
            expression.setText("$F{" + o + "}");
            variable.setExpression(expression);
            try {
                design.addVariable(variable);
            } catch (JRException e) {
                e.printStackTrace();
            }

            JRDesignTextField textField = new JRDesignTextField();
            textField.setX(x.get());
            textField.setY(0);
            textField.setWidth(width - 1);
            textField.setHeight(20);
            textField.setFontSize(12f);
            textField.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
            textField.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
            textField.setForecolor(Color.WHITE);
            textField.setBackcolor(Color.BLACK);
            textField.setMode(ModeEnum.OPAQUE);

            JRDesignExpression exp = new JRDesignExpression();
            exp.setText("$V{total" + o + "}");
            textField.setExpression(exp);
            band.addElement(textField);
            x.getAndAdd(width);
        });

        return band;

    }

    private JRDesignLine designLine(int x, int y, int width, int height, Color color) {
        JRDesignLine line = new JRDesignLine();
        line.setX(x);
        line.setY(y);
        line.setWidth(width);
        line.setHeight(height);
        line.setForecolor(color);
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
