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

    public long countFactures() {
        return em.createQuery("SELECT COUNT(f) FROM Facture f", Long.class).getSingleResult();
    }

    public List<Facture> getLast3Factures() {
        return em.createQuery("SELECT f FROM Facture f ORDER BY f.dateFacture DESC", Facture.class)
                 .setMaxResults(3)
                 .getResultList();
    }

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

    public void update(Facture facture) {
        em.merge(facture);
    }

    public List<Facture> findAll() {
        return em.createQuery("SELECT f FROM Facture f", Facture.class).getResultList();
    }
    
    public void create(Facture facture) {
        em.persist(facture);
    }
    public void delete(Facture facture) {
        em.remove(em.merge(facture));
    }
} 