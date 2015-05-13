package com.oracle.cloud.demo.oe.sessions;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.oracle.cloud.demo.oe.entities.Customer;
import com.oracle.cloud.demo.oe.entities.Order;
import com.oracle.cloud.demo.oe.entities.OrderItem;

@Stateless
public class OrdersFacade extends AbstractFacade<Order> {

    @PersistenceContext
    private EntityManager em;
    private String filterByCustomerEmail;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrdersFacade() {
        super(Order.class);
    }

    public void setFilterByCustomerEmail(String email) {
        this.filterByCustomerEmail = email;
    }

    @Override
    protected CriteriaQuery filterQuery(CriteriaQuery query, Root<Order> rt) {
        if (filterByCustomerEmail == null) {
            return query;
        }

        if (filterByCustomerEmail != null && filterByCustomerEmail.trim().isEmpty() == false) {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            query.where(
                    cb.like(
                            rt.<Customer>get("customer")
                            .<String>get("custEmail"),
                            "%" + filterByCustomerEmail + "%"));
        }
        return query;
    }

    @Override
    protected CriteriaQuery orderByQuery(CriteriaQuery query, Root<Order> rt) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        return query.orderBy(cb.desc(rt.get("orderDate")));
    }

    public List<Order> getOrdersByCustomerEmail(String customerEmail) {
        return em.createNamedQuery("Order.findByCustomerEmail")
                .setParameter("customerEmail", customerEmail)
                .getResultList();
    }

    public List<OrderItem> getItemsByCustomerEmailAndOrderId(String customerEmail, Integer orderId) {
        List<OrderItem> orderItems = (List<OrderItem>) em.createNamedQuery("OrderItem.findByOrderIdAndCustomerEmail")
                .setParameter("customerEmail", customerEmail)
                .setParameter("orderId", orderId)
                .getResultList();

        if (orderItems == null) {
            return Collections.emptyList();
        }

        return orderItems;
    }
}
