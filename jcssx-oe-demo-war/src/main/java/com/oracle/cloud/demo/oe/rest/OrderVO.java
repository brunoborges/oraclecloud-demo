/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.cloud.demo.oe.rest;

import com.oracle.cloud.demo.oe.entities.Order;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bruno
 */
@XmlRootElement
public class OrderVO {

    private Long orderId;
    private Date orderDate;
    private BigDecimal orderTotal;

    public OrderVO() {
    }

    public OrderVO(Order order) {
        this.orderId = order.getOrderId();
        // this.orderDate = order.getOrderDate();
        this.orderTotal = order.getOrderTotal();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }

}
