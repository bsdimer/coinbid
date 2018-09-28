<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fomr" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title></title>
</head>
<body>
<div class="page-header">
    <a href="?lang=en">English</a>|<a href="?lang=bg">Български</a>

    <h1><spring:message code="label.homepage.title"/> <sec:authentication property="principal.firstName"/>
        <sec:authentication property="principal.lastName"/></h1>
</div>
<div>
    <p><spring:message code="text.homepage.greeting"/></p>
</div>
<div>
    <spring:message code="message.coins.available"/>:
    <span data-bind="text: userModel().coinsAvailable"></span>
</div>

<script src="${pageContext.request.contextPath}/static/js/vendor/knockout/knockout.js"></script>
<script src="${pageContext.request.contextPath}/static/js/vendor/stomp/lib/stomp.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/vendor/sockjs/sockjs.min.js"></script>

<form:form method="post" action="${pageContext.request.contextPath}/buySubmit" modelAttribute="paymentEntity">

    <c:if test="${not empty redirectUrl}">
        <a href="${redirectUrl}">Pay</a>
    </c:if>

    <br/>
    <a href="${pageContext.request.contextPath}/">Cancel</a>

</form:form>

<!-- application JS -->
<script src="${pageContext.request.contextPath}/static/js/app/auctions.js"></script>
<script type="text/javascript">
    (function () {
        var socket = new SockJS('/madbid-web/stompEndpoint');
        var stompClient = Stomp.over(socket);

        var appModel = new ApplicationModel(stompClient);
        ko.applyBindings(appModel);

        appModel.connect();
    })();
</script>

</body>
</html>