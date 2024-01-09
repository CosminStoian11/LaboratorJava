package ProiectLaborator.controller;

import ProiectLaborator.entity.Book;
import ProiectLaborator.mapper.BookMapper;
import ProiectLaborator.request.BookRequest;
import ProiectLaborator.response.BookResponse;
import ProiectLaborator.service.BookService;
import ProiectLaborator.utils.DataUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    private BookService bookService;

    @Test
    void createBookTest() throws Exception {
        Book book = DataUtils.getBookTest();
        BookRequest request = BookMapper.toRequestFromEntity(book);

        when(bookService.createBook(any(BookRequest.class))).thenReturn(BookMapper.toResponse(book));

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(book.getTitle()));
    }

    @Test
    void getAllBooksTest() throws Exception {
        Book book = DataUtils.getBookTest();
        List<BookResponse> bookResponses = List.of(BookMapper.toResponse(book));

        when(bookService.getAllBooks()).thenReturn(bookResponses);

        mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getBookByIdTest() throws Exception {
        Book book = DataUtils.getBookTest();
        BookRequest request = BookMapper.toRequestFromEntity(book);

        when(bookService.getBookById(1L)).thenReturn(BookMapper.toResponse(book));

        String responseString = mockMvc.perform(get("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        BookResponse bookResponse = objectMapper.readValue(responseString, BookResponse.class);

        mockMvc.perform(get("/books/" + bookResponse.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookResponse.getId()));
    }

    @Test
    void getBookByIdNotFoundTest() throws Exception {
        when(bookService.getBookById(999L)).thenThrow(new RuntimeException("Book not found"));

        mockMvc.perform(get("/books/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBookTest() throws Exception {
        // Mock the behavior of bookService to perform the delete operation (since it's void)
        Mockito.doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/books/1")) // Use DELETE request with the book ID to delete
                .andExpect(status().isOk()); // Assuming you return a 200 OK status on successful delete
    }

}