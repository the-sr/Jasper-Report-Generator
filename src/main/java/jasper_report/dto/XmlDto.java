package jasper_report.dto;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import lombok.Getter;

@Getter
public class XmlDto {
    String query;
    Set<String> titles = new LinkedHashSet<>();
    Map<String, Object> leftSideHeaderFields = new LinkedHashMap<>();
    Map<String, Object> rightSideHeaderFields = new LinkedHashMap<>();
    Map<String, Object> secondaryHeadersFields = new LinkedHashMap<>();
    String secondaryHeaderText;
    Map<String, Object> detailsFields = new LinkedHashMap<>();
    Map<String, String> expression = new LinkedHashMap<>();
    Map<String, Object> leftSideSummaryFields = new LinkedHashMap<>();
    Map<String, Object> rightSideSummaryFields = new LinkedHashMap<>();
}