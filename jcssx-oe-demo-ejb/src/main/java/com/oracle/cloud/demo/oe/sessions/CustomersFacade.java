/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.cloud.demo.oe.sessions;

import com.oracle.cloud.demo.oe.entities.Customer;
import javax.ejb.Local;

/**
 *
 * @author bruno
 */
@Local
public interface CustomersFacade extends AbstractFacade<Customer> {

    Customer getCustomerByEmail(String email);

    void setFilterByEmail(String email);

}