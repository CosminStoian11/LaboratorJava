package ProiectLaborator.response;

import lombok.Data;

import java.util.Set;

@Data
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private Integer year;
    private Double price;
    private Set<String> categories;
}
