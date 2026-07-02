<%@page import="in.co.rays.proj4.controller.VehicleCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Vehicle</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>

<body>

	<form action="<%=ORSView.VEHICLE_CTL%>" method="post">

		<%@ include file="ModuleView.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.VehicleBean"
			scope="request">
		</jsp:useBean>

		<div align="center">

			<h1 align="center" style="margin-bottom: -15; color: navy">

				<%
				if (bean != null && bean.getId() > 0) {
				%>
				Update
				<%
				} else {
				%>
				Add
				<%
				}
				%>

				Vehicle

			</h1>

			<div style="height: 15px; margin-bottom: 12px">

				<H3 align="center">

					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>

					</font>

				</H3>

				<H3 align="center">

					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>

					</font>

				</H3>

			</div>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">

			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">

			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>

				<tr>

					<th align="left">Vehicle Name <span style="color: red">*</span>

					</th>

					<td><input type="text" name="vehicleName"
						placeholder="Enter Vehicle Name"
						value="<%=DataUtility.getStringData(bean.getVehicleName())%>">

					</td>

					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("vehicleName", request)%>

					</font></td>

				</tr>


				<tr>

					<th align="left">Model <span style="color: red">*</span>

					</th>

					<td><input type="text" name="model" placeholder="Enter Model"
						value="<%=DataUtility.getStringData(bean.getModel())%>"></td>

					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("model", request)%>

					</font></td>

				</tr>


				<tr>

					<th align="left">Company <span style="color: red">*</span>

					</th>

					<td><input type="text" name="company"
						placeholder="Enter Company"
						value="<%=DataUtility.getStringData(bean.getCompany())%>">

					</td>

					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("company", request)%>

					</font></td>

				</tr>


				<tr>

					<th align="left">Price <span style="color: red">*</span>

					</th>

					<td><input type="text" name="price" placeholder="Enter Price"
						value="<%=(bean.getPrice() == 0) ? "" : bean.getPrice()%>"></td>

					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("price", request)%>

					</font></td>

				</tr>

				<tr>
					<th></th>
					<td></td>
				</tr>

				<tr>

					<th></th>

					<%
					if (bean != null && bean.getId() > 0) {
					%>

					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=VehicleCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=VehicleCtl.OP_CANCEL%>">

					</td>

					<%
					} else {
					%>

					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=VehicleCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=VehicleCtl.OP_RESET%>">

					</td>

					<%
					}
					%>

				</tr>

			</table>

		</div>

	</form>

</body>
</html>