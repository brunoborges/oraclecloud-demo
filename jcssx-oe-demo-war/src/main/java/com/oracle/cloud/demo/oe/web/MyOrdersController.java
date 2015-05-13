package com.oracle.cloud.demo.oe.web;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "myOrdersController")
@SessionScoped
public class MyOrdersController extends OrdersController {

    @PostConstruct
    public void postConstruct() {
        String userEmail = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();

        // mock user if it's 'customer'
        if (userEmail.equals("customer")) {
            userEmail = "Graham.Spielberg@CHUKAR.EXAMPLE.COM";
        }

        setSearchByCustomerEmail(userEmail);
    }

    @Override
    public String prepareList() {
        return "/shop/MyOrders";
    }

    @Override
    public String next() {
        super.next();
        return "/shop/MyOrders";
    }

    @Override
    public String previous() {
        super.previous();
        return "/shop/MyOrders";
    }

    @Override
    public String destroy() {
        super.destroy();
        return "/shop/MyOrders";
    }

}
