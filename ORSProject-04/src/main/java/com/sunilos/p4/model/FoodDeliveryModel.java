package com.sunilos.p4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.sunilos.p4.bean.FoodDeliveryBean;
import com.sunilos.p4.bean.ProductBean;
import com.sunilos.p4.exception.ApplicationException;
import com.sunilos.p4.exception.DuplicateRecordException;
import com.sunilos.p4.util.JDBCDataSource;

public class FoodDeliveryModel extends BaseModel<FoodDeliveryBean>{

	@Override
	public long add(FoodDeliveryBean bean) throws ApplicationException, DuplicateRecordException {
		
		log.debug("Model add Started");
		Connection conn = null;
		int pk = 0;

		FoodDeliveryBean existbean = findByProductName(bean.getCustomerName());
				
		if (existbean != null) {
			throw new DuplicateRecordException("CustomerName already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			// Get auto-generated next primary key
			System.out.println(pk + " in ModelJDBC");
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO " + getTable() + " VALUES(?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getRestaurantName());
			pstmt.setString(3, bean.getCustomerName());
			pstmt.setInt(4, bean.getAmount());
			pstmt.setString(5, bean.getDeliveryStatus());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	
	}

	@Override
	public void update(FoodDeliveryBean bean) throws ApplicationException, DuplicateRecordException {
		
	}
	
	public FoodDeliveryBean findByProductName(String customerName) {
		return findByUniqueColumn("Customer_Name", customerName);
	}

	@Override
	public String getWhereClause(FoodDeliveryBean bean) {
		StringBuffer sql = new StringBuffer();

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getCustomerName() != null && bean.getCustomerName().length() > 0) {
				sql.append(" AND Customer_Name like '" + bean.getCustomerName() + "%'");
			}
		}

		return sql.toString();
	}

	@Override
	public String getTable() {
		
		return "st_FoodDelivery";
	}
	

	@Override
	public FoodDeliveryBean getBean() {
		
		return new FoodDeliveryBean();
	}

}
