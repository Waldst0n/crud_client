package com.waldstonsantana.crud_cliente.controllers;

import com.waldstonsantana.crud_cliente.Dtos.ClientDto;
import com.waldstonsantana.crud_cliente.models.Client;
import com.waldstonsantana.crud_cliente.services.ClientService;
import com.waldstonsantana.crud_cliente.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    private ClientService service;


    @GetMapping
    public ResponseEntity<Page<ClientDto>> findAll(Pageable pageable) {

        Page<ClientDto> dto = service.findAll(pageable);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping
    @RequestMapping(value = "/{id}")
    public  ResponseEntity<ClientDto> findById(@PathVariable Long id) {
        ClientDto  dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<ClientDto> insert( @Valid @RequestBody ClientDto dto) {
        dto = service.insert(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();

        return  ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public  ResponseEntity<ClientDto> update (@PathVariable Long id,@Valid @RequestBody ClientDto dto) {



        dto = service.update(id, dto);

        return ResponseEntity.ok().body(dto);



    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();

    }

}
