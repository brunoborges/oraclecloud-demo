package com.oracle.cloud.demo.oe.sessions;

import com.oracle.cloud.demo.oe.entities.ProductInformation;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ProductInformationBean extends AbstractBean<ProductInformation> implements ProductInformationFacade {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductInformationBean() {
        super(ProductInformation.class);
    }

}
