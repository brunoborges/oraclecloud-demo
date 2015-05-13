/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.cloud.demo.oe.rest;

import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Type;

/**
 *
 * @author bruno
 */
@Provider
public class EJBProvider implements InjectableProvider<EJB, Type> {

    @Override
    public ComponentScope getScope() {
        return ComponentScope.Singleton;
    }

    @Override
    public Injectable getInjectable(ComponentContext cc, EJB ejb, Type t) {
        if (!(t instanceof Class)) {
            return null;
        }

        try {
            Class c = (Class) t;
            Context ic = new InitialContext();

            final Object o = ic.lookup("java:/comp/env/" + c.getSimpleName());

            return new Injectable<Object>() {
                @Override
                public Object getValue() {
                    return o;
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
