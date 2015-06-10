/**
 * Copyright © 2015, 2015, Oracle and/or its affiliates. All rights reserved.
 */
package com.oracle.cloud.demo.oe.web;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * 
 * @author Bruno Borges
 *
 */
@Named("myOrdersController")
@SessionScoped
public class MyOrdersController extends OrdersController {

	private static final long serialVersionUID = 1L;

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
