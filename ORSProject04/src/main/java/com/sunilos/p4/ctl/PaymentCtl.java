package com.sunilos.p4.ctl;

import com.sunilos.p4.bean.PaymentBean;
import com.sunilos.p4.model.PaymentModel;
import com.sunilos.p4.util.DataUtility;
import com.sunilos.p4.util.DataValidator;
import com.sunilos.p4.util.PropertyReader;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;

@WebServlet("/ctl/PaymentCtl")
public class PaymentCtl extends BaseCtl<PaymentBean, PaymentModel> {

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("upi"))) {
            request.setAttribute("upi",
                    PropertyReader.getValue("error.require", "UPI"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("card"))) {
            request.setAttribute("card",
                    PropertyReader.getValue("error.require", "Card"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("netBanking"))) {
            request.setAttribute("netBanking",
                    PropertyReader.getValue("error.require", "Net Banking"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("wallet"))) {
            request.setAttribute("wallet",
                    PropertyReader.getValue("error.require", "Wallet"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("paymentStatus"))) {
            request.setAttribute("paymentStatus",
                    PropertyReader.getValue("error.require", "Payment Status"));
            pass = false;
        }

        return pass;
    }

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
        return ORSView.PAYMENT_VIEW;
    }

    @Override
    protected String getView(String op) {

        if (OP_CANCEL.equalsIgnoreCase(op)) {
            return ORSView.PAYMENT_CTL;
        }

        return ORSView.PAYMENT_VIEW;
    }

    @Override
    protected PaymentModel getModel() {
        return new PaymentModel();
    }
}