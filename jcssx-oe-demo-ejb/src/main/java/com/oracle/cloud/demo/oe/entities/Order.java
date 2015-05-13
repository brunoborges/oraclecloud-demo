package com.oracle.cloud.demo.oe.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "DEMO_ORDERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Order.countAll", query = "SELECT COUNT(c) FROM Order c"),
    @NamedQuery(name = "Order.countFilteredByCustomerEmail", query = "SELECT COUNT(c) FROM Order c WHERE c.customer.custEmail LIKE :custEmail"),
    @NamedQuery(name = "Order.findAll", query = "SELECT o FROM Order o ORDER BY o.orderDate DESC"),
    @NamedQuery(name = "Order.findByOrderId", query = "SELECT o FROM Order o WHERE o.orderId = :orderId"),
    @NamedQuery(name = "Order.findByOrderDate", query = "SELECT o FROM Order o WHERE o.orderDate = :orderDate"),
    @NamedQuery(name = "Order.findLikeCustomerEmail", query = "SELECT o FROM Order o WHERE o.customer.custEmail LIKE :custEmail ORDER BY o.orderDate DESC"),
    @NamedQuery(name = "Order.findByCustomerEmail", query = "SELECT o FROM Order o WHERE o.customer.custEmail = :custEmail ORDER BY o.orderDate DESC"),
    @NamedQuery(name = "Order.findByOrderTotal", query = "SELECT o FROM Order o WHERE o.orderTotal = :orderTotal")})
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "DEMO_ORD_SEQ", sequenceName = "DEMO_ORD_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEMO_ORD_SEQ")
    @Column(name = "ORDER_ID", nullable = false)
    private Long orderId;
    @Basic(optional = false)
    @Column(name = "ORDER_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar orderDate;
    @Column(name = "ORDER_TOTAL")
    private BigDecimal orderTotal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private Collection<OrderItem> orderItemsCollection;
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "CUSTOMER_ID")
    @ManyToOne(optional = false)
    private Customer customer;

    public Order() {
    }

    public Order(Long orderId) {
        this.orderId = orderId;
    }

    public Order(Long orderId, Calendar orderDate) {
        this.orderId = orderId;
        this.orderDate = orderDate;
    }

    public Order(Customer customer, Calendar orderDate, Long orderId,
            BigDecimal orderTotal) {
        this.customer = customer;
        this.orderDate = orderDate;
        this.orderId = orderId;
        this.orderTotal = orderTotal;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Timestamp getOrderDate() {
        return new Timestamp(orderDate.getTime().getTime());
    }

    public void setOrderDate(Timestamp orderDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(orderDate);
        this.orderDate = c;
    }

    public BigDecimal getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }

    @XmlTransient
    public Collection<OrderItem> getOrderItemsCollection() {
        return orderItemsCollection;
    }

    public void setOrderItemsCollection(Collection<OrderItem> orderItemsCollection) {
        this.orderItemsCollection = orderItemsCollection;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderId != null ? orderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Order)) {
            return false;
        }
        Order other = (Order) object;
        return !((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId)));
    }

    @Override
    public String toString() {
        return "com.oracle.samples.mavenproject2.Orders[ orderId=" + orderId + " ]";
    }

}
