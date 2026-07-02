package com.sunilos.p4.ctl;

import com.sunilos.p4.bean.PaymentBean;
import com.sunilos.p4.model.PaymentModel;
import com.sunilos.p4.util.DataUtility;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;

@WebServlet("/ctl/PaymentListCtl")
public class PaymentListCtl extends BaseListCtl<PaymentBean, PaymentModel> {

    @Override
    protected PaymentBean populateBean(HttpServletRequest request) {

        PaymentBean bean = new PaymentBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setUpi(DataUtility.getString(request.getParameter("upi")));
        bean.setCard(DataUtility.getString(request.getParameter("card")));
        bean.setNetBanking(
                DataUtility.getString(request.getParameter("netBanking")));
        bean.setWallet(
                DataUtility.getString(request.getParameter("wallet")));
        bean.setPaymentStatus(
                DataUtility.getString(request.getParameter("paymentStatus")));

        populateDTO(bean, request);

        return bean;
    }

    @Override
    protected String getView() {
        return ORSView.PAYMENT_LIST_VIEW;
    }

    @Override
    protected String getView(String op) {

        if (OP_CANCEL.equalsIgnoreCase(op)) {
            return ORSView.PAYMENT_LIST_CTL;
        }

        return ORSView.PAYMENT_LIST_VIEW;
    }

    @Override
    protected PaymentModel getModel() {
        return new PaymentModel();
    }
}