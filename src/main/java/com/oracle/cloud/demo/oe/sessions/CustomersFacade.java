package com.oracle.cloud.demo.oe.sessions;

import com.oracle.cloud.demo.oe.entities.Customer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Stateless
public class CustomersFacade extends AbstractFacade<Customer> {

    @PersistenceContext
    private EntityManager em;
    private String filterByEmail;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomersFacade() {
        super(Customer.class);
    }

    public void setFilterByEmail(String email) {
        this.filterByEmail = email;
    }

    @Override
    protected CriteriaQuery filterQuery(CriteriaQuery query, Root<Customer> rt) {
        if (filterByEmail == null) {
            return query;
        }

        if (filterByEmail != null && filterByEmail.trim().isEmpty() == false) {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            query.where(cb.like(rt.<String>get("custEmail"), "%" + filterByEmail + "%"));
        }
        return query;
    }

    public Customer getCustomerByEmail(String email) {
        return (Customer) em.createNamedQuery("Customer.findByCustEmail")
                .setParameter("custEmail", email).getSingleResult();
    }
}
