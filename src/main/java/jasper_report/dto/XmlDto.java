package jasper_report.dto;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class XmlDto {
    public String query;
    public Map<String, Object> pageHeaderFieldsLeftSide = new LinkedHashMap<>();
    public Map<String, Object> pageHeaderFieldsRightSide = new LinkedHashMap<>();
    public Map<String, Object> secondaryHeadersFields = new LinkedHashMap<>();
    public Map<String, Object> detailsFields = new LinkedHashMap<>();
    public Map<String, Object> summaryFields = new LinkedHashMap<>();
    public Map<String, String> expression = new LinkedHashMap<>();
}