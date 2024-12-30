package jasper_report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jasper_report.model.Marks;

@Repository
public interface MarksRepo extends JpaRepository<Marks,Long>{
    
}
