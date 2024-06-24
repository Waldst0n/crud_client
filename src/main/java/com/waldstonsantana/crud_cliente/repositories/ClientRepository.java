package com.waldstonsantana.crud_cliente.repositories;

import com.waldstonsantana.crud_cliente.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Client, Long> {
}
