package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.BusBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class BusModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_bus");

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new DatabaseException("Exception : Exception in getting PK");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;
	}

	public BusBean findByPassengerName(String passengerName) throws ApplicationException {

		Connection conn = null;
		BusBean bean = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_bus where passenger_name = ?");

			pstmt.setString(1, passengerName);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new BusBean();

				bean.setId(rs.getLong(1));
				bean.setPassengerName(rs.getString(2));
				bean.setSource(rs.getString(3));
				bean.setDestination(rs.getString(4));
				bean.setTicketFare(rs.getLong(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception : Error finding passenger name");
		}

		return bean;
	}

	public long add(BusBean bean) throws ApplicationException, DuplicateRecordException {

		BusBean existBean = findByPassengerName(bean.getPassengerName());

		if (existBean != null) {

			throw new DuplicateRecordException("Passenger Name already exists");
		}

		int pk = 0;

		Connection conn = null;

		try {

			pk = nextPk();

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_bus values(?,?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getPassengerName());
			pstmt.setString(3, bean.getSource());
			pstmt.setString(4, bean.getDestination());
			pstmt.setLong(5, bean.getTicketFare());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
			}

			throw new ApplicationException("Exception : Exception in add Bus");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(BusBean bean) throws ApplicationException, DuplicateRecordException {

		BusBean existBean = findByPassengerName(bean.getPassengerName());

		if (existBean != null && existBean.getId() != bean.getId()) {

			throw new DuplicateRecordException("Passenger Name already exists");
		}

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_bus set passenger_name=?, source=?, destination=?, ticket_fare=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getPassengerName());
			pstmt.setString(2, bean.getSource());
			pstmt.setString(3, bean.getDestination());
			pstmt.setLong(4, bean.getTicketFare());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.setLong(9, bean.getId());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
			}

			throw new ApplicationException("Exception : Exception in update Bus");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(BusBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_bus where id=?");

			pstmt.setLong(1, bean.getId());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in delete Bus");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}
	}

	public BusBean findByPk(long pk) throws ApplicationException {

		Connection conn = null;

		BusBean bean = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_bus where id=?");

			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new BusBean();

				bean.setId(rs.getLong(1));
				bean.setPassengerName(rs.getString(2));
				bean.setSource(rs.getString(3));
				bean.setDestination(rs.getString(4));
				bean.setTicketFare(rs.getLong(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in getting Bus by PK");
		}

		return bean;
	}

	

	public List<BusBean> search(BusBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;

		List<BusBean> list = new ArrayList<BusBean>();

		StringBuffer sql = new StringBuffer("select * from st_bus where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {

				sql.append(" and id = " + bean.getId());
			}

			if (bean.getPassengerName() != null && bean.getPassengerName().length() > 0) {
			    sql.append(" and passenger_name like '" + bean.getPassengerName() + "%'");
			}

			if (bean.getSource() != null && bean.getSource().length() > 0) {

				sql.append(" and source like '" + bean.getSource() + "%'");
			}
		}

		if (pageNo > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" limit " + pageNo + "," + pageSize);
		}

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new BusBean();

				bean.setId(rs.getLong(1));
				bean.setPassengerName(rs.getString(2));
				bean.setSource(rs.getString(3));
				bean.setDestination(rs.getString(4));
				bean.setTicketFare(rs.getLong(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));

				list.add(bean);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in search Bus");
		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
	public List<BusBean> list() throws ApplicationException {
	    
	    Connection conn = null;
	    List<BusBean> list = new ArrayList<BusBean>();

	    try {
	        conn = JDBCDataSource.getConnection();

	        PreparedStatement pstmt = conn.prepareStatement(
	                "select * from st_bus order by passenger_name");

	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {

	            BusBean bean = new BusBean();

	            bean.setId(rs.getLong("id"));
	            bean.setPassengerName(rs.getString("passenger_name"));

	            list.add(bean);
	        }

	        rs.close();
	        pstmt.close();

	    } catch (Exception e) {
	        throw new ApplicationException("Exception in Bus list");
	    } finally {
	        JDBCDataSource.closeConnection(conn);
	    }

	    return list;
	}
}