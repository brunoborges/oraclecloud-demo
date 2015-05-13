/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.cloud.demo.oe.sessions;

import java.util.List;

/**
 *
 * @author bruno
 */
public interface AbstractFacade<T> {

    int count();

    void create(T entity);

    void edit(T entity);

    T find(Object id);

    List<T> findAll();

    List<T> findRange(int[] range);

    void remove(T entity);
    
}
