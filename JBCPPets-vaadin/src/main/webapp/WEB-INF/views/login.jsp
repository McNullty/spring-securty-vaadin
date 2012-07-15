<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<c:url value="/styles/style.css" var="cssUrl" />
<link rel="stylesheet" type="text/css" href="${cssUrl}" />
<title>JBCP Pets Vaadin</title>
</head>
<body>

	<h1>Please Log In to Your Account</h1>
	<p>Please use the form below to log in to your account.</p>
	<form action="/JBCPPets-vaadin/j_spring_security_check" method="post">
		<label for="j_username">Login</label>: <input id="j_username"
			name="j_username" size="20" maxlength="50" type="text" /> <br />

		<%--Promjenjena je defaulta vrijednost checkboxa iz 
		"_spring_security_remember_me" u "_remeber_me" --%>
		<input id="_remember_me" name="_remember_me" type="checkbox"
			value="true" /> <label for="_remember_me">Remember Me?</label> <br />

		<label for="j_password">Password</label>: <input id="j_password"
			name="j_password" size="20" maxlength="50" type="password" /> <br />

		<input type="submit" value="Login" />
	</form>
</body>
</html>