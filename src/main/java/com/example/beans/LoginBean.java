
// src/main/java/com/example/beans/LoginBean.java
package com.example.beans;

import com.example.entities.Utilisateur;
import com.example.sessions.UtilisateurFacade;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;
    private Utilisateur utilisateur;

    @EJB
    private UtilisateurFacade utilisateurFacade;

    public String login() {
        utilisateur = utilisateurFacade.findByUsernameAndPassword(username, password);
        if (utilisateur != null) {
            return "dashboard?faces-redirect=true";
        } else {
            return "login?faces-redirect=true";
        }
    }

    public String logout() {
        utilisateur = null;
        username = null;
        password = null;
        return "login?faces-redirect=true";
    }
    // Getters & skdjv

    // Getters & Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Utilisateur getUtilisateur() { return utilisateur; }
}
