package ProiectLaborator.service;

import ProiectLaborator.entity.Category;
import ProiectLaborator.mapper.CategoryMapper;
import ProiectLaborator.repository.CategoryRepository;
import ProiectLaborator.request.CategoryRequest;
import ProiectLaborator.response.CategoryResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void testCreateCategory() {
        // Arrange
        CategoryRequest request = new CategoryRequest();
        Category category = new Category();
        CategoryResponse expectedResponse = new CategoryResponse();

        when(categoryMapper.toEntry(request)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toResponse(category)).thenReturn(expectedResponse);

        // Act
        CategoryResponse actualResponse = categoryService.createCategory(request);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testGetAllCategories() {
        // Arrange
        List<Category> categories = Arrays.asList(new Category(), new Category());
        List<CategoryResponse> expectedResponses = Arrays.asList(new CategoryResponse(), new CategoryResponse());

        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryMapper.toResponse(categories)).thenReturn(expectedResponses);

        // Act
        List<CategoryResponse> actualResponses = categoryService.getAllCategories();

        // Assert
        assertNotNull(actualResponses);
        assertEquals(expectedResponses.size(), actualResponses.size());
        assertEquals(expectedResponses, actualResponses);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testGetCategoryById() {
        // Arrange
        Long categoryId = 1L;
        Category category = new Category();
        CategoryResponse expectedResponse = new CategoryResponse();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryMapper.toResponse(category)).thenReturn(expectedResponse);

        // Act
        CategoryResponse actualResponse = categoryService.getCategoryById(categoryId);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void testGetCategoryByIdNotFound() {
        // Arrange
        Long categoryId = 1L;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> categoryService.getCategoryById(categoryId));
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void testUpdateCategory() {
        // Arrange
        Long categoryId = 1L;
        CategoryRequest request = new CategoryRequest();
        Category category = new Category();
        CategoryResponse expectedResponse = new CategoryResponse();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toResponse(category)).thenReturn(expectedResponse);

        // Act
        CategoryResponse actualResponse = categoryService.updateCategory(categoryId, request);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testUpdateCategoryNotFound() {
        // Arrange
        Long categoryId = 1L;
        CategoryRequest request = new CategoryRequest();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> categoryService.updateCategory(categoryId, request));
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void testDeleteCategory() {
        // Arrange
        Long categoryId = 1L;

        // Act
        categoryService.deleteCategory(categoryId);

        // Assert
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }
}
