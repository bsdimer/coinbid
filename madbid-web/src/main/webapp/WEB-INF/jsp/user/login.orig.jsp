<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/social-buttons-3.css"/>
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/static/js/vendor/html5shiv/dist/html5shiv.js"></script>
    <![endif]-->
</head>
<body>

<script src="${pageContext.request.contextPath}/static/js/vendor/knockout/knockout.js"></script>
<script src="${pageContext.request.contextPath}/static/js/vendor/stomp/lib/stomp.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/vendor/sockjs/sockjs.min.js"></script>

<div class="page-header">
    <h1><spring:message code="label.user.login.page.title"/></h1>
</div>
<sec:authorize access="isAnonymous()">
    <div class="panel panel-default">
        <div class="panel-body">
            <a href="?lang=en">English</a>|<a href="?lang=bg">Български</a>

            <h2><spring:message code="label.login.form.title"/></h2>
            <c:if test="${param.error eq 'invalidCredentials'}">
                <div class="alert alert-danger alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <spring:message code="login.error.invalid.credentials"/>
                </div>
            </c:if>
            <c:if test="${param.error eq 'maximumExceeded'}">
                <div class="alert alert-danger alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <spring:message code="login.error.maximum.exceeded"/>
                </div>
            </c:if>
            <c:if test="${param.error eq 'inactiveUser'}">
                <div class="alert alert-danger alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <spring:message code="login.error.inactive.user"/>
                </div>
            </c:if>
            <c:if test="${param.error eq 'loginError'}">
                <div class="alert alert-danger alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <spring:message code="login.error"/>
                </div>
            </c:if>
            <form action="${pageContext.request.contextPath}/login/authenticate" method="POST" role="form">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <div class="row">
                    <div id="form-group-email" class="form-group col-lg-4">
                        <label class="control-label" for="user-email"><spring:message code="label.user.email"/>:</label>
                        <input id="user-email" name="username" type="text" class="form-control"/>
                    </div>
                </div>

                <div class="row">
                    <div id="form-group-password" class="form-group col-lg-4">
                        <label class="control-label" for="user-password"><spring:message
                                code="label.user.password"/>:</label>
                        <input id="user-password" name="password" type="password" class="form-control"/>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-lg-4">
                        <button type="submit" class="btn btn-default"><spring:message
                                code="label.user.login.submit.button"/></button>
                    </div>
                </div>
            </form>
            <div class="row">
                <div class="form-group col-lg-4">
                    <a href="${pageContext.request.contextPath}/user/register"><spring:message
                            code="label.navigation.registration.link"/></a>
                </div>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-body">
            <h2><spring:message code="label.social.sign.in.title"/></h2>

            <div class="row social-button-row">
                <div class="col-lg-4">
                    <a href="<c:url value="/auth/facebook"/>">
                        <button class="btn btn-facebook"><i class="icon-facebook"></i> | <spring:message
                                code="label.facebook.sign.in.button"/></button>
                    </a>
                </div>
            </div>
            <div class="row social-button-row">
                <div class="col-lg-4">
                    <a href="<c:url value="/auth/twitter"/>">
                        <button class="btn btn-twitter"><i class="icon-twitter"></i> | <spring:message
                                code="label.twitter.sign.in.button"/></button>
                    </a>
                </div>
            </div>
        </div>
    </div>

    <div id="auctionContainer">
        <div data-bind="foreach: auctionModel().auctions">
            <div style="float: left; margin-right: 1em">
                    <%--<div>id:<span data-bind="text: auctionId"></span></div>--%>
                <div><spring:message code="common.item"/>:<span data-bind="text: itemName"></span></div>
                <div><spring:message code="common.retailPrice"/>:<span data-bind="text: retailPrice"></span></div>
                <div>status:<span data-bind="text: state"></span></div>
                <div>bids:<span data-bind="text: bidsLen"></span></div>
                <div>timer:<span data-bind="text: timer"></span></div>
                <div>currentPrice:<span data-bind="text: currentBiddingsPrice"></span><span
                        data-bind="text: itemCurrency"></span></div>
                <div>last bidder:<span data-bind="text: lastBidder"></span></div>
                    <%--<img data-bind="attr: { src: pictures()[0] }" width="100" height="100"/>--%>
                <img data-bind="attr: { src: pictureUrl }" width="100" height="100"/>
                <button data-bind="click: $root.auctionModel().bid">+<span data-bind="text: creditsStep"></span>
                </button>
            </div>
        </div>
    </div>

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

</sec:authorize>
<sec:authorize access="isAuthenticated()">
    <p><spring:message code="text.login.page.authenticated.user.help"/></p>
</sec:authorize>
</body>
</html>