package com.example.beans;

import com.example.entities.*;
import com.example.sessions.*;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class FactureBean implements Serializable {

    private Facture facture;
    private LigneFacture ligne = new LigneFacture();

    private Integer clientId;
    private Integer articleId;
    private int quantite;
    @Inject
    private LoginBean loginBean;
    @Inject
    private FactureFacade factureFacade;

    @Inject
    private ClientFacade clientFacade;

    @Inject
    private ArticleFacade articleFacade;

    private List<Client> clients;
    private List<Article> articles;

    @PostConstruct
    public void init() {
        clients = clientFacade.findAll();
        articles = articleFacade.findAll();
        facture = new Facture();
        facture.setLignes(new ArrayList<>());
    }

    public void ajouterLigne() {
        if(clientId == null || articleId == null || quantite <= 0) return;

        // Récupérer client seulement lors de la sauvegarde
        if(facture.getClient() == null) {
            Client client = clientFacade.find(clientId);
            facture.setClient(client);
        }

        Article article = articleFacade.find(articleId);
        LigneFacture nouvelleLigne = new LigneFacture();
        nouvelleLigne.setArticle(article);
        nouvelleLigne.setQuantite(quantite);
        nouvelleLigne.setFacture(facture);

        facture.getLignes().add(nouvelleLigne);

        // reset sélection article et quantité
        articleId = null;
        quantite = 0;
    }

    public double getMontantTotal() {
        return facture.getLignes().stream()
                .mapToDouble(LigneFacture::getSousTotal)
                .sum();
    }

    public String enregistrer() {
        factureFacade.create(facture);
        init(); // reset la facture
        clientId = null;
        return "liste_factures.xhtml?faces-redirect=true";
    }

    public List<Facture> getFactures() {
        return factureFacade.findAll();
    }
    public double calculerMontant(Facture facture) {
    if (facture.getLignes() == null) return 0;
    return facture.getLignes().stream()
                  .mapToDouble(LigneFacture::getSousTotal)
                  .sum();
    }
public void supprimerLigne(LigneFacture ligne) {
    facture.getLignes().remove(ligne);
}


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
    // Getters / Setters
    public Facture getFacture() { return facture; }
    public Integer getClientId() { return clientId; }
    public void setClientId(Integer clientId) { this.clientId = clientId; }
    public Integer getArticleId() { return articleId; }
    public void setArticleId(Integer articleId) { this.articleId = articleId; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public List<Client> getClients() { return clients; }
    public List<Article> getArticles() { return articles; }
    public Client getClient() {
        Utilisateur utilisateur = loginBean.getUtilisateur();
        if (utilisateur != null) {
            return clientFacade.findByUtilisateur(utilisateur);
        }
        return null;
    }
}