package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.BusBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.BusModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "BusCtl", urlPatterns = { "/ctl/BusCtl" })

public class BusCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(BusCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("BusCtl validate started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("passengerName"))) {

			request.setAttribute("passengerName", PropertyReader.getValue("error.require", "Passenger Name"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("source"))) {

			request.setAttribute("source", PropertyReader.getValue("error.require", "Source"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("destination"))) {

			request.setAttribute("destination", PropertyReader.getValue("error.require", "Destination"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("ticketFare"))) {

			request.setAttribute("ticketFare", PropertyReader.getValue("error.require", "Ticket Fare"));

			pass = false;

		} else if (!DataValidator.isLong(request.getParameter("ticketFare"))) {

			request.setAttribute("ticketFare", "Ticket Fare must be numeric");

			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("BusCtl populateBean started");

		BusBean bean = new BusBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setPassengerName(DataUtility.getString(request.getParameter("passengerName")));

		bean.setSource(DataUtility.getString(request.getParameter("source")));

		bean.setDestination(DataUtility.getString(request.getParameter("destination")));

		bean.setTicketFare(DataUtility.getLong(request.getParameter("ticketFare")));

		populateDTO(bean, request);

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("BusCtl doGet started");

		long id = DataUtility.getLong(request.getParameter("id"));

		BusModel model = new BusModel();

		if (id > 0) {

			try {

				BusBean bean = model.findByPk(id);

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

		log.debug("BusCtl doPost started");

		String op = DataUtility.getString(request.getParameter("operation"));

		BusModel model = new BusModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			BusBean bean = (BusBean) populateBean(request);

			try {

				model.add(bean);

				ServletUtility.setBean(bean, request);

				ServletUtility.setSuccessMessage("Bus record added successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);

				ServletUtility.setErrorMessage(bean.getPassengerName() + " already exists", request);

			} catch (ApplicationException e) {

				e.printStackTrace();
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			BusBean bean = (BusBean) populateBean(request);

			try {

				if (id > 0) {

					model.update(bean);
				}

				ServletUtility.setBean(bean, request);

				ServletUtility.setSuccessMessage("Bus record updated successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);

				ServletUtility.setErrorMessage(bean.getPassengerName() + " already exists", request);

			} catch (ApplicationException e) {

				e.printStackTrace();

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BUS_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BUS_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {

		return ORSView.BUS_VIEW;
	}
}