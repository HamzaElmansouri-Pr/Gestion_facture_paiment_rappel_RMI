package com.example.sessions;

import com.example.entities.Article;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ArticleFacade {

    @PersistenceContext(unitName = "facture_dbPU")  
    private EntityManager em;

    public void create(Article article) {
        em.persist(article);
    }

    public void update(Article article) {
        em.merge(article);
    }

    public void delete(Article article) {
        em.remove(em.merge(article));
    }

    public Article find(int id) {
        return em.find(Article.class, id);
    }

    public List<Article> findAll() {
        return em.createQuery("SELECT a FROM Article a", Article.class).getResultList();
    }
}