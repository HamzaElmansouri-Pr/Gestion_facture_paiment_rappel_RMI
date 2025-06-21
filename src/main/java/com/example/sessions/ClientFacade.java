package com.example.sessions;

import com.example.entities.Client;
import com.example.entities.Utilisateur;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ClientFacade {

    @PersistenceContext(unitName = "facture_dbPU")  
    private EntityManager em;

    public void create(Client client) {
        em.persist(client);
    }

    public void update(Client client) {
        em.merge(client);
    }

    public void delete(Client client) {
        em.remove(em.merge(client));
    }

    public Client find(int id) {
        return em.find(Client.class, id);
    }

    public List<Client> findAll() {
        return em.createQuery("SELECT c FROM Client c", Client.class).getResultList();
    }
       public long countClients() {
        return em.createQuery("SELECT COUNT(c) FROM Client c", Long.class).getSingleResult();
    }
    public Client findByUtilisateur(Utilisateur utilisateur) {
    try {
        return em.createQuery("SELECT c FROM Client c WHERE c.utilisateur = :utilisateur", Client.class)
                 .setParameter("utilisateur", utilisateur)
                 .getSingleResult();
    } catch (NoResultException e) {
        return null; // or handle differently
    }
}
    } 
