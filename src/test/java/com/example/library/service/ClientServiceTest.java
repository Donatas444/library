package com.example.library.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.library.model.Book;
import com.example.library.model.Client;
import com.example.library.repository.ClientRepository;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ClientService.class})
@ExtendWith(SpringExtension.class)
public class ClientServiceTest {
    @MockBean
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @Test
    public void testAddClient() {
        Client client = new Client();
        client.setId(123L);
        client.setName("Name");
        client.setBooks(new ArrayList<>());
        when(this.clientRepository.save(any())).thenReturn(client);
        this.clientService.addClient(new Client());
        verify(this.clientRepository).save(any());
    }

    @Test
    public void testGetClientById() {
        Client client = new Client();
        client.setId(123L);
        client.setName("Name");
        client.setBooks(new ArrayList<>());
        Optional<Client> ofResult = Optional.of(client);
        when(this.clientRepository.findById(any())).thenReturn(ofResult);
        assertSame(client, this.clientService.getClientById(123L));
        verify(this.clientRepository).findById(any());
    }

    @Test
    public void testGetClientById2() {
        when(this.clientRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> this.clientService.getClientById(123L));
        verify(this.clientRepository).findById(any());
    }
}

