package ProiectLaborator.controller;

import ProiectLaborator.request.BookRequest;
import ProiectLaborator.response.BookResponse;
import ProiectLaborator.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody BookRequest request) {
        return ResponseEntity.ok(bookService.createBook(request));
    }

    @GetMapping
    public List<BookResponse> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        try {
            BookResponse book = bookService.getBookById(id);
            // If book is found, return a success response
            return ResponseEntity.ok(book);
        } catch (RuntimeException e) {
            // If book is not found, return a 404 Not Found response
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }
}