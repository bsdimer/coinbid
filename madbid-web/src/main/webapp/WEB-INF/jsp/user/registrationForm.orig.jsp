<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/app/user.form.js"></script>
</head>
<body>

<sec:authorize access="isAnonymous()">
    <form:form action="${pageContext.request.contextPath}/user/register" modelAttribute="user" method="POST" id="userRegisterForm"
               enctype="utf8" role="form">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <c:if test="${user.signInProvider != null}">
            <form:hidden path="signInProvider"/>
        </c:if>

        <div class="row">
            <div class="col-lg-2 col-lg-offset-1">
                <h1><spring:message code="label.user.registration.page.title"/></h1>
            </div>
        </div>

        <fieldset>
            <legend><spring:message code="common.main"/></legend>

            <div class="row">
                <div id="form-group-firstName" class="form-group col-lg-2 col-lg-offset-1">
                    <label class="control-label" for="user-firstName"><spring:message
                            code="label.user.firstName"/>:</label>
                    <form:input id="user-firstName" path="firstName" cssClass="form-control"/>
                    <form:errors id="error-firstName" path="firstName" cssClass="help-block"/>
                </div>
            </div>
            <div class="row">
                <div id="form-group-lastName" class="form-group col-lg-2 col-lg-offset-1">
                    <label class="control-label" for="user-lastName"><spring:message
                            code="label.user.lastName"/>:</label>
                    <form:input id="user-lastName" path="lastName" cssClass="form-control"/>
                    <form:errors id="error-lastName" path="lastName" cssClass="help-block"/>
                </div>
            </div>
            <div class="row">
                <div id="form-group-username" class="form-group col-lg-2 col-lg-offset-1">
                    <label class="control-label" for="user-username"><spring:message
                            code="label.user.username"/>:</label>
                    <form:input id="user-username" path="username" cssClass="form-control"/>
                    <form:errors id="error-username" path="username" cssClass="help-block"/>
                </div>
            </div>
            <div class="row">
                <div id="form-group-email" class="form-group col-lg-2 col-lg-offset-1">
                    <label class="control-label" for="user-email"><spring:message
                            code="label.user.email"/>:</label>
                    <form:input id="user-email" path="email" cssClass="form-control"/>
                    <form:errors id="error-email" path="email" cssClass="help-block"/>
                </div>
            </div>
            <div class="row">
                <div id="form-group-mobileNumber" class="form-group col-lg-2 col-lg-offset-1">
                    <label class="control-label" for="user-mobileNumber"><spring:message
                            code="label.user.mobileNumber"/>:</label>
                    <form:input id="user-mobileNumber" path="mobileNumber" cssClass="form-control"/>
                    <form:errors id="error-mobileNumber" path="mobileNumber" cssClass="help-block"/>
                </div>
            </div>
            <c:if test="${user.signInProvider == null}">
                <div class="row">
                    <div id="form-group-password" class="form-group col-lg-2 col-lg-offset-1">
                        <label class="control-label" for="user-password"><spring:message
                                code="label.user.password"/>:</label>
                        <form:password id="user-password" path="password" cssClass="form-control"/>
                        <form:errors id="error-password" path="password" cssClass="help-block"/>
                    </div>
                </div>
                <div class="row">
                    <div id="form-group-passwordVerification" class="form-group col-lg-2 col-lg-offset-1">
                        <label class="control-label" for="user-passwordVerification"><spring:message
                                code="label.user.passwordVerification"/>:</label>
                        <form:password id="user-passwordVerification" path="passwordVerification"
                                       cssClass="form-control"/>
                        <form:errors id="error-passwordVerification" path="passwordVerification"
                                     cssClass="help-block"/>
                    </div>
                </div>
            </c:if>
        </fieldset>
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="row">
                    <div id="form-group-country" class="form-group col-lg-2 col-lg-offset-1">
                        <label class="control-label" for="user-country"><spring:message
                                code="label.user.country"/>:</label>
                        <form:input id="user-country" path="country" cssClass="form-control"/>
                        <form:errors id="error-country" path="country" cssClass="help-block"/>
                    </div>
                </div>
                <div class="row">
                    <div id="form-group-city" class="form-group col-lg-2 col-lg-offset-1">
                        <label class="control-label" for="user-city"><spring:message code="label.user.city"/>:</label>
                        <form:input id="user-city" path="city" cssClass="form-control"/>
                        <form:errors id="error-city" path="city" cssClass="help-block"/>
                    </div>
                </div>
                    <%--<div class="row">
                        <div id="form-group-quarter" class="form-group col-lg-2 col-lg-offset-1">
                            <label class="control-label" for="user-quarter"><spring:message
                                    code="label.user.quarter"/>:</label>
                            <form:input id="user-quarter" path="quarter" cssClass="form-control"/>
                            <form:errors id="error-quarter" path="quarter" cssClass="help-block"/>
                        </div>
                    </div>--%>
                <div class="row">
                    <div id="form-group-street1" class="form-group col-lg-2 col-lg-offset-1">
                        <label class="control-label" for="user-street1"><spring:message
                                code="label.user.street1"/>:</label>
                        <form:input id="user-street1" path="street1" cssClass="form-control"/>
                        <form:errors id="error-street1" path="street1" cssClass="help-block"/>
                    </div>
                </div>

                <div class="row">
                    <div id="form-group-street2" class="form-group col-lg-2 col-lg-offset-1">
                        <label class="control-label" for="user-street2"><spring:message
                                code="label.user.street2"/>:</label>
                        <form:input id="user-street2" path="street2" cssClass="form-control"/>
                        <form:errors id="error-street2" path="street2" cssClass="help-block"/>
                    </div>
                </div>

                    <%--<div class="row">
                        <div id="form-group-number" class="form-group col-lg-2 col-lg-offset-1">
                            <label class="control-label" for="user-number"><spring:message
                                    code="label.user.number"/>:</label>
                            <form:input id="user-number" path="number" cssClass="form-control"/>
                            <form:errors id="error-number" path="number" cssClass="help-block"/>
                        </div>
                    </div>

                    <div class="row">
                        <div id="form-group-entrance" class="form-group col-lg-2 col-lg-offset-1">
                            <label class="control-label" for="user-entrance"><spring:message
                                    code="label.user.entrance"/>:</label>
                            <form:input id="user-entrance" path="entrance" cssClass="form-control"/>
                            <form:errors id="error-entrance" path="entrance" cssClass="help-block"/>
                        </div>
                    </div>
                    <div class="row">
                        <div id="form-group-floor" class="form-group col-lg-2 col-lg-offset-1">
                            <label class="control-label" for="user-floor"><spring:message code="label.user.floor"/>:</label>
                            <form:input id="user-floor" path="floor" cssClass="form-control"/>
                            <form:errors id="error-floor" path="floor" cssClass="help-block"/>
                        </div>
                    </div>
                    <div class="row">
                        <div id="form-group-apartment" class="form-group col-lg-2 col-lg-offset-1">
                            <label class="control-label" for="user-apartment"><spring:message
                                    code="label.user.apartment"/>:</label>
                            <form:input id="user-apartment" path="apartment" cssClass="form-control"/>
                            <form:errors id="error-apartment" path="apartment" cssClass="help-block"/>
                        </div>
                    </div>--%>
                <div class="row">
                    <div id="form-group-postcode" class="form-group col-lg-2 col-lg-offset-1">
                        <label class="control-label" for="user-postcode"><spring:message
                                code="label.user.postcode"/>:</label>
                        <form:input id="user-postcode" path="postcode" cssClass="form-control"/>
                        <form:errors id="error-postcode" path="postcode" cssClass="help-block"/>
                    </div>
                </div>
            </div>
        </div>
        <div style="padding-bottom: 10px;">
            <button type="submit" class="btn btn-default"><spring:message
                    code="label.user.registration.submit.button"/></button>
        </div>
    </form:form>
</sec:authorize>
<sec:authorize access="isAuthenticated()">
    <p><spring:message code="text.registration.page.authenticated.user.help"/></p>
</sec:authorize>
</body>
</html>