package com.Ecommerce.controller;

import com.Ecommerce.exception.NotFoundException;
import com.Ecommerce.model.Client;
import com.Ecommerce.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/client")
    public List<Client> getAllCustomers(){
        return clientRepository.findAll();
    }

    @GetMapping("/client/{id}")
    public Client getCategoryById(@PathVariable Long id) {
        Optional<Client> optCategory = clientRepository.findById(id);
        if (optCategory.isPresent()) {
            return optCategory.get();
        }else {
            throw new NotFoundException("Client not found with id "+id);
        }
    }

    @PostMapping("/client")
    public Client addCategory(@Valid @RequestBody Client client) {
        return clientRepository.save(client);
    }

    @PutMapping("/client/{id}")
    public Client updateCategory(@PathVariable Long id, @Valid @RequestBody Client clientUpdate) {
        return clientRepository.findById(id)
                .map(client -> {
                    client.setAddress(clientUpdate.getAddress());
                    client.setCc_number(clientUpdate.getCc_number());
                    client.setCity_region(clientUpdate.getCity_region());
                    client.setEmail(clientUpdate.getEmail());
                    client.setName(clientUpdate.getName());
                    client.setPhone(clientUpdate.getPhone());
                    return clientRepository.save(client);
                }).orElseThrow(() -> new NotFoundException("Client not found with id \"+id"));

    }

    @DeleteMapping("/client/{id}")
    public String deleteCategory(@PathVariable Long id) {
        return clientRepository.findById(id)
                .map(client -> {
                    clientRepository.delete(client);
                    return "Delete Successfully!";
                }).orElseThrow(() -> new NotFoundException("Client not found with id " + id));
    }
}
