package ProiectLaborator.controller;

import ProiectLaborator.request.CategoryRequest;
import ProiectLaborator.response.CategoryResponse;
import ProiectLaborator.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;


    @Test
    public void testCreateCategory() throws Exception {
        CategoryRequest request = new CategoryRequest();
        request.setName("Test Category");

        CategoryResponse response = new CategoryResponse();
        response.setId(1L);
        response.setName("Test Category");

        Mockito.when(categoryService.createCategory(Mockito.any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Category\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Category"));
    }

    @Test
    public void testGetAllCategories() throws Exception {
        CategoryResponse response = new CategoryResponse();
        response.setId(1L);
        response.setName("Test Category");

        Mockito.when(categoryService.getAllCategories()).thenReturn(Collections.singletonList(response));

        mockMvc.perform(MockMvcRequestBuilders.get("/categories"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Test Category"));
    }

    @Test
    public void testGetCategoryById() throws Exception {
        CategoryResponse response = new CategoryResponse();
        response.setId(1L);
        response.setName("Test Category");

        Mockito.when(categoryService.getCategoryById(1L)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/categories/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Category"));
    }

    @Test
    public void testUpdateCategory() throws Exception {
        CategoryRequest request = new CategoryRequest();
        request.setName("Updated Category");

        CategoryResponse response = new CategoryResponse();
        response.setId(1L);
        response.setName("Updated Category");

        Mockito.when(categoryService.updateCategory(Mockito.eq(1L), Mockito.any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.put("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Category\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Category"));
    }

    @Test
    public void testDeleteCategory() throws Exception {
        Mockito.doNothing().when(categoryService).deleteCategory(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/categories/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
