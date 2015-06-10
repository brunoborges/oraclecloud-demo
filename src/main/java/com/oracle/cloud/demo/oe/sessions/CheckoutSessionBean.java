/**
 * Copyright © 2015, 2015, Oracle and/or its affiliates. All rights reserved.
 */
package com.oracle.cloud.demo.oe.sessions;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import com.oracle.cloud.demo.oe.entities.Customer;
import com.oracle.cloud.demo.oe.entities.Order;
import com.oracle.cloud.demo.oe.entities.OrderItem;
import com.oracle.cloud.demo.oe.web.util.BasketItem;

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

	@Resource(lookup = "jms/OEConnectionFactory")
	private ConnectionFactory connectionFactory;

	@Resource(lookup = "jms/OEProcessOrders")
	private Queue queue;

	public Order checkout(String user, List<BasketItem> basketItems) {

		String userEmail = user;

		// mock user if it's 'customer'
		if (user.equals("customer")) {
			userEmail = "Graham.Spielberg@CHUKAR.EXAMPLE.COM";
		}

		Customer cust = customerDao.getCustomerByEmail(userEmail);

		BigDecimal total = calculateTotal(basketItems);

		Order order = new Order(cust, new Timestamp(
				(new java.util.Date()).getTime()), new Long(0), "online",
				(short) 1, total, 1, null);

		orderDao.create(order);

		short itemId = 1;
		for (BasketItem basketItem : basketItems) {
			OrderItem orderItem = new OrderItem(itemId++, order,
					basketItem.getProduct(), basketItem.getQuantity(),
					basketItem.getSubtotal());
			orderItemDao.create(orderItem);
		}

		queueOrderProcessing(order);

		return order;
	}

	private void queueOrderProcessing(Order order) {
		// JMS set TimeToDeliver to delay change of order status
		Connection connection = null;
		try {
			connection = connectionFactory.createConnection();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(queue);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			Message message = session.createObjectMessage(order.getOrderId());
			producer.send(message);
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

	private BigDecimal calculateTotal(List<BasketItem> basketItems) {
		BigDecimal total = BigDecimal.ZERO;
		for (BasketItem bi : basketItems) {
			total = total.add(bi.getSubtotal());
		}
		return total;
	}

}
