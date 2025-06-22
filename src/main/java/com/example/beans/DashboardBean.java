package com.example.beans;

import com.example.entities.Facture;
import com.example.sessions.ClientFacade;
import com.example.sessions.FactureFacade;
import com.example.sessions.ArticleFacade;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class DashboardBean implements Serializable {

    @EJB
    private ClientFacade clientFacade;

    @EJB
    private FactureFacade factureFacade;

    @EJB
    private ArticleFacade articleFacade;

    public int getClientCount() {
        try {
            return clientFacade.findAll().size();
        } catch (Exception e) {
            return 0;
        }
    }

    public int getArticleCount() {
        try {
            return articleFacade.findAll().size();
        } catch (Exception e) {
            return 0;
        }
    }

    public int getFactureCount() {
        try {
            return factureFacade.findAll().size();
        } catch (Exception e) {
            return 0;
        }
    }

    public List<Facture> getLastFactures() {
        try {
            List<Facture> allFactures = factureFacade.findAll();
            // Return last 5 factures
            return allFactures.size() > 5 ? allFactures.subList(allFactures.size() - 5, allFactures.size()) : allFactures;
        } catch (Exception e) {
            return null;
        }
    }
}