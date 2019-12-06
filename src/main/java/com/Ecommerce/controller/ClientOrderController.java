package com.Ecommerce.controller;

import com.Ecommerce.exception.NotFoundException;
import com.Ecommerce.model.ClientOrder;
import com.Ecommerce.repository.ClientOrderRepository;
import com.Ecommerce.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientOrderController {
    @Autowired
    private ClientOrderRepository clientOrderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/client/{clientId}/orderClient")
    public List<ClientOrder> getOrderClientByClientId(@PathVariable Long clientId) {

        if(!clientRepository.existsById(clientId)) {
            throw new NotFoundException("Client not found!");
        }

        return clientOrderRepository.findByClientId(clientId);
    }

    @PostMapping("/client/{clientId}/orderClient")
    public ClientOrder addOrderClient(@PathVariable Long clientId,
                              @Valid @RequestBody ClientOrder clientOrder) {
        return clientRepository.findById(clientId)
                .map(client -> {
                    clientOrder.setClient(client);
                    return clientOrderRepository.save(clientOrder);
                }).orElseThrow(() -> new NotFoundException("Client not found!"));
    }

    @PutMapping("client/{clientId}/orderClient/{clientOrderId}")
    public ClientOrder updateOrderClient(@PathVariable Long clientId,
                                    @PathVariable Long clientOrderId,
                                    @Valid @RequestBody ClientOrder clientOrderUpdate) {

        if(!clientRepository.existsById(clientId)) {
            throw new NotFoundException("Client not found!");
        }

        return clientOrderRepository.findById(clientOrderId)
                .map(orderClient -> {
                    orderClient.setAmount(clientOrderUpdate.getAmount());
                    orderClient.setConfirmation_number(clientOrderUpdate.getConfirmation_number());
                    return clientOrderRepository.save(orderClient);
                }).orElseThrow(() -> new NotFoundException("Order client not found!"));
    }

    @DeleteMapping("client/{clientId}/orderClient/{clientOrderId}")
    public String deleteOrderClient(@PathVariable Long clientId,
                                   @PathVariable Long clientOrderId) {

        if(!clientRepository.existsById(clientId)) {
            throw new NotFoundException("Client not found!");
        }

        return clientOrderRepository.findById(clientOrderId)
                .map(orderClient -> {
                    clientOrderRepository.delete(orderClient);
                    return "Deleted Successfully!";
                }).orElseThrow(() -> new NotFoundException("Order client not found!"));
    }
}
