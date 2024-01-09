package ProiectLaborator.request;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class BookRequest {
    private String title;
    private String author;
    private Integer year;
    private Double price;
    private Set<Long> categoryId;

}
