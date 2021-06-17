package com.example.library.service;

import com.example.library.model.Client;
import com.example.library.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public void addClient(Client client) {
        clientRepository.save(client);
    }

    public Client getClientById(Long id) throws NullPointerException {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            return client.get();
        } else {
            throw new RuntimeException("Client not found" + id);
        }
    }
}
