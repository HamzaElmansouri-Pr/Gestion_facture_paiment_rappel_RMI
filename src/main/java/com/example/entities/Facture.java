package com.example.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "facture")
public class Facture implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Client client;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_facture", nullable = false)
    private Date dateFacture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtatFacture etat = EtatFacture.NON_PAYEE;

    public enum EtatFacture {
        PAYEE("Payée"),
        NON_PAYEE("Non Payée");

        private final String label;

        EtatFacture(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public Date getDateFacture() { return dateFacture; }
    public void setDateFacture(Date dateFacture) { this.dateFacture = dateFacture; }

    public EtatFacture getEtat() { return etat; }
    public void setEtat(EtatFacture etat) { this.etat = etat; }
}
