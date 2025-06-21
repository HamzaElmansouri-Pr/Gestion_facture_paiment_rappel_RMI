package com.example.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ligne_facture")
public class LigneFacture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="facture_id", nullable = false)
    private Facture facture;

    @ManyToOne
    @JoinColumn(name="article_id", nullable = false)
    private Article article;

    @Column(nullable = false)
    private int quantite;

    // Getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Facture getFacture() { return facture; }
    public void setFacture(Facture facture) { this.facture = facture; }

    public Article getArticle() { return article; }
    public void setArticle(Article article) { this.article = article; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public double getSousTotal() {
        return article.getPrix() * quantite;
    }
}