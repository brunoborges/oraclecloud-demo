/**
 * Copyright © 2015, 2015, Oracle and/or its affiliates. All rights reserved.
 */
package com.oracle.cloud.demo.oe.sessions;

import com.oracle.cloud.demo.oe.entities.OrderItem;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Bruno Borges
 */
@Stateless
public class OrderItemsFacade extends AbstractFacade<OrderItem> {
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderItemsFacade() {
        super(OrderItem.class);
    }
    
}
