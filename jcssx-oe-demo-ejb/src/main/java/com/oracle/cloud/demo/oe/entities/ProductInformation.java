package com.oracle.cloud.demo.oe.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

@Entity
@Table(name = "DEMO_PRODUCT_INFO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductInformation.countAll", query = "SELECT COUNT(c) FROM ProductInformation c"),
    @NamedQuery(name = "ProductInformation.findAll", query = "SELECT p FROM ProductInformation p"),
    @NamedQuery(name = "ProductInformation.findByProductId", query = "SELECT p FROM ProductInformation p WHERE p.productId = :productId"),
    @NamedQuery(name = "ProductInformation.findByProductName", query = "SELECT p FROM ProductInformation p WHERE p.productName = :productName"),
    @NamedQuery(name = "ProductInformation.findByProductDescription", query = "SELECT p FROM ProductInformation p WHERE p.productDescription = :productDescription"),
    @NamedQuery(name = "ProductInformation.findByListPrice", query = "SELECT p FROM ProductInformation p WHERE p.listPrice = :listPrice")})
public class ProductInformation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "DEMO_PROD_SEQ", sequenceName = "DEMO_PROD_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEMO_PROD_SEQ")
    @Basic(optional = false)
    @Column(name = "PRODUCT_ID")
    private Integer productId;
    @Column(name = "PRODUCT_NAME")
    private String productName;
    @Column(name = "PRODUCT_DESCRIPTION")
    private String productDescription;
    @Column(name = "CATEGORY")
    private String category;
    @Column(name = "LIST_PRICE")
    private BigDecimal listPrice;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId", fetch = FetchType.LAZY)
    private Collection<OrderItem> orderItemCollection;

    public ProductInformation() {
    }

    public ProductInformation(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    @XmlTransient
    public Collection<OrderItem> getOrderItemCollection() {
        return orderItemCollection;
    }

    public void setOrderItemCollection(Collection<OrderItem> orderItemCollection) {
        this.orderItemCollection = orderItemCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductInformation)) {
            return false;
        }

        ProductInformation other = (ProductInformation) object;

        return this.productId.equals(other.productId);
    }

    @Override
    public String toString() {
        return "com.oracle.samples.mavenproject2.ProductInformation[ productId=" + productId + " ]";
    }

}
