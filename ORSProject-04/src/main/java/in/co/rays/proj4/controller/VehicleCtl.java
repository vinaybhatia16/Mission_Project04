package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.VehicleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.VehicleModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "VehicleCtl", urlPatterns = { "/ctl/VehicleCtl" })
public class VehicleCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(VehicleCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("VehicleCtl validate started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("vehicleName"))) {

			request.setAttribute("vehicleName", PropertyReader.getValue("error.require", "Vehicle Name"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("model"))) {

			request.setAttribute("model", PropertyReader.getValue("error.require", "Model"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("company"))) {

			request.setAttribute("company", PropertyReader.getValue("error.require", "Company"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("price"))) {

			request.setAttribute("price", PropertyReader.getValue("error.require", "Price"));

			pass = false;

		} else if (!DataValidator.isInteger(request.getParameter("price"))) {

			request.setAttribute("price", "Price must be numeric");

			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("VehicleCtl populateBean started");

		VehicleBean bean = new VehicleBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setVehicleName(DataUtility.getString(request.getParameter("vehicleName")));

		bean.setModel(DataUtility.getString(request.getParameter("model")));

		bean.setCompany(DataUtility.getString(request.getParameter("company")));

		bean.setPrice(DataUtility.getLong(request.getParameter("price")));

		populateDTO(bean, request);

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("VehicleCtl doGet started");

		long id = DataUtility.getLong(request.getParameter("id"));

		VehicleModel model = new VehicleModel();

		if (id > 0) {

			try {

				VehicleBean bean = model.findByPk(id);

				ServletUtility.setBean(bean, request);

			} catch (ApplicationException e) {

				e.printStackTrace();

				ServletUtility.handleException(e, request, response);

				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("VehicleCtl doPost started");

		String op = DataUtility.getString(request.getParameter("operation"));

		VehicleModel model = new VehicleModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			VehicleBean bean = (VehicleBean) populateBean(request);

			try {

				model.add(bean);

				ServletUtility.setBean(bean, request);

				ServletUtility.setSuccessMessage("Vehicle added successfully", request);

			} catch (ApplicationException e) {

				e.printStackTrace();

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("vehicle name already exists" + bean.getVehicleName(), request);
				e.printStackTrace();

			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			VehicleBean bean = (VehicleBean) populateBean(request);

			try {
				if (id > 0) {

					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("record updated successfully", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage(bean.getVehicleName() + " Vehicle Name Already Exist", request);
				e.printStackTrace();
			} catch (ApplicationException e) {
				e.printStackTrace();
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.VEHICLE_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.VEHICLE_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.VEHICLE_VIEW;
	}
}
