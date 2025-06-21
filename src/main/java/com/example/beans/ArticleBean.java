package com.example.beans;

import com.example.entities.Article;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import com.example.sessions.ArticleFacade;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ArticleBean implements Serializable {

    private Article article = new Article();

    @Inject
    private ArticleFacade articleDAO;

    public List<Article> getArticles() {
        return articleDAO.findAll();
    }

    public String save() {
        if (article.getId() == 0) {
            articleDAO.create(article);
        } else {
            articleDAO.update(article);
        }
        article = new Article(); // Reset form
        return "article.xhtml?faces-redirect=true";
    }

    public void edit(Article a) {
        this.article = a;
    }

    public void delete(Article a) {
        articleDAO.delete(a);
    }

    public Article getArticle() { return article; }
    public void setArticle(Article article) { this.article = article; }
}
