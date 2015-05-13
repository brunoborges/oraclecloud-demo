package com.oracle.cloud.demo.oe.web;

import com.oracle.cloud.demo.oe.dto.BasketItem;
import com.oracle.cloud.demo.oe.entities.Order;
import com.oracle.cloud.demo.oe.sessions.CheckoutFacade;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;

@SessionScoped
@ManagedBean(name = "basket")
public class BasketController implements Serializable {

    @EJB
    private CheckoutFacade checkoutBean;

    private final List<BasketItem> products = new ArrayList<>();

    private BigDecimal total = BigDecimal.ZERO;

    @ManagedProperty(value = "#{ordersController}")
    private OrdersController ordersController;

    public void setOrdersController(OrdersController ordersController) {
        this.ordersController = ordersController;
    }

    public OrdersController getOrdersController() {
        return ordersController;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public DataModel<BasketItem> getProducts() {
        return new ArrayDataModel<>(products.toArray(new BasketItem[0]));
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

    public void removeItem() {
        int itemIndex = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("itemIndex"));
        BasketItem item = products.get(itemIndex);
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
