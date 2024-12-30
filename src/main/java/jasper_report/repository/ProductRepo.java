package jasper_report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jasper_report.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long>{
    
}
