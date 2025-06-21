package com.example.beans;

import com.example.sessions.*;
import com.example.entities.Facture;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class DashboardBean implements Serializable {

    @EJB
    private ClientFacade clientFacade;
    @EJB
    private ArticleFacade articleFacade;
    @EJB
    private FactureFacade factureFacade;

    private long clientCount;
    private long articleCount;
    private long factureCount;
    private List<Facture> lastFactures;

    @PostConstruct
    public void init() {
        try {
            clientCount = clientFacade.countClients();
            articleCount = articleFacade.countArticles(); // Utilisation de la nouvelle méthode
            factureCount = factureFacade.countFactures();
lastFactures = factureFacade.getLast3Factures();            
            // Debug : afficher les valeurs dans les logs
            System.out.println("=== DASHBOARD DEBUG ===");
            System.out.println("Client count: " + clientCount);
            System.out.println("Article count: " + articleCount);
            System.out.println("Facture count: " + factureCount);
            System.out.println("Last factures size: " + (lastFactures != null ? lastFactures.size() : "null"));
        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation du dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Getters
    public long getClientCount() { return clientCount; }
    public long getArticleCount() { return articleCount; }
    public long getFactureCount() { return factureCount; }
    public List<Facture> getLastFactures() { return lastFactures; }
}