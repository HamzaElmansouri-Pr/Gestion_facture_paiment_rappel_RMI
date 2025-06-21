package com.example.beans;

import com.example.entities.Facture;
import com.example.sessions.FactureFacade;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class FactureDetailBean implements Serializable {
    private int factureId;
    private Facture facture;

    @EJB
    private FactureFacade factureFacade;

    public void init() {
        if (factureId != 0) {
            facture = factureFacade.findWithLignes(factureId);
        }
    }

    // Getters and setters
    public int getFactureId() {
        return factureId;
    }

    public void setFactureId(int factureId) {
        this.factureId = factureId;
    }

    public Facture getFacture() {
        return facture;
    }
} 