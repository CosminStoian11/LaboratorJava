package ProiectLaborator.mapper;

import ProiectLaborator.entity.Book;
import ProiectLaborator.entity.Category;
import ProiectLaborator.request.BookRequest;
import ProiectLaborator.response.BookResponse;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public static BookResponse toResponse(Book book) {
        BookResponse response = new BookResponse();

        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        response.setYear(book.getYear());
        response.setPrice(book.getPrice());
        response.setCategories(book.getCategories().stream()
                .map(Category::getName)
                .collect(Collectors.toSet()));

        return response;
    }

    public static Book toEntity(BookRequest request, Set<Category> categories) {
        Book book = new Book();

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setYear(request.getYear());
        book.setPrice(request.getPrice());
        book.setCategories(categories);

        return book;
    }

    public static BookRequest toRequestFromEntity(Book book) {
        Set<Long> categoryIds = book.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet());

        return BookRequest.builder()
                .title(book.getTitle())
                .year(book.getYear())
                .price(book.getPrice())
                .author(book.getAuthor())
                .categoryId(categoryIds)
                .build();
    }

}