/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.sessions;

import com.example.entities.Client;
import com.example.entities.Facture;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author Yassine
 */
@Stateless
public class FactureFacade {
    @PersistenceContext(unitName = "facture_dbPU")
    private EntityManager em;

    public long countFactures() {
        return em.createQuery("SELECT COUNT(f) FROM Facture f", Long.class).getSingleResult();
    }

public List<Facture> getLast3Factures() {
    return em.createQuery("SELECT f FROM Facture f ORDER BY f.dateFacture DESC", Facture.class)
             .setMaxResults(3)
             .getResultList();
}

public void create(Facture facture) {
    em.persist(facture);
    em.flush();
    em.refresh(facture);  // rafraîchit pour récupérer la date générée par la base
}

    public void update(Facture facture) {
        em.merge(facture);
    }

    public void delete(Facture facture) {
        em.remove(em.merge(facture));
    }

    public Facture find(int id) {
        return em.find(Facture.class, id);
    }

    public List<Facture> findAll() {
        return em.createQuery("SELECT f FROM Facture f", Facture.class).getResultList();
    }
    public List<Facture> findByClient(Client client) {
        return em.createQuery("SELECT f FROM Facture f WHERE f.client = :client", Facture.class)
                 .setParameter("client", client)
                 .getResultList();
    }
}
