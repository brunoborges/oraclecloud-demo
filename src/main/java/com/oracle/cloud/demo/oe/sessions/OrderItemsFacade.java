/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oracle.cloud.demo.oe.sessions;

import com.oracle.cloud.demo.oe.entities.OrderItem;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author bruno
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
