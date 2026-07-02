package com.sunilos.p4.ctl;

import com.sunilos.p4.bean.FoodDeliveryBean;
import com.sunilos.p4.bean.ProductBean;
import com.sunilos.p4.model.FoodDeliveryModel;
import com.sunilos.p4.util.DataUtility;
import com.sunilos.p4.util.DataValidator;
import com.sunilos.p4.util.PropertyReader;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
@WebServlet("/ctl/FoodDeliveryCtl")
public class FoodDeliveryCtl extends BaseCtl<FoodDeliveryBean, FoodDeliveryModel> {
	
	
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("restaurentName"))) {
			request.setAttribute("restaurentName", PropertyReader.getValue("erroe.require", "restaurentName"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("customerName"))) {
			request.setAttribute("customerName", PropertyReader.getValue("error.require", "CustomerName"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("amount"))) {
			request.setAttribute("orderDate", PropertyReader.getValue("error.require", "amount"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("deliveryStatus"))) {
			request.setAttribute("deliveryStatus", PropertyReader.getValue("error.require", "deliveryStatus"));
			pass = false;
		}
		return pass;

	}
	
	@Override
	protected FoodDeliveryBean populateBean(HttpServletRequest request) {
		FoodDeliveryBean bean = new FoodDeliveryBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setRestaurantName(DataUtility.getString(request.getParameter("restaurentName")));
		bean.setCustomerName(DataUtility.getString(request.getParameter("customerName")));
		bean.setAmount(DataUtility.getInt(request.getParameter("amount")));
		bean.setDeliveryStatus(DataUtility.getString(request.getParameter("deliveryStatus")));

		populateDTO(bean, request);
		return bean;
	}

	@Override
	protected String getView() {
		
		return ORSView.FOOD_DELIVERY_VIEW;
	}

	@Override
	protected String getView(String op) {
		if (OP_CANCEL.equalsIgnoreCase(op)) {
			return ORSView.FOOD_DELIVERY_CTL;
		}
		return ORSView.FOOD_DELIVERY_VIEW;
		
	}

	@Override
	protected FoodDeliveryModel getModel() {
		
		return new FoodDeliveryModel();
	}

}
