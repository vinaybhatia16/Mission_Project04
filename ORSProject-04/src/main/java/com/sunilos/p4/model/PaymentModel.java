package com.sunilos.p4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.sunilos.p4.bean.PaymentBean;
import com.sunilos.p4.exception.ApplicationException;
import com.sunilos.p4.exception.DuplicateRecordException;
import com.sunilos.p4.util.JDBCDataSource;

public class PaymentModel extends BaseModel<PaymentBean> {

    @Override
    public long add(PaymentBean bean)
            throws ApplicationException, DuplicateRecordException {

        log.debug("Model add Started");

        Connection conn = null;
        int pk = 0;

        PaymentBean existBean = findByUPI(bean.getUpi());

        if (existBean != null) {
            throw new DuplicateRecordException("UPI already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPK();

            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO " + getTable()
                            + " VALUES(?,?,?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getUpi());
            pstmt.setString(3, bean.getCard());
            pstmt.setString(4, bean.getNetBanking());
            pstmt.setString(5, bean.getWallet());
            pstmt.setString(6, bean.getPaymentStatus());
            pstmt.setString(7, bean.getCreatedBy());
            pstmt.setString(8, bean.getModifiedBy());
            pstmt.setTimestamp(9, bean.getCreatedDatetime());
            pstmt.setTimestamp(10, bean.getModifiedDatetime());

            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (Exception e) {

            log.error("Database Exception..", e);

            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException(
                        "Exception : add rollback exception "
                                + ex.getMessage());
            }

            throw new ApplicationException(
                    "Exception : Exception in add Payment");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        log.debug("Model add End");

        return pk;
    }

    @Override
    public void update(PaymentBean bean)
            throws ApplicationException, DuplicateRecordException {

    }

    public PaymentBean findByUPI(String upi) {
        return findByUniqueColumn("UPI", upi);
    }

    @Override
    public String getWhereClause(PaymentBean bean) {

        StringBuffer sql = new StringBuffer();

        if (bean != null) {

            if (bean.getId() > 0) {
                sql.append(" AND id = " + bean.getId());
            }

            if (bean.getUpi() != null
                    && bean.getUpi().length() > 0) {
                sql.append(" AND UPI like '"
                        + bean.getUpi() + "%'");
            }

            if (bean.getPaymentStatus() != null
                    && bean.getPaymentStatus().length() > 0) {
                sql.append(" AND Payment_Status like '"
                        + bean.getPaymentStatus() + "%'");
            }
        }

        return sql.toString();
    }

    @Override
    public String getTable() {
        return "st_payment";
    }

    @Override
    public PaymentBean getBean() {
        return new PaymentBean();
    }
}