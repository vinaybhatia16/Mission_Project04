<%@page import="com.sunilos.p4.ctl.BaseCtl"%>
<%@page import="com.sunilos.p4.ctl.ORSView"%>
<%@page import="com.sunilos.p4.util.DataUtility"%>
<%@page import="com.sunilos.p4.util.ServletUtility"%>

<jsp:useBean id="bean" class="com.sunilos.p4.bean.FoodDeliveryBean"
	scope="request"></jsp:useBean>

<%
String _suc = ServletUtility.getSuccessMessage(request);
String _err = ServletUtility.getErrorMessage(request);
%>

<div class="container py-4" style="max-width: 580px;">
	<div class="card border-0 shadow-sm rounded-4 overflow-hidden">

		<div class="card-header text-white border-0 py-3 px-4"
			style="background: linear-gradient(135deg, #0d2137 0%, #1565c0 100%);">

			<h5 class="mb-0 fw-bold">
				<i class="bi bi-fork-knife  me-2"></i>
				<%=bean.getId() > 0 ? "Edit Food Delivery" : "Add Food Delivery"%>
			</h5>

		</div>

		<div class="card-body px-4 py-4">

			<%
			if (_suc != null && !_suc.isEmpty()) {
			%>
			<div class="alert alert-success py-2">
				<i class="bi bi-check-circle-fill me-2"></i><%=_suc%>
			</div>
			<%
			}
			%>

			<%
			if (_err != null && !_err.isEmpty()) {
			%>
			<div class="alert alert-danger py-2">
				<i class="bi bi-exclamation-triangle-fill me-2"></i><%=_err%>
			</div>
			<%
			}
			%>

			<form action="<%=ORSView.FOOD_DELIVERY_CTL%>" method="POST">

				<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
					type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">

				<input type="hidden" name="modifiedBy"
					value="<%=bean.getModifiedBy()%>"> <input type="hidden"
					name="createdDatetime"
					value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">

				<input type="hidden" name="modifiedDatetime"
					value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

				<div class="mb-3">
					<label class="form-label fw-semibold"> Restaurant Name <span
						class="text-danger">*</span>
					</label> <input type="text" name="restaurentName" class="form-control"
						maxlength="100"
						value="<%=DataUtility.getStringData(bean.getRestaurantName())%>">

					<div class="text-danger small mt-1">
						<%=ServletUtility.getErrorMessage("restaurantName", request)%>
					</div>
				</div>

				<div class="mb-3">
					<label class="form-label fw-semibold"> Customer Name <span
						class="text-danger">*</span>
					</label> <input type="text" name="customerName" class="form-control"
						maxlength="200"
						value="<%=DataUtility.getStringData(bean.getCustomerName())%>">

					<div class="text-danger small mt-1">
						<%=ServletUtility.getErrorMessage("customerName", request)%>
					</div>
				</div>

				<div class="mb-3">
					<label class="form-label fw-semibold"> Amount <span
						class="text-danger">*</span>
					</label> <input type="text" name="amount" 
						class="form-control" maxlength="200"
						value="<%=DataUtility.getStringData(bean.getAmount())%>">

					<div class="text-danger small mt-1">
						<%=ServletUtility.getErrorMessage("orderDate", request)%>
					</div>
				</div>

				<div class="mb-3">
					<label class="form-label fw-semibold"> Delivery Status <span
						class="text-danger">*</span>
					</label> <input type="text" name="price" class="form-control"
						maxlength="200"
						value="<%=DataUtility.getStringData(bean.getDeliveryStatus())%>">

					<div class="text-danger small mt-1">
						<%=ServletUtility.getErrorMessage("deliveryStatus", request)%>
					</div>
				</div>

				<table width="100%">
					<div class="d-flex gap-2 pt-2 border-top">
						<button type="submit" name="operation"
							value="<%=BaseCtl.OP_SAVE%>" class="btn btn-primary">
							<i class="bi bi-save me-1"></i> Save
						</button>

						<button type="submit" name="operation"
							value="<%=BaseCtl.OP_CANCEL%>" class="btn btn-secondary ms-auto">
							<i class="bi bi-x-circle me-1"></i> Cancel
						</button>
					</div>
				</table>

			</form>

		</div>
	</div>
</div>