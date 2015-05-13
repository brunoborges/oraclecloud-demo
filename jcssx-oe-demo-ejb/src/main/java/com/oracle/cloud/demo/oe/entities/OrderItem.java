package com.oracle.cloud.demo.oe.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "DEMO_ORDER_ITEMS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderItem.countAll", query = "SELECT COUNT(c) FROM OrderItem c"),
    @NamedQuery(name = "OrderItem.findAll", query = "SELECT o FROM OrderItem o"),
    @NamedQuery(name = "OrderItem.findByOrderId", query = "SELECT o FROM OrderItem o WHERE o.order.orderId = :orderId"),
    @NamedQuery(name = "OrderItem.findByOrderIdAndCustomerEmail", query = "SELECT o FROM OrderItem o WHERE o.order.orderId = :orderId and o.order.customer.custEmail = :custEmail"),
    @NamedQuery(name = "OrderItem.findByLineItemId", query = "SELECT o FROM OrderItem o WHERE o.lineItemId = :lineItemId"),
    @NamedQuery(name = "OrderItem.findByUnitPrice", query = "SELECT o FROM OrderItem o WHERE o.unitPrice = :unitPrice"),
    @NamedQuery(name = "OrderItem.findByQuantity", query = "SELECT o FROM OrderItem o WHERE o.quantity = :quantity")})
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "DEMO_ORDER_ITEMS_SEQ", sequenceName = "DEMO_ORDER_ITEMS_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEMO_ORDER_ITEMS_SEQ")
    @Basic(optional = false)
    @Column(name = "ORDER_ITEM_ID")
    private Long lineItemId;
    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;
    @Column(name = "QUANTITY")
    private Integer quantity;
    @JoinColumn(name = "ORDER_ID", referencedColumnName = "ORDER_ID")
    @ManyToOne(optional = false)
    private Order order;
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    @ManyToOne(optional = false)
    private ProductInformation productId;

    public OrderItem() {
    }

    public OrderItem(Long lineItemId, Order order, ProductInformation productInfo, Integer quantity,
            BigDecimal unitPrice) {

        this.lineItemId = lineItemId;
        this.order = order;
        this.productId = productInfo;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Long getLineItemId() {
        return lineItemId;
    }

    public void setLineItemId(Long lineItemId) {
        this.lineItemId = lineItemId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ProductInformation getProductId() {
        return productId;
    }

    public void setProductId(ProductInformation productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.lineItemId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrderItem other = (OrderItem) obj;
        return Objects.equals(this.lineItemId, other.lineItemId);
    }

    @Override
    public String toString() {
        return "com.oracle.samples.mavenproject2.OrderItems[ orderItemsId=" + lineItemId + " ]";
    }

}
