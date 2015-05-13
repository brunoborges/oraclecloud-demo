package com.oracle.cloud.demo.oe.sessions;

import com.oracle.cloud.demo.oe.entities.Customer;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class CustomersBean extends AbstractBean<Customer> implements CustomersFacade {

    @PersistenceContext
    private EntityManager em;
    private String filterByEmail;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomersBean() {
        super(Customer.class);
    }

    @Override
    public void setFilterByEmail(String email) {
        this.filterByEmail = email;
    }

    private boolean hasFilter() {
        return filterByEmail != null && filterByEmail.trim().isEmpty() == false;
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        setFilterByEmail(email);
        List<Customer> result = findAll();
        setFilterByEmail(null);

        if (result.isEmpty()) {
            createCustomerAutomagically(email);
        }

        return (Customer) em.createNamedQuery("Customer.findByCustEmail")
                .setParameter("custEmail", "%" + email + "%").getSingleResult();
    }

    protected Query createFindAllQuery() {
        Query q;
        if (hasFilter()) {
            q = em.createNamedQuery("Customer.findByCustEmail");
            q.setParameter("custEmail", "%" + filterByEmail + "%");
        } else {
            q = em.createNamedQuery("Customer.findAll");
        }
        return q;
    }

    @Override
    public List<Customer> findAll() {
        return createFindAllQuery().getResultList();
    }

    @Override
    public List<Customer> findRange(int[] range) {
        Query q = createFindAllQuery();
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    @Override
    public int count() {
        Query q;
        if (hasFilter()) {
            q = getEntityManager().createNamedQuery("Customer.countFilteredByEmail");
            q.setParameter("custEmail", "%" + filterByEmail + "%");
        } else {
            q = getEntityManager().createNamedQuery("Customer.countAll");
        }
        return ((Number) q.getSingleResult()).intValue();
    }

    private void createCustomerAutomagically(String email) {
        Customer cust = new Customer();
        cust.setCreditLimit(new BigDecimal("1000"));
        cust.setCustEmail(email);
        cust.setCustFirstName(email.split("@")[0]);
        cust.setCustLastName(email.split("@")[0]);

        create(cust);
        getEntityManager().flush();
    }
}
