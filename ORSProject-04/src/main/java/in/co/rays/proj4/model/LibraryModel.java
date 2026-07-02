package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.LibraryBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class LibraryModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_library");

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

	public LibraryBean findByBookName(String bookName) throws ApplicationException {

		Connection conn = null;
		LibraryBean bean = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_library where bookname=?");

			pstmt.setString(1, bookName);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new LibraryBean();

				bean.setId(rs.getLong(1));
				bean.setBookName(rs.getString(2));
				bean.setAuthorname(rs.getString(3));
				bean.setIssueDate(rs.getDate(4));
				bean.setPrice(rs.getInt(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception in findByBookName");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public long add(LibraryBean bean) throws ApplicationException, DuplicateRecordException {

		LibraryBean existBean = findByBookName(bean.getBookName());

		if (existBean != null) {

			throw new DuplicateRecordException("Book Name already exists");
		}

		Connection conn = null;
		int pk = 0;

		try {

			pk = nextPk();

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_library values(?,?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getBookName());
			pstmt.setString(3, bean.getAuthorname());

			pstmt.setDate(4, new java.sql.Date(bean.getIssueDate().getTime()));

			pstmt.setInt(5, bean.getPrice());

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

			throw new ApplicationException("Exception in add");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(LibraryBean bean) throws ApplicationException, DuplicateRecordException {

		LibraryBean existBean = findByBookName(bean.getBookName());

		if (existBean != null && existBean.getId() != bean.getId()) {

			throw new DuplicateRecordException("Book Name already exists");
		}

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(

					"update st_library set bookname=?,authorname=?,issuedate=?,price=?,createdby=?,modifiedby=?,createddatetime=?,modifieddatetime=? where id=?");

			pstmt.setString(1, bean.getBookName());

			pstmt.setString(2, bean.getAuthorname());

			pstmt.setDate(3, new java.sql.Date(bean.getIssueDate().getTime()));

			pstmt.setInt(4, bean.getPrice());

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

			throw new ApplicationException("Exception in update");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(LibraryBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_library where id=?");

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

			throw new ApplicationException("Exception in delete");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public LibraryBean findByPk(long pk) throws ApplicationException {

		Connection conn = null;

		LibraryBean bean = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_library where id=?");

			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new LibraryBean();

				bean.setId(rs.getLong(1));
				bean.setBookName(rs.getString(2));
				bean.setAuthorname(rs.getString(3));
				bean.setIssueDate(rs.getDate(4));
				bean.setPrice(rs.getInt(5));
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

	public List<LibraryBean> list() throws ApplicationException {

		return search(null, 0, 0);
	}

	public List<LibraryBean> search(LibraryBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;

		List<LibraryBean> list = new ArrayList<LibraryBean>();

		StringBuffer sql = new StringBuffer("select * from st_library where 1=1");

		if (bean != null) {

			if (bean.getBookName() != null && bean.getBookName().length() > 0) {

				sql.append(" and bookname like '" + bean.getBookName() + "%'");
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

				bean = new LibraryBean();

				bean.setId(rs.getLong(1));
				bean.setBookName(rs.getString(2));
				bean.setAuthorname(rs.getString(3));
				bean.setIssueDate(rs.getDate(4));
				bean.setPrice(rs.getInt(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));

				list.add(bean);
			}

		} catch (Exception e) {

			throw new ApplicationException("Exception in search");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}