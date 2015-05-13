/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.cloud.demo.oe.sessions;

import com.oracle.cloud.demo.oe.dto.BasketItem;
import com.oracle.cloud.demo.oe.entities.Order;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author bruno
 */
@Local
public interface CheckoutFacade {

    Order checkout(String user, List<BasketItem> basketItems);
    
}
