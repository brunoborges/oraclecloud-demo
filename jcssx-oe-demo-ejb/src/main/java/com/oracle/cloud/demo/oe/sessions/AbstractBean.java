package com.oracle.cloud.demo.oe.sessions;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public abstract class AbstractBean<T> implements AbstractFacade<T> {

    private final Class<T> entityClass;

    public AbstractBean(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    @Override
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    @Override
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    @Override
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    @Override
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    @Override
    public int count() {
        Query q = getEntityManager().createNamedQuery(entityClass.getSimpleName() + ".countAll");
        return ((Number) q.getSingleResult()).intValue();
    }

    @Override
    public List<T> findAll() {
        return getEntityManager().createNamedQuery(entityClass.getSimpleName() + ".findAll").getResultList();
    }

    @Override
    public List<T> findRange(int[] range) {
        Query q = getEntityManager().createNamedQuery(entityClass.getSimpleName() + ".findAll");
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

}
