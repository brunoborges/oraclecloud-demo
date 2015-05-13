/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.cloud.demo.oe.sessions;

import com.oracle.cloud.demo.oe.entities.Order;
import com.oracle.cloud.demo.oe.entities.OrderItem;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author bruno
 */
@Local
public interface OrdersFacade extends AbstractFacade<Order> {

    List<OrderItem> getItemsByCustomerEmailAndOrderId(String customerEmail, Integer orderId);

    List<Order> getOrdersByCustomerEmail(String customerEmail);

    List<Order> getOrdersLikeCustomerEmail(String customerEmail);

    void setFilterByCustomerEmail(String email);

}
