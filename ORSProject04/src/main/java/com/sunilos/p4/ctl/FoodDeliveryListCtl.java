package com.sunilos.p4.ctl;

import com.sunilos.p4.bean.FoodDeliveryBean;
import com.sunilos.p4.model.FoodDeliveryModel;
import com.sunilos.p4.util.DataUtility;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
@WebServlet("/ctl/FoodDeliveryListCtl")
public class FoodDeliveryListCtl extends BaseListCtl<FoodDeliveryBean, FoodDeliveryModel> {
	
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
		return ORSView.FOOD_DELIVERY_LIST_VIEW;
	}

	@Override
	protected String getView(String op) {
		if (OP_CANCEL.equalsIgnoreCase(op)) {
			return ORSView.FOOD_DELIVERY_LIST_CTL;
		}
		return ORSView.FOOD_DELIVERY_LIST_VIEW;
		
	}

	@Override
	protected FoodDeliveryModel getModel() {
		return new FoodDeliveryModel();
	}

}
