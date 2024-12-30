package jasper_report.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="marks")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Marks {

    @Id
    private Long id;
    
    private String subject;
    private String term;
    private Float fullMarks;
    private Float passMarks;
    private Float obtainedMarks;
}
