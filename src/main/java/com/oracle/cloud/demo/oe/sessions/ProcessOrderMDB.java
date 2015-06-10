/**
 * Copyright © 2015, 2015, Oracle and/or its affiliates. All rights reserved.
 */
package com.oracle.cloud.demo.oe.sessions;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.oracle.cloud.demo.oe.entities.Order;

/**
 * @author Bruno Borges
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/OEProcessOrders"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") }, name = "ProcessOrderMDB", mappedName = "jms/OEProcessOrders")
public class ProcessOrderMDB implements MessageListener {

	@EJB
	private OrdersFacade ordersFacade;

	public void onMessage(Message message) {
		Long id;
		try {
			id = (Long) ((ObjectMessage) message).getObject();
			Order order = ordersFacade.find(id);
			ordersFacade.orderDelivered(order);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
