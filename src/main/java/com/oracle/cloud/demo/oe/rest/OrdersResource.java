package com.oracle.cloud.demo.oe.rest;

import com.oracle.cloud.demo.oe.entities.Order;
import com.oracle.cloud.demo.oe.entities.OrderItem;
import com.oracle.cloud.demo.oe.sessions.CheckoutSessionBean;
import com.oracle.cloud.demo.oe.sessions.OrdersFacade;
import com.oracle.cloud.demo.oe.web.util.BasketItem;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;

@Path("orders")
@RequestScoped
public class OrdersResource {

    @EJB
    OrdersFacade orderDao;

    @EJB
    CheckoutSessionBean checkoutBean;

    @GET
    @Path("fromCustomer/{customerEmail}")
    @Produces("application/json")
    public OrderList byCustomer(@PathParam("customerEmail") String customerEmail) {
        List<Order> list = orderDao.getOrdersByCustomerEmail(customerEmail);
        if (list == null) {
            list = Collections.emptyList();
        }

        for (Order o : list) {
            o.setCustomer(null);
        }

        return new OrderList(list);
    }

    @GET
    @Path("fromCustomer/{customerEmail}/{orderId}")
    @Produces("application/json")
    public OrderItemList items(@PathParam("orderId") Integer orderId, @PathParam("customerEmail") String customerEmail) {
        List<OrderItem> list = orderDao.getItemsByCustomerEmailAndOrderId(customerEmail, orderId);
        if (list == null) {
            list = Collections.emptyList();
        }
        for (OrderItem oi : list) {
            oi.setOrder(null);
        }
        return new OrderItemList(list);
    }

    @PUT
    @Path("newOrder/{customerEmail}")
    @Consumes("application/json")
    @Produces("application/json")
    public Order newOrder(@PathParam("customerEmail") String customerEmail, List<BasketItem> basketItems) {
        return checkoutBean.checkout(customerEmail, basketItems);
    }

}
