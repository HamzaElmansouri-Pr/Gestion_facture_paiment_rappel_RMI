package com.example.sessions;

import com.example.entities.Facture;
import com.example.entities.Client;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class FactureFacade {
    @PersistenceContext(unitName = "facture_dbPU")
    private EntityManager em;

    public List<Facture> findByClient(Client client) {
        return em.createQuery("SELECT f FROM Facture f WHERE f.client = :client", Facture.class)
                 .setParameter("client", client)
                 .getResultList();
    }

    public Facture find(int id) {
        return em.find(Facture.class, id);
    }

    public Facture findWithLignes(int id) {
        try {
            return em.createQuery("SELECT f FROM Facture f LEFT JOIN FETCH f.lignes WHERE f.id = :id", Facture.class)
                     .setParameter("id", id)
                     .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
} 