package com.sunilos.p4.bean;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FoodDeliveryBean extends BaseBean {

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	private String customerName;
	private int amount;
	private String deliveryStatus;
	private String restaurantName;

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setResultset(ResultSet rs) {
		try {
			super.setResultset(rs);
			this.setRestaurantName(rs.getString(2));
			this.setCustomerName(rs.getString(3));
			this.setAmount(rs.getInt(4));
			this.setDeliveryStatus(rs.getString(5));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}