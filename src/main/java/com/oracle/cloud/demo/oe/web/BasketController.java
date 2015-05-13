package com.oracle.cloud.demo.oe.web;

import com.oracle.cloud.demo.oe.entities.Order;
import com.oracle.cloud.demo.oe.sessions.CheckoutSessionBean;
import com.oracle.cloud.demo.oe.web.util.BasketItem;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@SessionScoped
@Named("basket")
public class BasketController implements Serializable {

    private final List<BasketItem> products = new ArrayList<>();

    private BigDecimal total = BigDecimal.ZERO;

    @Inject
    @Named("ordersController")
    private OrdersController ordersController;

    @EJB
    CheckoutSessionBean checkoutBean;

    public BigDecimal getTotal() {
        return total;
    }

    public List<BasketItem> getProducts() {
        return products;
    }

    public void addMoreItems(List<BasketItem> basketItemsToAdd) {
        for (BasketItem p : basketItemsToAdd) {
            total = total.add(p.getSubtotal());

            // do not duplicate basketitems with same product. merge quantity
            boolean merged = false;
            for (BasketItem bi : products) {
                if (bi.getProduct().equals(p.getProduct())) {
                    bi.setQuantity(bi.getQuantity() + p.getQuantity());
                    merged = true;
                }
            }

            if (merged == false) {
                products.add(p);
            }
        }
    }

    public void clearBasket() {
        products.clear();
        total = BigDecimal.ZERO;
    }

    public void removeItem(BasketItem item) {
        products.remove(item);
        total = total.subtract(item.getSubtotal());
    }

    public String checkout() {
        if (products.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Product list is empty. You must first add products to the basket.", ""));
            return "/shop/Basket";
        }

        String user = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRemoteUser();

        Order order = checkoutBean.checkout(user, products);
        ordersController.setSelected(order);
        clearBasket();

        return "/shop/ViewLastPlacedOrder";
    }

}
