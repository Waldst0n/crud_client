package com.waldstonsantana.crud_cliente.services;

import com.waldstonsantana.crud_cliente.Dtos.ClientDto;
import com.waldstonsantana.crud_cliente.models.Client;
import com.waldstonsantana.crud_cliente.repositories.ClientRepository;
import com.waldstonsantana.crud_cliente.services.exceptions.DatabaseException;
import com.waldstonsantana.crud_cliente.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;


    @Transactional(readOnly = true)
    public ClientDto findById(Long id) {
        Client client = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado."));

        return new ClientDto(client);
    }

    @Transactional(readOnly = true)
    public Page<ClientDto> findAll(Pageable pageable) {
        Page<Client> clients = repository.findAll(pageable);
        return clients.map(x -> new ClientDto(x));
    }

    @Transactional
    public ClientDto insert(ClientDto dto) {
        Client entity = new Client();

        copyDtoToEntity(dto, entity);

        entity = repository.save(entity);

        return  new ClientDto(entity);
    }

    @Transactional
    public ClientDto update(Long id, ClientDto dto ) {
        try{
        Client entity = repository.getReferenceById(id);
        copyDtoToEntity(dto, entity);

        entity = repository.save(entity);
        return new ClientDto(entity);

        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }




    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void  delete(Long id) {
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }

        repository.deleteById(id);

    }


    private  void copyDtoToEntity(ClientDto dto, Client entity) {
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setBirthDate(dto.getBirthDate());
        entity.setIncome(dto.getIncome());
        entity.setChildren(dto.getChildren());
    }


}
