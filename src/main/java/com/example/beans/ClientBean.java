package com.example.beans;

import com.example.entities.Client;
import com.example.entities.Utilisateur;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import com.example.sessions.ClientFacade;
import com.example.sessions.UtilisateurFacade;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ClientBean implements Serializable {

    private Client client = new Client();

    @Inject
    private ClientFacade clientDAO;

    @Inject
    private UtilisateurFacade utilisateurDAO;

    public List<Client> getClients() {
        return clientDAO.findAll();
    }

    public String save() {
        if (client.getId() == 0) {
            // Create user first
            Utilisateur user = new Utilisateur();
            user.setUsername(client.getNom());
            user.setPassword(client.getNom());
            user.setRole(Utilisateur.Role.USER);
            utilisateurDAO.create(user);
            // Set user to client
            client.setUtilisateur(user);
            clientDAO.create(client);
        } else {
            clientDAO.update(client);
        }
        client = new Client(); // Reset form
        return "client.xhtml?faces-redirect=true";
    }

    public void edit(Client a) {
        this.client = a;
    }

    public void delete(Client a) {
        clientDAO.delete(a);
    }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
}