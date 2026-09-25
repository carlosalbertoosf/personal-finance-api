package com.carlosalbertoosf.personal_finance_api.services;

import com.carlosalbertoosf.personal_finance_api.data.dto.request.CategoryRequestDTO;
import com.carlosalbertoosf.personal_finance_api.data.dto.response.CategoryResponseDTO;
import com.carlosalbertoosf.personal_finance_api.exceptions.RequiredObjectIsNullException;
import com.carlosalbertoosf.personal_finance_api.model.Category;
import com.carlosalbertoosf.personal_finance_api.repository.CategoryRepository;
import com.carlosalbertoosf.personal_finance_api.unitetests.mapper.mocks.MockCategory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class CategoryServicesTest {

    MockCategory input;

    @InjectMocks
    private CategoryServices service;

    @Mock
    CategoryRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockCategory();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Category category = input.mockEntity(1);
        category.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(category));

        var result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertCategoryLinks(result, 1L);
        assertEquals(category.getId(), result.getId());
        assertEquals(category.getName(), result.getName());
    }

    @Test
    void create() {
        CategoryRequestDTO dto = input.mockRequestDTO(1);
        Category persisted = input.mockEntity(1);
        persisted.setId(1L);

        when(repository.save(any(Category.class))).thenReturn(persisted);

        var result = service.create(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertCategoryLinks(result, 1L);
        assertEquals(persisted.getId(), result.getId());
        assertEquals(persisted.getName(), result.getName());
    }

    @Test
    void testCreateWithNullCategory() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
        () -> {
            service.create(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update() {
        Category category = input.mockEntity(1);
        var requestDTO = input.mockRequestDTO(2);
        when(repository.findById(1L)).thenReturn(Optional.of(category));

        var result = service.update(1L, requestDTO);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertCategoryLinks(result, 1L);
        assertEquals(category.getId(), result.getId());
        assertEquals(requestDTO.getName(), result.getName());
        verify(repository).save(category);
    }

    @Test
    void testUpdateWithNullCategory() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    service.update(1L,null);
                });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {
        Category category = input.mockEntity(1);
        when(repository.findById(1L)).thenReturn(Optional.of(category));

        service.delete(1L);
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Category.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findAll() {
        List<Category> categories = input.mockEntityList();
        when(repository.findAll()).thenReturn(categories);

        var result = service.findAll();

        assertNotNull(result);
        assertEquals(14, result.size());

        var categoryOne = result.get(1);
        assertNotNull(categoryOne);
        assertNotNull(categoryOne.getId());
        assertNotNull(categoryOne.getLinks());

        assertCategoryLinks(categoryOne, 1L);
        assertEquals(categories.get(1).getId(), categoryOne.getId());
        assertEquals(categories.get(1).getName(), categoryOne.getName());
    }

    private void assertCategoryLinks(CategoryResponseDTO result, Long id) {
        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/financial/category/" + id)
                        && link.getType().equals("GET")
                ));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/financial/category")
                        && link.getType().equals("GET")
                ));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/financial/category")
                        && link.getType().equals("POST")
                ));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/financial/category/" + id)
                        && link.getType().equals("PUT")
                ));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/financial/category/" + id)
                        && link.getType().equals("DELETE")
                ));
    }
}
