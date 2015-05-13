/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.cloud.demo.oe.web;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author bruno
 */
@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController {

    public boolean isUserCustomer() {
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("Customers");
    }

    public boolean isUserAdmin() {
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("Admins");
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";
    }

    public void checkLoggedIn() {
        if (isUserCustomer() || isUserAdmin()) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
