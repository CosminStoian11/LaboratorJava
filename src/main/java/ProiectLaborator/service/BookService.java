package ProiectLaborator.service;

import ProiectLaborator.entity.Book;
import ProiectLaborator.entity.Category;
import ProiectLaborator.mapper.BookMapper;
import ProiectLaborator.repository.BookRepository;
import ProiectLaborator.repository.CategoryRepository;
import ProiectLaborator.request.BookRequest;
import ProiectLaborator.response.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public BookResponse createBook(BookRequest bookRequest) {

      Set<Category> categories = bookRequest.getCategoryId().stream()
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Category not found with id: " + id)))
                .collect(Collectors.toSet());

        Book book = BookMapper.toEntity(bookRequest, categories);
        book = bookRepository.save(book);
        return BookMapper.toResponse(book);
    }

    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookMapper::toResponse)
                .collect(Collectors.toList());
    }

    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found for id: " + id));
        return BookMapper.toResponse(book);
    }


    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

}

