<%--
  Created by IntelliJ IDEA.
  User: dimer
  Date: 8/24/14
  Time: 2:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
exception:<%=request.getAttribute("exception") %> <br/>
url:<%=request.getAttribute("url") %>
<a href="${pageContext.request.contextPath}/">Back</a>
</body>
</html>
