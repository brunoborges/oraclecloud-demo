/**
 * Copyright © 2015, 2015, Oracle and/or its affiliates. All rights reserved.
 */
package com.oracle.cloud.demo.oe.entities;

public enum OrderStatus {
	PROCESSING(1), DELIVERED(2);

	private short value;

	OrderStatus(int value) {
		this.value = (short) value;
	}

	public short getValue() {
		return value;
	}

	@Override
	public String toString() {
		switch (this) {
		case PROCESSING:
			return "Processing";
		case DELIVERED:
			return "Delivered";
		default:
			throw new IllegalStateException();
		}
	}

}
