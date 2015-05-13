package com.oracle.cloud.demo.oe.rest;

import com.oracle.cloud.demo.oe.entities.Order;
import com.oracle.cloud.demo.oe.entities.OrderItem;
import com.oracle.cloud.demo.oe.sessions.CheckoutFacade;
import com.oracle.cloud.demo.oe.sessions.OrdersFacade;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("orders")
public class OrdersResource {

    @EJB
    OrdersFacade ordersFacade;

    @EJB
    CheckoutFacade checkoutFacade;

    @GET
    @Path("fromCustomer/{customerEmail}")
    @Produces("application/json")
    public List<OrderVO> byCustomer(@PathParam("customerEmail") String customerEmail) {
        List<Order> list = ordersFacade.getOrdersByCustomerEmail(customerEmail);
        if (list == null) {
            list = Collections.emptyList();
        }

        List<OrderVO> ordersVOList = new ArrayList<>();

        for (Order o : list) {
            ordersVOList.add(new OrderVO(o));
        }

        return ordersVOList;
    }

    @GET
    @Path("fromCustomer/{customerEmail}/{orderId}")
    @Produces("application/json")
    public OrderItemVO[] items(@PathParam("customerEmail") String customerEmail, @PathParam("orderId") Integer orderId) {
        List<OrderItem> list = ordersFacade.getItemsByCustomerEmailAndOrderId(customerEmail, orderId);
        if (list == null) {
            list = Collections.emptyList();
        }

        List<OrderItemVO> itemsVOList = new ArrayList<>();
        for (OrderItem oi : list) {
            itemsVOList.add(new OrderItemVO(oi));
        }

        return itemsVOList.toArray(new OrderItemVO[0]);
    }

}
