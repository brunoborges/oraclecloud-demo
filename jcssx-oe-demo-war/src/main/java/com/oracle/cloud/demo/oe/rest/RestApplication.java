package com.oracle.cloud.demo.oe.rest;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author bruno
 */
public class RestApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<>();
        s.add(ProductsResource.class);
        s.add(CustomersResource.class);
        s.add(OrdersResource.class);
        s.add(EJBProvider.class);
        return s;
    }

}
