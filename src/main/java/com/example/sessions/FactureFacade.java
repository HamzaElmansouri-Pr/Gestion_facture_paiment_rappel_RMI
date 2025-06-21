package com.example.sessions;

import com.example.entities.Facture;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class FactureFacade {

    @PersistenceContext(unitName = "facture_dbPU")
    private EntityManager em;

    public void create(Facture facture) {
        em.persist(facture);
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
}