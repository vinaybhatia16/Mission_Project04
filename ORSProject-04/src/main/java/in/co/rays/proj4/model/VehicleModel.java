package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.bean.VehicleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class VehicleModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_vehicle");

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}

		} catch (Exception e) {
			throw new DatabaseException("Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;
	}

	public long add(VehicleBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		int pk = 0;

		VehicleBean existbean = findByVehicleName(bean.getVehicleName());

		if (existbean != null) {
			throw new DuplicateRecordException("vehicle name already exists");
		}

		try {

			pk = nextPk();

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_vehicle values(?,?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getVehicleName());
			pstmt.setString(3, bean.getModel());
			pstmt.setString(4, bean.getCompany());
			pstmt.setDouble(5, bean.getPrice());
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
				throw new ApplicationException("Rollback Exception");
			}

			throw new ApplicationException("Exception in add Vehicle");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(VehicleBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		VehicleBean existbean = findByVehicleName(bean.getVehicleName());
		if (existbean != null && existbean.getId() != bean.getId()) {
			throw new DuplicateRecordException("vehicle name already exists");
		}
		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_vehicle set vehiclename=?, model=?, company=?, price=?, createdby=?, modifiedby=?, createddatetime=?, modifieddatetime=? where id=?");

			pstmt.setString(1, bean.getVehicleName());
			pstmt.setString(2, bean.getModel());
			pstmt.setString(3, bean.getCompany());
			pstmt.setLong(4, bean.getPrice());
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
				throw new ApplicationException("Rollback Exception");
			}

			throw new ApplicationException("Exception in update Vehicle");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(VehicleBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_vehicle where id=?");

			pstmt.setLong(1, bean.getId());

			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback Exception");
			}

			throw new ApplicationException("Exception in delete Vehicle");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public VehicleBean findByPk(long pk) throws ApplicationException {

		Connection conn = null;
		VehicleBean bean = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_vehicle where id=?");

			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new VehicleBean();

				bean.setId(rs.getLong(1));
				bean.setVehicleName(rs.getString(2));
				bean.setModel(rs.getString(3));
				bean.setCompany(rs.getString(4));
				bean.setPrice(rs.getLong(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));

			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in findByPk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<VehicleBean> list() throws ApplicationException {

		return search(null, 0, 0);
	}

	public List<VehicleBean> search(VehicleBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;

		List<VehicleBean> list = new ArrayList<VehicleBean>();

		StringBuffer sql = new StringBuffer("select * from st_vehicle where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id=" + bean.getId());
			}

			if (bean.getVehicleName() != null && bean.getVehicleName().length() > 0) {

				sql.append(" and vehiclename like '" + bean.getVehicleName() + "%'");
			}
			if (bean.getCompany() != null && bean.getCompany().length() > 0) {

				sql.append(" and company like '" + bean.getCompany() + "%'");
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

				bean = new VehicleBean();

				bean.setId(rs.getLong(1));
				bean.setVehicleName(rs.getString(2));
				bean.setModel(rs.getString(3));
				bean.setCompany(rs.getString(4));
				bean.setPrice(rs.getLong(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));

				list.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in search Vehicle");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	public VehicleBean findByVehicleName(String vehicleName) throws ApplicationException {

		Connection conn = null;
		VehicleBean bean = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_vehicle where vehiclename=?");

			pstmt.setString(1, vehicleName);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new VehicleBean();

				bean.setId(rs.getLong(1));
				bean.setVehicleName(rs.getString(2));
				bean.setModel(rs.getString(3));
				bean.setCompany(rs.getString(4));
				bean.setPrice(rs.getLong(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();

			throw new ApplicationException("Exception in findByVehicleName");
		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}
}