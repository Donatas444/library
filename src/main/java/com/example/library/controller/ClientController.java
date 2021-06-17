package com.example.library.controller;

import com.example.library.model.Client;
import com.example.library.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping("/client")
    public void addClient(@RequestBody Client client) {
        clientService.addClient(client);
    }

    @GetMapping("/client/{id}")
    public Client getClient(@PathVariable("id") Long id) {
        return clientService.getClientById(id);
    }
}
