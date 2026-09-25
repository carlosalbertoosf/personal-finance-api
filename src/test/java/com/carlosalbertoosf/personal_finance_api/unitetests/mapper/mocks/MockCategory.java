package com.carlosalbertoosf.personal_finance_api.unitetests.mapper.mocks;

import com.carlosalbertoosf.personal_finance_api.data.dto.request.CategoryRequestDTO;
import com.carlosalbertoosf.personal_finance_api.data.dto.response.CategoryResponseDTO;
import com.carlosalbertoosf.personal_finance_api.model.Category;

import java.util.ArrayList;
import java.util.List;

public class MockCategory {

    public Category mockEntity() {
        return mockEntity(0);
    }

    public CategoryRequestDTO mockRequestDTO() {
        return mockRequestDTO(0);
    }

    public CategoryResponseDTO mockResponseDTO() {
        return mockResponseDTO(0);
    }

    public List<Category> mockEntityList() {
        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            categories.add(mockEntity(i));
        }
        return categories;
    }

    public List<CategoryRequestDTO> mockRequestDTOList() {
        List<CategoryRequestDTO> categories = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            categories.add(mockRequestDTO(i));
        }
        return categories;
    }

    public List<CategoryResponseDTO> mockResponseDTOList() {
        List<CategoryResponseDTO> categories = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            categories.add(mockResponseDTO(i));
        }
        return categories;
    }

    public Category mockEntity(Integer number) {
        Category category = new Category();
        category.setId(number.longValue());
        category.setName("Category " + number);
        return category;
    }

    public CategoryRequestDTO mockRequestDTO(Integer number) {
        CategoryRequestDTO category = new CategoryRequestDTO();
        category.setName("Category " + number);
        return category;
    }

    public CategoryResponseDTO mockResponseDTO(Integer number) {
        CategoryResponseDTO category = new CategoryResponseDTO();
        category.setId(number.longValue());
        category.setName("Category " + number);
        return category;
    }
}
