package com.oracle.cloud.demo.oe.sessions;

import com.oracle.cloud.demo.oe.entities.Customer;
import com.oracle.cloud.demo.oe.entities.Order;
import com.oracle.cloud.demo.oe.entities.OrderItem;
import com.oracle.cloud.demo.oe.web.util.BasketItem;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class CheckoutSessionBean {

    @EJB
    CustomersFacade customerDao;

    @EJB
    OrdersFacade orderDao;

    @EJB
    OrderItemsFacade orderItemDao;

    @EJB
    ProductInformationFacade productInformationDao;

    public Order checkout(String user, List<BasketItem> basketItems) {

        String userEmail = user;

        // mock user if it's 'customer'
        if (user.equals("customer")) {
            userEmail = "Graham.Spielberg@CHUKAR.EXAMPLE.COM";
        }

        Customer cust = customerDao.getCustomerByEmail(userEmail);

        BigDecimal total = calculateTotal(basketItems);

        Order order = new Order(cust,
                new Timestamp((new java.util.Date()).getTime()),
                new Long(0),
                "online",
                (short) 1,
                total,
                1,
                null);

        orderDao.create(order);

        short itemId = 1;
        for (BasketItem basketItem : basketItems) {
            OrderItem orderItem = new OrderItem(itemId++, order,
                    basketItem.getProduct(),
                    basketItem.getQuantity(),
                    basketItem.getSubtotal());
            orderItemDao.create(orderItem);
        }

        return order;
    }

    private BigDecimal calculateTotal(List<BasketItem> basketItems) {
        BigDecimal total = BigDecimal.ZERO;
        for (BasketItem bi : basketItems) {
            total = total.add(bi.getSubtotal());
        }
        return total;
    }

}
