package com.oracle.cloud.demo.oe.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author bruno
 */
@Entity
@Table(name = "DEMO_CUSTOMERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customer.countAll", query = "SELECT COUNT(c) FROM Customer c"),
    @NamedQuery(name = "Customer.countFilteredByEmail", query = "SELECT COUNT(c) FROM Customer c WHERE c.custEmail LIKE :custEmail"),
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findByCustomerId", query = "SELECT c FROM Customer c WHERE c.customerId = :customerId"),
    @NamedQuery(name = "Customer.findByCustFirstName", query = "SELECT c FROM Customer c WHERE c.custFirstName = :custFirstName"),
    @NamedQuery(name = "Customer.findByCustLastName", query = "SELECT c FROM Customer c WHERE c.custLastName = :custLastName"),
    @NamedQuery(name = "Customer.findByCreditLimit", query = "SELECT c FROM Customer c WHERE c.creditLimit = :creditLimit"),
    @NamedQuery(name = "Customer.findByCustEmail", query = "SELECT c FROM Customer c WHERE c.custEmail LIKE :custEmail")})
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "DEMO_CUST_SEQ", sequenceName = "DEMO_CUST_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEMO_CUST_SEQ")
    @Basic(optional = false)
    @Column(name = "CUSTOMER_ID")
    private Integer customerId;
    @Basic(optional = false)
    @Column(name = "CUST_FIRST_NAME")
    private String custFirstName;
    @Basic(optional = false)
    @Column(name = "CUST_LAST_NAME")
    private String custLastName;
    @Column(name = "CREDIT_LIMIT")
    private BigDecimal creditLimit;
    @Column(name = "CUST_EMAIL")
    private String custEmail;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Collection<Order> orderCollection;

    public Customer() {
    }

    public Customer(Integer customerId) {
        this.customerId = customerId;
    }

    public Customer(Integer customerId, String custFirstName, String custLastName) {
        this.customerId = customerId;
        this.custFirstName = custFirstName;
        this.custLastName = custLastName;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustFirstName() {
        return custFirstName;
    }

    public void setCustFirstName(String custFirstName) {
        this.custFirstName = custFirstName;
    }

    public String getCustLastName() {
        return custLastName;
    }

    public void setCustLastName(String custLastName) {
        this.custLastName = custLastName;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    @XmlTransient
    public Collection<Order> getOrderCollection() {
        return orderCollection;
    }

    public void setOrderCollection(Collection<Order> orderCollection) {
        this.orderCollection = orderCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.oracle.samples.mavenproject2.Customers[ customerId=" + customerId + " ]";
    }

}
