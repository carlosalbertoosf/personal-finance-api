package com.carlosalbertoosf.personal_finance_api.controllers;

import com.carlosalbertoosf.personal_finance_api.controllers.docs.UserControllerDocs;
import com.carlosalbertoosf.personal_finance_api.data.dto.request.UserRequestDTO;
import com.carlosalbertoosf.personal_finance_api.data.dto.response.UserResponseDTO;
import com.carlosalbertoosf.personal_finance_api.services.UserServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/financial/user")
@Tag(name="User", description = "Provides endpoints for managing users.")
public class UserController implements UserControllerDocs {

    @Autowired
    private UserServices userServices;

    @GetMapping(
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public List<UserResponseDTO> findAll() {
        return userServices.findAll();
    }

    @GetMapping(value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public UserResponseDTO findById(@PathVariable("id") Long id) {
        return userServices.findById(id);
    }

    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public UserResponseDTO create(@RequestBody UserRequestDTO user) {
        return userServices.create(user);
    }

    @PutMapping(value = "/{id}",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public UserResponseDTO update(@PathVariable("id") Long id, @RequestBody UserRequestDTO user) {
        return userServices.update(id, user);
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        userServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
