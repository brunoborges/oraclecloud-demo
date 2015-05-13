package com.oracle.cloud.demo.oe.rest;

import com.oracle.cloud.demo.oe.entities.ProductInformation;
import com.oracle.cloud.demo.oe.sessions.ProductInformationFacade;
import javax.ejb.EJB;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("products")
public class ProductsResource {

     @EJB
     ProductInformationFacade productInformationFacade;

     @GET
     @Path("all")
     @Produces("application/json")
     public ProductInformation[] all() {
         return productInformationFacade.findAll().toArray(new ProductInformation[0]);
     }
}
