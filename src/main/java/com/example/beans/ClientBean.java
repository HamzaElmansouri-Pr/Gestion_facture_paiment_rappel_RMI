package com.example.beans;

import com.example.entities.Client;
import com.example.sessions.ClientFacade;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ClientBean implements Serializable {

    private Client client = new Client();

    @Inject
    private ClientFacade clientFacade;

    public List<Client> getClients() {
        return clientFacade.findAll();
    }

    public String save() {
        if (client.getId() == 0) {
            clientFacade.create(client);
        } else {
            clientFacade.update(client);
        }
        client = new Client(); // Reset
        return "clients.xhtml?faces-redirect=true";
    }

    public void edit(Client c) {
        this.client = c;
    }

    public void delete(Client c) {
        clientFacade.delete(c);
    }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
}