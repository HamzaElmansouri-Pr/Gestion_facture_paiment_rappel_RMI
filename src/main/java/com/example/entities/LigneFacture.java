package com.example.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ligne_facture")
public class LigneFacture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "facture_id")
    private Facture facture;

    @ManyToOne
    @JoinColumn(name = "article_id") // this joins with article.id
    private Article article;

    private int quantite;
    private double prix;

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Facture getFacture() { return facture; }
    public void setFacture(Facture facture) { this.facture = facture; }

    public Article getArticle() { return article; }
    public void setArticle(Article article) { this.article = article; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
}
