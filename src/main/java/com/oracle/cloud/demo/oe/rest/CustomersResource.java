package com.oracle.cloud.demo.oe.rest;

import com.oracle.cloud.demo.oe.entities.Customer;
import com.oracle.cloud.demo.oe.sessions.CustomersFacade;
import javax.ejb.EJB;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.PathParam;

@Path("customers")
@RequestScoped
public class CustomersResource {

    @EJB
    CustomersFacade custDao;

    @GET
    @Path("byEmail/{customerEmail}")
    @Produces("application/json")
    public Customer byEmail(@PathParam("customerEmail") String email) {
        return custDao.getCustomerByEmail(email);
    }

}
