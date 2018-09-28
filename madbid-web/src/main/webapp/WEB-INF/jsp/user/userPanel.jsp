<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<head>
</head>
<body>

<section>
    <div class="container">
        <div class="row">
            <div class="col-sm-5">
                <h1><spring:message code="label.registrationForm"/></h1>
            </div>
        </div>
    </div>
</section>


<section>
    <div class="container">
        <div class="row">

            <div id="userPanelTabView">
                <ul id="userPanelTab" class="nav nav-pills">
                    <li role="presentation" class="active">
                        <a href="#tab1" aria-controls="home" role="tab" data-toggle="tab">
                            <span class="glyphicon glyphicon-envelope"></span>Messages</a>
                    </li>
                    <li role="presentation">
                        <a href="#tab2" data-bind="click: function () { $root.userModel().getUserAuctions('${pageContext.request.contextPath}'); }" aria-controls="home" role="tab" data-toggle="tab">
                            <span class="glyphicon glyphicon-download-alt"></span>Auctions</a>
                    </li>
                    <li role="presentation">
                        <a href="#tab3" aria-controls="home" role="tab" data-toggle="tab">
                            <span class="glyphicon glyphicon-transfer"></span>Transactions</a>
                    </li>
                    <li role="presentation">
                        <a href="#tab4" aria-controls="home" role="tab" data-toggle="tab">
                            <span class="glyphicon glyphicon-user"></span>Account info</a>
                    </li>
                </ul>
                <div class="tab-content">
                    <div id="tab1" role="tabpanel" class="tab-pane active">
                        <%@include file="inbox.jsp" %>
                    </div>
                    <div id="tab2" role="tabpanel" class="tab-pane">
                        <%@include file="userAuctions.jsp" %>
                    </div>
                    <div id="tab3" role="tabpanel" class="tab-pane">
                        <%@include file="userTransactions.jsp" %>
                    </div>
                    <div id="tab4" role="tabpanel" class="tab-pane">
                        <%@include file="userAccountInfo.jsp" %>
                    </div>
                </div>
            </div>

        </div>
    </div>
</section>


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