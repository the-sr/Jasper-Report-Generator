package jasper_report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jasper_report.model.Data;

@Repository
public interface DataRepo extends JpaRepository<Data, Long> {

}