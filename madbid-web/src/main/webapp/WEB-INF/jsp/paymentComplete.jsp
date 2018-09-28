<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Response Page</title>

</head>
<body>
<br/>
Status: ${transactionStatus}<br/>
Transaction ID: ${transactionId}<br/>
<a href="${pageContext.request.contextPath}/">Back</a>

<br/>
</body>
</html>