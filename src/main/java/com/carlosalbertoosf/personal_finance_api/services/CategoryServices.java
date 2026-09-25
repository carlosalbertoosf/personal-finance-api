package com.carlosalbertoosf.personal_finance_api.services;

import com.carlosalbertoosf.personal_finance_api.controllers.CategoryController;
import com.carlosalbertoosf.personal_finance_api.data.dto.request.CategoryRequestDTO;
import com.carlosalbertoosf.personal_finance_api.data.dto.response.CategoryResponseDTO;
import com.carlosalbertoosf.personal_finance_api.exceptions.RequiredObjectIsNullException;
import com.carlosalbertoosf.personal_finance_api.exceptions.ResourceNotFoundException;
import com.carlosalbertoosf.personal_finance_api.model.Category;
import com.carlosalbertoosf.personal_finance_api.repository.CategoryRepository;
import static com.carlosalbertoosf.personal_finance_api.mapper.ObjectMapper.parseObject;
import static com.carlosalbertoosf.personal_finance_api.mapper.ObjectMapper.parseListObjects;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServices {

    @Autowired
    CategoryRepository categoryRepository;

    public List<CategoryResponseDTO> findAll() {
        var categories = parseListObjects(categoryRepository.findAll(), CategoryResponseDTO.class);
        categories.forEach(this::addHateoasLinks);
        return categories;
    }

    public CategoryResponseDTO findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));

        var dto = parseObject(category, CategoryResponseDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public CategoryResponseDTO create(CategoryRequestDTO dto) {

        if(dto == null) throw new RequiredObjectIsNullException();

        Category category = parseObject(dto, Category.class);

        Category categorySaved = categoryRepository.save(category);

        var responseDTO = parseObject(categorySaved, CategoryResponseDTO.class);
        addHateoasLinks(responseDTO);
        return responseDTO;
    }

    public CategoryResponseDTO update(Long id, CategoryRequestDTO dto) {

        if(dto == null) throw new RequiredObjectIsNullException();

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));

        category.setName(dto.getName());

        categoryRepository.save(category);

        var responseDTO = parseObject(category, CategoryResponseDTO.class);
        addHateoasLinks(responseDTO);
        return responseDTO;
    }

    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));

        categoryRepository.delete(category);
    }

    private void addHateoasLinks(CategoryResponseDTO dto) {
        dto.add(linkTo(methodOn(CategoryController.class)
                .findById(dto.getId()))
                .withSelfRel()
                .withType("GET"));

        dto.add(linkTo(methodOn(CategoryController.class)
                .findAll())
                .withRel("findAll")
                .withType("GET"));

        dto.add(linkTo(methodOn(CategoryController.class)
                .create(null))
                .withRel("create")
                .withType("POST"));

        dto.add(linkTo(methodOn(CategoryController.class)
                .update(dto.getId(), null))
                .withRel("update")
                .withType("PUT"));

        dto.add(linkTo(methodOn(CategoryController.class)
                .delete(dto.getId()))
                .withRel("delete")
                .withType("DELETE"));
    }
}
