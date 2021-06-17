package com.example.library.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.example.library.model.Book;
import com.example.library.model.Client;
import com.example.library.service.ClientService;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ClientController.class})
@ExtendWith(SpringExtension.class)
public class ClientControllerTest {
    @Autowired
    private ClientController clientController;

    @MockBean
    private ClientService clientService;

    @Test
    public void testGetClient() throws Exception {
        Client client = new Client();
        client.setId(123L);
        client.setName("Name");
        client.setBooks(new ArrayList<>());
        when(this.clientService.getClientById(any())).thenReturn(client);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/client/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.clientController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":123,\"name\":\"Name\",\"books\":[]}"));
    }
}

