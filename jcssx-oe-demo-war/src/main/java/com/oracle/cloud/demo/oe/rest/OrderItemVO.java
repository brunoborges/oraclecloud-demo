/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.cloud.demo.oe.rest;

import com.oracle.cloud.demo.oe.entities.OrderItem;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bruno
 */
@XmlRootElement
public class OrderItemVO {

    private Long orderItemId;
    private Integer quantity;
    private Integer productId;
    private BigDecimal unitPrice;

    public OrderItemVO() {
    }

    public OrderItemVO(OrderItem item) {
        this.unitPrice = item.getUnitPrice();
        this.productId = item.getProductId().getProductId();
        this.quantity = item.getQuantity();
        this.orderItemId = item.getLineItemId();
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

}
