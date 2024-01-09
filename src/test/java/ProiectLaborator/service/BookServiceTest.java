package ProiectLaborator.service;

import ProiectLaborator.entity.Book;
import ProiectLaborator.entity.Category;
import ProiectLaborator.mapper.BookMapper;
import ProiectLaborator.repository.BookRepository;
import ProiectLaborator.repository.CategoryRepository;
import ProiectLaborator.request.BookRequest;
import ProiectLaborator.response.BookResponse;
import ProiectLaborator.utils.DataUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void createBookTest_NormalFlow() {
        Category category = DataUtils.getCategoryTest();
        Book book = DataUtils.getBookTest();
        BookRequest bookRequest = BookMapper.toRequestFromEntity(book);

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookResponse response = bookService.createBook(bookRequest);

        assertNotNull(response);
        assertEquals(book.getId(), response.getId());
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(categoryRepository, times(1)).findById(anyLong());
    }

    @Test
    void createBookTest_CategoryNotFound() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        Book book = DataUtils.getBookTest();
        BookRequest bookRequest = BookMapper.toRequestFromEntity(book);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            bookService.createBook(bookRequest);
        });

        assertEquals("Category not found with id: 1", exception.getMessage());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void getAllBookTest() {
        Book book = DataUtils.getBookTest();
        BookRequest bookRequest = BookMapper.toRequestFromEntity(book);

        when(bookRepository.findAll()).thenReturn(Collections.singletonList(book));

        List<BookResponse> responses = bookService.getAllBooks();

        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookByIdTest_NormalFlow() {
        Book book = DataUtils.getBookTest();
        BookRequest bookRequest = BookMapper.toRequestFromEntity(book);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        BookResponse response = bookService.getBookById(1L);

        assertNotNull(response);
        assertEquals(book.getId(), response.getId());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getBookByIdTest_NotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            bookService.getBookById(1L);
        });

        assertEquals("Book not found for id: 1", exception.getMessage());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void deleteBookTest() {
        doNothing().when(bookRepository).deleteById(anyLong());

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }
}

