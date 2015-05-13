package com.oracle.cloud.demo.oe.dto;

import com.oracle.cloud.demo.oe.entities.ProductInformation;
import com.oracle.cloud.demo.oe.entities.ProductInformation;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BasketItem implements Serializable {

    private ProductInformation product;
    private int quantity = 0;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.product);
        hash = 41 * hash + this.quantity;
        return hash;
    }

    public BigDecimal getSubtotal() {
        return new BigDecimal(quantity).multiply(product.getListPrice());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BasketItem other = (BasketItem) obj;
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (this.quantity != other.quantity) {
            return false;
        }
        return true;
    }

    public ProductInformation getProduct() {
        return product;
    }

    public void setProduct(ProductInformation product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
