package jasper_report.services;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.CalculationEnum;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.StretchTypeEnum;
import net.sf.jasperreports.engine.type.VerticalTextAlignEnum;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jasper_report.dto.MarksXmlDto;
import jasper_report.dto.ProductXmlDto;
import jasper_report.dto.XmlDto;

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

    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Scheduled(cron = "*/5 * * * * *")
    public void generateProductReport() throws JRException, ClassNotFoundException, SQLException {

        JasperDesign jasperDesign = new JasperDesign();
        jasperDesign.setName("Report");
        jasperDesign.setPageHeight(595);
        jasperDesign.setPageWidth(842);
        jasperDesign.setColumnWidth(802);
        jasperDesign.setLeftMargin(20);
        jasperDesign.setRightMargin(20);

        ProductXmlDto dto=new ProductXmlDto();

        // query
        JRDesignQuery query = new JRDesignQuery();
        query.setText(dto.getQuery());
        jasperDesign.setQuery(query);

        // fields
        createFields(jasperDesign, dto.getLeftSideHeaderFields());
        createFields(jasperDesign, dto.getRightSideHeaderFields());
        createFields(jasperDesign, dto.getSecondaryHeadersFields());
        createFields(jasperDesign, dto.getDetailsFields());

        // variables
        createVariables(jasperDesign, dto.getExpression());

        // --------------------------------BANDS-------------------------------

        JRDesignBand titleBand = titleBand(50, jasperDesign, dto);
        jasperDesign.setTitle(titleBand);

        JRDesignBand headerBand = headerBand(50, jasperDesign, dto);
        jasperDesign.setPageHeader(headerBand);

        JRDesignBand columnHeaderBand = columnHeaderBand(20, jasperDesign, dto);
        jasperDesign.setColumnHeader(columnHeaderBand);

        JRDesignBand detailBand = detailBand(20, jasperDesign, dto);
        ((JRDesignSection) jasperDesign.getDetailSection()).addBand(detailBand);

        JRDesignBand summaryBand = summaryBand(20, jasperDesign, dto);
        jasperDesign.setSummary(summaryBand);

        // --------------------------REPORT_GENERATION----------------------------

        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        Connection con;
        Class.forName(driver);
        con = DriverManager.getConnection(url + "?user=" + username + "&password=" + password);
        Map<String, Object> parameters = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, con);
        JasperExportManager.exportReportToPdfFile(jasperPrint, "Product Report.pdf");
        System.out.println("Product Report generated successfully!");
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void generateMarksReport() throws JRException, ClassNotFoundException, SQLException {

        JasperDesign jasperDesign = new JasperDesign();
        jasperDesign.setName("Report");
        jasperDesign.setPageHeight(595);
        jasperDesign.setPageWidth(842);
        jasperDesign.setColumnWidth(802);
        jasperDesign.setLeftMargin(20);
        jasperDesign.setRightMargin(20);

        MarksXmlDto dto=new MarksXmlDto();

        // query
        JRDesignQuery query = new JRDesignQuery();
        query.setText(dto.getQuery());
        jasperDesign.setQuery(query);

        // fields
        createFields(jasperDesign, dto.getLeftSideHeaderFields());
        createFields(jasperDesign, dto.getRightSideHeaderFields());
        createFields(jasperDesign, dto.getSecondaryHeadersFields());
        createFields(jasperDesign, dto.getDetailsFields());

        // variables
        createVariables(jasperDesign, dto.getExpression());

        // --------------------------------BANDS-------------------------------

        JRDesignBand titleBand = titleBand(50, jasperDesign, dto);
        jasperDesign.setTitle(titleBand);

        JRDesignBand headerBand = headerBand(50, jasperDesign, dto);
        jasperDesign.setPageHeader(headerBand);

        JRDesignBand columnHeaderBand = columnHeaderBand(20, jasperDesign, dto);
        jasperDesign.setColumnHeader(columnHeaderBand);

        JRDesignBand detailBand = detailBand(20, jasperDesign, dto);
        ((JRDesignSection) jasperDesign.getDetailSection()).addBand(detailBand);

        JRDesignBand summaryBand = summaryBand(150, jasperDesign, dto);
        jasperDesign.setSummary(summaryBand);

        // --------------------------REPORT_GENERATION----------------------------

        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        Connection con;
        Class.forName(driver);
        con = DriverManager.getConnection(url + "?user=" + username + "&password=" + password);
        Map<String, Object> parameters = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, con);
        JasperExportManager.exportReportToPdfFile(jasperPrint, "Marks Report.pdf");
        System.out.println("Marks Report generated successfully!");
    }
    
    private JRDesignBand titleBand(int bandHeight, JasperDesign design, XmlDto dto) {
        JRDesignBand band = new JRDesignBand();
        band.setHeight(bandHeight);

        // JRDesignImage logo=new JRDesignImage(design);
        // logo.setExpression(new JRDesignExpression("$F{logo}"));
        // logo.setX(0);
        // logo.setY(0);
        // logo.setWidth(60);
        // logo.setHeight(bandHeight);
        // band.addElement(logo);

        AtomicInteger y = new AtomicInteger();
        if (dto.getTitles() != null && !dto.getTitles().isEmpty()) {
            dto.getTitles().forEach(title -> {
                JRDesignStaticText text = staticText(0, y.get(), design.getColumnWidth(), 25);
                text.setText(title);
                text.setFontSize(16f);
                text.setBold(true);
                band.addElement(text);
                y.getAndAdd(20);
            });
        }

        return band;
    }

    private JRDesignBand headerBand(int bandHeight, JasperDesign design, XmlDto dto) {

        JRDesignBand band = new JRDesignBand();
        band.setPrintWhenExpression(new JRDesignExpression("Boolean.valueOf($V{PAGE_NUMBER}==1)"));
        band.setHeight(bandHeight);

        Map<String, Object> fieldsMap;
        int y1 = 0, y2 = 0;

        fieldsMap = dto.getLeftSideHeaderFields();
        if (fieldsMap != null && !fieldsMap.isEmpty()) {
            Set<String> fieldsSet = fieldsMap.keySet();
            for (String f : fieldsSet) {
                JRDesignExpression expression = new JRDesignExpression();
                expression.setText("\"" + f + ": \" + $F{" + f + "}");

                JRDesignTextField textField = new JRDesignTextField();
                textField.setExpression(expression);
                textField.setX(0);
                textField.setY(y1);
                textField.setHeight(20);
                textField.setWidth(design.getColumnWidth() / 2);
                textField.setFontSize(12f);
                textField.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
                band.addElement(textField);
                y1 += 20;
            }
        }

        fieldsMap = dto.getRightSideHeaderFields();
        if (fieldsMap != null && !fieldsMap.isEmpty()) {
            Set<String> fieldsSet = fieldsMap.keySet();
            for (String f : fieldsSet) {
                JRDesignExpression expression = new JRDesignExpression();
                expression.setText("\"" + f + ": \" + $F{" + f + "}");

                JRDesignTextField textField = new JRDesignTextField();
                textField.setExpression(expression);
                textField.setX(design.getColumnWidth() / 2);
                textField.setY(y2);
                textField.setHeight(20);
                textField.setWidth(design.getColumnWidth() / 2);
                textField.setFontSize(12f);
                textField.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
                band.addElement(textField);
                y2 += 20;
            }
        }
        int y = y1 > y2 ? y1 : y2;

        band.addElement(designLine(0, y, design.getColumnWidth(), 1, Color.BLACK));
        y += 5;

        fieldsMap = dto.getSecondaryHeadersFields();
        if (fieldsMap != null && !fieldsMap.isEmpty()) {
            int x = 0;
            Set<String> fieldsSet = fieldsMap.keySet();
            for (String f : fieldsSet) {
                JRDesignExpression expression = new JRDesignExpression();
                expression.setText("\"" + f + ": \" + $F{" + f + "}");

                JRDesignTextField textField = new JRDesignTextField();
                textField.setExpression(expression);
                textField.setX(x);
                textField.setY(y);
                textField.setHeight(20);
                textField.setWidth(design.getColumnWidth() / fieldsSet.size());
                textField.setFontSize(12f);
                textField.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
                band.addElement(textField);
                x += (design.getColumnWidth() / fieldsSet.size());
            }
            y += 20;
        }

        if (dto.getSecondaryHeaderText() != null && !dto.getSecondaryHeaderText().isEmpty()) {
            JRDesignStaticText text = staticText(0, y, design.getColumnWidth(), 20);
            text.setText(dto.getSecondaryHeaderText());
            text.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
            text.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
            y += 20;
            band.addElement(text);
        }

        band.addElement(designLine(0, y, design.getColumnWidth(), 1, Color.BLACK));
        y += 5;

        return band;
    }

    private JRDesignBand columnHeaderBand(int bandHeight, JasperDesign design, XmlDto dto) {
        JRDesignBand band = new JRDesignBand();
        band.setPrintWhenExpression(new JRDesignExpression("Boolean.valueOf($V{PAGE_NUMBER}==1)"));
        band.setHeight(bandHeight);

        Map<String, Object> fieldsMap = dto.getDetailsFields();
        if (fieldsMap != null && !fieldsMap.isEmpty()) {
            Set<String> fieldsSet = fieldsMap.keySet();
            int width = design.getColumnWidth() / fieldsSet.size();
            AtomicInteger x = new AtomicInteger(0);
            fieldsSet.forEach(f -> {
                JRDesignStaticText text = new JRDesignStaticText();
                text.setText(f);
                text.setX(x.get());
                text.setY(0);
                text.setWidth(width - 1);
                text.setHeight(bandHeight);
                text.setFontSize(12f);
                text.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
                text.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
                text.setStretchType(StretchTypeEnum.CONTAINER_BOTTOM);
                text.setForecolor(Color.WHITE);
                text.setBackcolor(Color.BLACK);
                text.setMode(ModeEnum.OPAQUE);
                band.addElement(text);
                x.getAndAdd(width);
                band.addElement(designLine(x.get(), 0, 0, 20, Color.WHITE));
            });
        }
        return band;
    }

    private JRDesignBand detailBand(int bandHeight, JasperDesign design, XmlDto dto) {

        JRDesignBand band = new JRDesignBand();
        band.setHeight(bandHeight);

        Map<String, Object> fieldsMap = dto.getDetailsFields();
        if (fieldsMap != null && !fieldsMap.isEmpty()) {
            Set<String> fieldsSet = fieldsMap.keySet();
            int width = design.getColumnWidth() / fieldsSet.size();

            AtomicInteger x = new AtomicInteger();
            fieldsSet.forEach(f -> {
                JRDesignExpression expression = new JRDesignExpression();
                expression.setText("$F{" + f + "}");

                JRDesignTextField textField = new JRDesignTextField();
                textField.setExpression(expression);
                textField.setX(x.get());
                textField.setY(0);
                textField.setWidth(width);
                textField.setHeight(bandHeight);
                textField.setFontSize(12f);
                textField.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
                textField.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
                textField.setStretchType(StretchTypeEnum.CONTAINER_BOTTOM);
                band.addElement(textField);

                band.addElement(designLine(x.get(), 0, 0, bandHeight, Color.black));
                band.addElement(designLine(x.get(), bandHeight - 1, width, 0, Color.black));

                x.getAndAdd(width);
                band.addElement(designLine(x.get(), 0, 0, bandHeight, Color.black));

            });
        }
        return band;
    }

    private JRDesignBand summaryBand(int bandHeight, JasperDesign design, XmlDto dto) {

        JRDesignBand band = new JRDesignBand();
        band.setHeight(bandHeight);

        int width = design.getColumnWidth() / dto.getDetailsFields().size();

        Set<String> detailsFields=dto.getDetailsFields().keySet();
        Map<String, String> operationMap = dto.getExpression();
        AtomicInteger x = new AtomicInteger(0);


        detailsFields.forEach(field-> {
            JRDesignElement element;
            if (operationMap != null && operationMap.containsKey(field)) {

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
                exp.setText("$V{Total " + field + "}");
                textField.setExpression(exp);
        
                element=textField;
            }else{
                JRDesignRectangle rectangle=new JRDesignRectangle();
                rectangle.setX(x.get());
                rectangle.setY(0);
                rectangle.setWidth(width-1);
                rectangle.setHeight(20);
                rectangle.setBackcolor(Color.BLACK);
                element=rectangle;            
            }
            band.addElement(element);
            x.getAndAdd(width);
        });

        JRDesignStaticText text = new JRDesignStaticText();
        text.setText("Total");
        text.setX(0);
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

        Map<String, Object> leftSideSummaryFields = dto.getLeftSideSummaryFields();
        if (leftSideSummaryFields != null && !leftSideSummaryFields.isEmpty()) {
            Set<String> fieldsSet = leftSideSummaryFields.keySet();
            AtomicInteger y = new AtomicInteger(50);
            fieldsSet.forEach(f -> {
                JRDesignExpression expression = new JRDesignExpression();
                expression.setText("\"" + f + ": \" + $V{" + f + "}");

                JRDesignTextField textField = new JRDesignTextField();
                textField.setExpression(expression);
                textField.setX(0);
                textField.setY(y.get());
                textField.setHeight(20);
                textField.setWidth(design.getColumnWidth() / 2);
                textField.setFontSize(12f);
                textField.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
                band.addElement(textField);
                y.getAndAdd(20);
            });
        }

        Map<String, Object> rightSideSummaryFields = dto.getRightSideSummaryFields();
        if (rightSideSummaryFields != null && !rightSideSummaryFields.isEmpty()) {
            Set<String> fieldsSet = rightSideSummaryFields.keySet();
            AtomicInteger y = new AtomicInteger(50);
            fieldsSet.forEach(f -> {
                JRDesignExpression expression = new JRDesignExpression();
                expression.setText("\"" + f + ": \" + $F{" + f + "}");

                JRDesignTextField textField = new JRDesignTextField();
                textField.setExpression(expression);
                textField.setX(0);
                textField.setY(y.get());
                textField.setHeight(20);
                textField.setWidth(design.getColumnWidth() / 2);
                textField.setFontSize(12f);
                textField.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
                band.addElement(textField);
                y.getAndAdd(20);
            });
        }

        return band;

    }

    private void createFields(JasperDesign design, Map<String, Object> fieldsMap) {
        if (fieldsMap != null && !fieldsMap.isEmpty()) {
            Set<String> fieldSet = fieldsMap.keySet();
            fieldSet.forEach(f -> {
                JRDesignField field = new JRDesignField();
                field.setName(f);
                field.setValueClass(fieldsMap.get(f).getClass());
                try {
                    design.addField(field);
                } catch (JRException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private void createVariables(JasperDesign design, Map<String, String> variablesMap) {
        if (variablesMap != null && !variablesMap.isEmpty()) {
            Set<String> variablesSet = variablesMap.keySet();
            variablesSet.forEach(v -> {

                JRDesignVariable variable = new JRDesignVariable();
                variable.setName("Total " + v);
                variable.setValueClass(Double.class);
                variable.setCalculation(
                        variablesMap.get(v).toUpperCase().equals("SUM") ? CalculationEnum.SUM : CalculationEnum.SYSTEM);

                JRDesignExpression expression = new JRDesignExpression();
                expression.setText("$F{" + v + "}");
                variable.setExpression(expression);
                try {
                    design.addVariable(variable);
                } catch (JRException e) {
                    e.printStackTrace();
                }
            });
        }
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