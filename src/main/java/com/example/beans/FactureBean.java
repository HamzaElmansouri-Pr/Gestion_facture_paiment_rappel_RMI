package com.example.beans;

import com.example.entities.Facture;
import com.example.entities.Client;
import com.example.entities.Utilisateur;
import com.example.sessions.FactureFacade;
import com.example.sessions.ClientFacade;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class FactureBean implements Serializable {

    @Inject
    private FactureFacade factureFacade;

    @Inject
    private LoginBean loginBean;

    @Inject
    private ClientFacade clientFacade;

    public List<Facture> getUserFactures() {
        Utilisateur utilisateur = loginBean.getUtilisateur();
        if (utilisateur != null) {
            Client client = clientFacade.findByUtilisateur(utilisateur);
            if (client != null) {
                return factureFacade.findByClient(client);
            }
        }
        return null;
    }

    public Client getClient() {
        Utilisateur utilisateur = loginBean.getUtilisateur();
        if (utilisateur != null) {
            return clientFacade.findByUtilisateur(utilisateur);
        }
        return null;
    }
} 