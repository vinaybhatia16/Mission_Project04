package com.sunilos.p4.bean;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentBean extends BaseBean {

	private String upi;

	private String Card;

	private String NetBanking;

	private String Wallet;

	private String PaymentStatus;

	public String getUpi() {
		return upi;
	}

	public void setUpi(String upi) {
		this.upi = upi;
	}

	public String getCard() {
		return Card;
	}

	public void setCard(String card) {
		Card = card;
	}

	public String getNetBanking() {
		return NetBanking;
	}

	public void setNetBanking(String netBanking) {
		NetBanking = netBanking;
	}

	public String getWallet() {
		return Wallet;
	}

	public void setWallet(String wallet) {
		Wallet = wallet;
	}

	public String getPaymentStatus() {
		return PaymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		PaymentStatus = paymentStatus;
	}

	@Override
	public String getKey() {
		return null;
	}

	@Override
	public String getValue() {
		return null;
	}
	
	public void setResultset(ResultSet rs) {
		try {
			super.setResultset(rs);
			this.setId(rs.getInt(1));
			this.setUpi(rs.getString(2));
			this.setCard(rs.getString(3));
			this.setNetBanking(rs.getString(4));
			this.setWallet(rs.getString(5));
			this.setPaymentStatus(rs.getString(6));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
