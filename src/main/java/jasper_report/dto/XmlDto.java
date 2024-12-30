package jasper_report.dto;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import lombok.Getter;

@Getter
public class XmlDto {
    public String query;
    Set<String> titles = new LinkedHashSet<>();
    public String secondaryHeaderText;
    public Map<String, Object> leftSideHeaderFields = new LinkedHashMap<>();
    public Map<String, Object> rightSideHeaderFields = new LinkedHashMap<>();
    public Map<String, Object> secondaryHeadersFields = new LinkedHashMap<>();
    public Map<String, Object> detailsFields = new LinkedHashMap<>();
    public Map<String, Object> summaryFields = new LinkedHashMap<>();
    public Map<String, String> expression = new LinkedHashMap<>();
}