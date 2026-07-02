<%@page import="in.co.rays.proj4.controller.BusCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Bus</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>

<body>

	<form action="<%=ORSView.BUS_CTL%>" method="post">

		<%@ include file="ModuleView.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.BusBean"
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

				Bus

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

					<th align="left">Passenger Name <span style="color: red">*</span>
					</th>

					<td><input type="text" name="passengerName"
						placeholder="Enter Passenger Name"
						value="<%=DataUtility.getStringData(bean.getPassengerName())%>">
					</td>

					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("passengerName", request)%>
					</font></td>

				</tr>

				<tr>

					<th align="left">Source <span style="color: red">*</span>
					</th>

					<td><input type="text" name="source"
						placeholder="Enter Source"
						value="<%=DataUtility.getStringData(bean.getSource())%>">
					</td>

					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("source", request)%>
					</font></td>

				</tr>

				<tr>

					<th align="left">Destination <span style="color: red">*</span>
					</th>

					<td><input type="text" name="destination"
						placeholder="Enter Destination"
						value="<%=DataUtility.getStringData(bean.getDestination())%>">
					</td>

					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("destination", request)%>
					</font></td>

				</tr>

				<tr>

					<th align="left">Ticket Fare <span style="color: red">*</span>
					</th>

					<td><input type="text" name="ticketFare"
						placeholder="Enter Ticket Fare"
						value="<%=(bean.getTicketFare() == 0) ? "" : bean.getTicketFare()%>">
					</td>

					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("ticketFare", request)%>
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
						name="operation" value="<%=BusCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=BusCtl.OP_CANCEL%>">
					</td>

					<%
					} else {
					%>

					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=BusCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=BusCtl.OP_RESET%>">
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