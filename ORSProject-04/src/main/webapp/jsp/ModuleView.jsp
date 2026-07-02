<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.bean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Module Page</title>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script>
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">

<script src="/ORSProject-04/js/checkbox.js"></script>
<script src="/ORSProject-04/js/datepicker.js"></script>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>
<body>
	<!-- Logo -->
	<img src="<%=request.getContextPath()%>/img/customLogo.jpg"
		align="right" width="100" height="40" border="0">
	<%
	UserBean user = (UserBean) session.getAttribute("user");
	%>
	<%
	if (user != null) {
	%>
	<h3>
		Hi,
		<%=user.getFirstName()%>
		(<%=session.getAttribute("role")%>)
	</h3>

	<%
	}
	%>

	<a href="<%=ORSView.MEETING_CTL%>">Add Meeting</a>
	<b>|</b>

	<a href="<%=ORSView.MEETING_LIST_CTL%>">List Meeting</a>
	<b>|</b>
	<a href="<%=ORSView.VEHICLE_CTL%>">Add Vehicle</a>
	<b>|</b>
	<a href="<%=ORSView.VEHICLE_LIST_CTL%>">List Vehicle</a>
	<b>|</b>
	<a href="<%=ORSView.LIBRARY_CTL%>">Add Library</a>
	<b>|</b>
	<a href="<%=ORSView.LIBRARY_LIST_CTL%>">List Library</a>
	<b>|</b>
	<a href="<%=ORSView.BUS_CTL%>">Add Bus</a>
	<b>|</b>
	<a href="<%=ORSView.BUS_LIST_CTL%>">Bus List </a>
	<b>|</b>
	<hr>


	<!-- <h1 align="center">
		<font size="10px" color="navy">Daily Module page</font>
	</h1>
	 -->

	<%@include file="Footer.jsp"%>
</body>
</html>