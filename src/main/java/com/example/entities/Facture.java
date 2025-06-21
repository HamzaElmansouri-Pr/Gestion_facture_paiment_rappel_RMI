package com.example.entities;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "facture")
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="client_id", nullable = false)
    private Client client;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_facture", insertable = false, updatable = false)
    private Date dateFacture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, insertable = false)
    private Etat etat;

    @OneToMany(mappedBy = "facture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneFacture> lignes;

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public Date getDateFacture() { return dateFacture; }

    public Etat getEtat() { return etat; }

    public List<LigneFacture> getLignes() { return lignes; }
    public void setLignes(List<LigneFacture> lignes) { this.lignes = lignes; }
}