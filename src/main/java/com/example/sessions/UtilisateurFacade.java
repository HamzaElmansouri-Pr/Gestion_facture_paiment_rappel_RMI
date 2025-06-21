// src/main/java/com/example/sessions/UtilisateurFacade.java
package com.example.sessions;

import com.example.entities.Utilisateur;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

@Stateless
public class UtilisateurFacade {

    @PersistenceContext(unitName = "facture_dbPU")
    private EntityManager em;

    public Utilisateur findByUsernameAndPassword(String username, String password) {
        try {
            return em.createQuery("SELECT u FROM Utilisateur u WHERE u.username = :username AND u.password = :password", Utilisateur.class)
                     .setParameter("username", username)
                     .setParameter("password", password)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public boolean testConnection() {
        try {
            em.createNativeQuery("SELECT 1").getSingleResult();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void create(Utilisateur utilisateur) {
        em.persist(utilisateur);
    }
}