<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title><spring:message code="common.title"/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap-theme.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/font-awesome.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/js/vendor/jquery-ui/jquery-ui-1.11.1.custom/jquery-ui.css"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/js/vendor/jquery-ui/jquery-ui-1.11.1.custom/jquery-ui.theme.css"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/js/vendor/prime-ui/primeui-1.1/development/primeui-1.1.css"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/js/vendor/jquery-steps/jquery.steps-1.1.0/jquery.steps.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/vendor/jquery-2.0.3.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/vendor/bootstrap.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/vendor/prime-ui/primeui-1.1/development/primeui-1.1.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/vendor/jquery-ui/jquery-ui-1.11.1.custom/jquery-ui.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/vendor/jquery-steps/jquery.steps-1.1.0/jquery.steps.js"></script>
    <sitemesh:write property="head"/>
</head>
<body>

<div class="page">
    <div class="navbar navbar-default" role="navigation">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                <span class="sr-only"><spring:message code="label.navigation.toggle.navigation.button"/></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <span class="navbar-brand">Madbid Demo</span>
        </div>
        <div class="collapse navbar-collapse navbar-ex1-collapse">
            <ul class="nav navbar-nav navbar-left">
                <sec:authorize access="isAuthenticated()">
                    <li><a href="${pageContext.request.contextPath}/"><spring:message
                            code="label.navigation.home.link"/></a></li>
                    <li><a href="${pageContext.request.contextPath}/user/userPanel"><spring:message
                            code="label.navigation.userPanel"/></a></li>
                    <li><a href="#" onclick="$('#buyCredits_dlg').puidialog('show');"><spring:message
                            code="label.navigation.payment"/></a></li>
                    <li>
                        <form action="${pageContext.request.contextPath}/notification/subscribe" method="POST">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-default navbar-btn">
                                <spring:message code="label.navigation.subscribe"/>
                            </button>
                        </form>
                    </li>
                </sec:authorize>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <sec:authorize access="isAnonymous()">
                    <li><a href="${pageContext.request.contextPath}/user/register"><spring:message
                            code="label.navigation.registration.link"/></a></li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li>
                        <form action="${pageContext.request.contextPath}/logout" method="POST">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-default navbar-btn">
                                <spring:message code="label.navigation.logout.link"/>
                            </button>
                        </form>
                    </li>
                </sec:authorize>
            </ul>
            <sec:authorize access="isAuthenticated()">
                <p class="nav navbar-nav navbar-right navbar-text sign-in-text">
                    <sec:authentication property="principal.socialSignInProvider" var="signInProvider"/>
                    <c:if test="${signInProvider == 'FACEBOOK'}">
                        <i class="icon-facebook-sign"></i>
                    </c:if>
                    <c:if test="${signInProvider == 'TWITTER'}">
                        <i class="icon-twitter-sign"></i>
                    </c:if>
                    <c:if test="${empty signInProvider}">
                        <spring:message code="label.navigation.signed.in.as.text"/>
                    </c:if>
                    <sec:authentication property="principal.username"/>
                </p>
            </sec:authorize>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <div class="content">
        <div id="view-holder">
            <sitemesh:write property="body"/>
        </div>
    </div>

    <div id="buyCredits_dlg" title="<spring:message code="label.navigation.payment"/>">
        <form id="pay4coinsForm" action="${pageContext.request.contextPath}/pay4coins">
            <div>

                <h3><spring:message code="label.payment.packet"/></h3>
                <section>
                    <div id="packages_div">
                        <input type="radio" id="pack20" name="packRadio" value="20">
                        <label for="pack20" class="buttonPackets">20 coins</label>
                        <input type="radio" id="pack100" name="packRadio" value="100">
                        <label for="pack100" class="buttonPackets">100 coins</label>
                        <input type="radio" id="pack500" name="packRadio" value="500">
                        <label for="pack500" class="buttonPackets">500 coins</label>
                        <input type="radio" id="pack2000" name="packRadio" value="2000">
                        <label for="pack2000" class="buttonPackets">2000 coins</label>
                        <input type="radio" id="pack10000" name="packRadio" value="10000">
                        <label for="pack10000" class="buttonPackets">10000 coins</label>
                    </div>
                </section>

                <h3><spring:message code="label.payment.paymentType"/></h3>
                <section>
                    <div id="paymentTypes_div">

                        <input type="radio" id="paymentType_dc" name="paymentType" value="dc">
                        <label for="paymentType_dc" class="buttonPackets">
                            <spring:message code="label.payment.debitCard"/>
                        </label>

                        <input type="radio" id="paymentType_cc" name="paymentType" value="cc">
                        <label for="paymentType_cc" class="buttonPackets">
                            <spring:message code="label.payment.creditCard.message"/>
                        </label>

                        <input type="radio" id="paymentType_epay" name="paymentType" value="epay">
                        <label for="paymentType_epay" class="buttonPackets">
                            <spring:message code="label.payment.epay"/>
                        </label>

                        <input type="radio" id="paymentType_paypal" name="paymentType" value="paypal">
                        <label for="paymentType_paypal" class="buttonPackets">
                            Paypal
                        </label>
                    </div>
                </section>

                <h3><spring:message code="label.payment.finalizing"/></h3>
                <section>
                    <%--<label for="pt_invoice_view">
                        <spring:message code="label.payment.invoiceId"/>
                    </label>
                    <input id="pt_invoice_view" type=text name=INVOICE value=""/>

                    <label for="pt_amount_view">
                        <spring:message code="label.payment.amount"/>
                    </label>
                    <input id="pt_amount_view" type=text name=AMOUNT value=""/>

                    <label for="pt_currency_view">
                        <spring:message code="common.currency"/>
                    </label>
                    <input id="pt_currency_view" type=text name=CURRENCY value="BGN"/>

                    <label for="pt_expTime_view">
                        <spring:message code="label.payment.validThrough"/>
                    </label>
                    <input id="pt_expTime_view" type=text name=EXP_TIME value=""/>

                    <label for="pt_descr_view">
                        <spring:message code="common.description"/>
                    </label>
                    <input id="pt_descr_view" type=text name=DESCR value="<spring:message code="label.payment.descr"/>"/>--%>
                </section>
            </div>
        </form>
        <form id="paymentForm" action="http://demo.epay.bg" method="POST">
            <input id="pt_page" type=hidden name=PAGE value="paylogin">
            <input type=hidden name=ENCODED value="[ENCODED]">
            <input type=hidden name=CHECKSUM value="[CHECKSUM]">
            <input type=hidden name=URL_OK value="http://localhost:8080/madbid-web">
            <input type=hidden name=URL_CANCEL value="http://localhost:8080/madbid-web">
            <input type=hidden name=MIN value="1"/>
            <input id="pt_invoice" type=hidden name=INVOICE value=""/>
            <input id="pt_amount" type=hidden name=AMOUNT value=""/>
            <input id="pt_currency" type=hidden name=CURRENCY value="BGN"/>
            <input id="pt_expTime" type=hidden name=EXP_TIME value=""/>
            <input id="pt_descr" type=hidden name=DESCR value="<spring:message code="label.payment.descr"/>"/>
            <input type=hidden name=ENCODING value="utf-8"/>
        </form>
    </div>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/app/commons.js"></script>
    <script type="text/javascript">
        $(function () {
            onLoad();
        })
    </script>

</div>
</body>
</html>