package jasper_report.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jasper_report.model.Marks;
import jasper_report.model.Product;
import jasper_report.repository.MarksRepo;
import jasper_report.repository.ProductRepo;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final ProductRepo productRepo;
    private final MarksRepo marksRepo;

    @PostMapping("/save-product")
    public String saveProduct() {

        for (long i = 1; i <= 20; i++) {
            Product product = Product.builder()
                    .id(i)
                    .productName("Product " + i)
                    .quantity(10)
                    .rate(i * 50.0f)
                    .amount(i * 1000.0)
                    .discountRate(i * 1.0f)
                    .discountAmount((i * 1.0) * (i * 10))
                    .finalAmount((i * 1000.0) - (i * 1.0) * (i * 10))
                    .createdDate(new Date())
                    .build();

            productRepo.save(product);
        }
        return "Products saved";
    }
    @PostMapping("/save-marks")
    public String savemarks() {

        for (long i = 1; i <= 10; i++) {
            Marks marks = Marks.builder()
                    .id(i)
                    .subject("Subject "+i)
                    .term("Term "+i)
                    .fullMarks(100f)
                    .passMarks(50f)
                    .obtainedMarks(i*10f)
                    .build();

            marksRepo.save(marks);
        }
        return "Marks saved";
    }

}
