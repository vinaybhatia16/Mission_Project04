package com.sunilos.p4.ctl;

import java.util.List;

import com.sunilos.p4.bean.PaymentBean;
import com.sunilos.p4.model.PaymentModel;

import jakarta.servlet.annotation.WebServlet;

@WebServlet("/ctl/PaymentReportCtl")
public class PaymentReportCtl extends BaseReportCtl<PaymentBean> {

	@Override
	public String getView() {

		return ORSView.PAYMENT_REPORT_VIEW;
	}

	@Override
	public String getCompiledReportKey() {

		return "PAYMENT_LIST_COMPILED_REPORT";
	}

	@Override
	public List<PaymentBean> getList() {
		PaymentModel model = new PaymentModel();
		List<PaymentBean> roles = model.list();
		return roles;
	}

}
