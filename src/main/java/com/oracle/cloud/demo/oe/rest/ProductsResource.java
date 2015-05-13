package com.oracle.cloud.demo.oe.rest;

import com.oracle.cloud.demo.oe.sessions.ProductInformationFacade;
import javax.ejb.EJB;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;

@Path("products")
@RequestScoped
public class ProductsResource {

    @EJB
    ProductInformationFacade prodDao;

    @GET
    @Path("all")
    @Produces("application/json")
    public ProductList all() {
        return new ProductList(prodDao.findAll());
    }

}
