package com.carlosalbertoosf.personal_finance_api.controllers;

import com.carlosalbertoosf.personal_finance_api.data.dto.request.TransactionRequestDTO;
import com.carlosalbertoosf.personal_finance_api.data.dto.response.TransactionResponseDTO;
import com.carlosalbertoosf.personal_finance_api.services.TransactionServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/financial/transaction")
@Tag(name="Transaction", description = "Provides endpoints for managing financial transactions, including income and expenses.")
public class TransactionController {

    @Autowired
    private TransactionServices transactionServices;

    @GetMapping(
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE }
    )
    public List<TransactionResponseDTO> findAll() {
        return transactionServices.findAll();
    }

    @GetMapping(value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE }
    )
    public TransactionResponseDTO findById(@PathVariable("id") Long id) {
        return transactionServices.findById(id);
    }

    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE }
    )
    public TransactionResponseDTO create(@RequestBody TransactionRequestDTO transaction) {
        return transactionServices.create(transaction);
    }

    @PutMapping(value = "/{id}",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE }
    )
    public TransactionResponseDTO update(@PathVariable("id") Long id, @RequestBody TransactionRequestDTO transaction) {
        return transactionServices.update(id, transaction);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        transactionServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
