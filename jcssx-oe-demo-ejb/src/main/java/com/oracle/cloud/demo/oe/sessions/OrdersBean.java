package com.oracle.cloud.demo.oe.sessions;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.oracle.cloud.demo.oe.entities.Order;
import com.oracle.cloud.demo.oe.entities.OrderItem;
import javax.persistence.Query;

@Stateless
public class OrdersBean extends AbstractBean<Order> implements OrdersFacade {

    @PersistenceContext
    private EntityManager em;
    private String filterByCustomerEmail;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrdersBean() {
        super(Order.class);
    }

    @Override
    public void setFilterByCustomerEmail(String email) {
        this.filterByCustomerEmail = email;
    }

    private boolean hasFilter() {
        return filterByCustomerEmail != null && filterByCustomerEmail.trim().isEmpty() == false;
    }

    @Override
    public List<Order> getOrdersByCustomerEmail(String customerEmail) {
        return em.createNamedQuery("Order.findByCustomerEmail")
                .setParameter("custEmail", customerEmail)
                .getResultList();
    }

    @Override
    public List<Order> getOrdersLikeCustomerEmail(String customerEmail) {
        return em.createNamedQuery("Order.findLikeCustomerEmail")
                .setParameter("custEmail", customerEmail)
                .getResultList();
    }

    @Override
    public List<OrderItem> getItemsByCustomerEmailAndOrderId(String customerEmail, Integer orderId) {
        List<OrderItem> orderItems = (List<OrderItem>) em.createNamedQuery("OrderItem.findByOrderIdAndCustomerEmail")
                .setParameter("custEmail", customerEmail)
                .setParameter("orderId", orderId)
                .getResultList();

        if (orderItems == null) {
            return Collections.emptyList();
        }

        return orderItems;
    }

    protected Query createFindAllQuery() {
        Query q;
        if (hasFilter()) {
            q = em.createNamedQuery("Order.findLikeCustomerEmail");
            q.setParameter("custEmail", "%" + filterByCustomerEmail + "%");
        } else {
            q = em.createNamedQuery("Order.findAll");
        }
        return q;
    }

    @Override
    public List<Order> findAll() {
        return createFindAllQuery().getResultList();
    }

    @Override
    public List<Order> findRange(int[] range) {
        Query q = createFindAllQuery();
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    @Override
    public int count() {
        Query q;
        if (hasFilter()) {
            q = getEntityManager().createNamedQuery("Order.countFilteredByCustomerEmail");
            q.setParameter("custEmail", "%" + filterByCustomerEmail + "%");
        } else {
            q = getEntityManager().createNamedQuery("Order.countAll");
        }
        return ((Number) q.getSingleResult()).intValue();
    }

}
