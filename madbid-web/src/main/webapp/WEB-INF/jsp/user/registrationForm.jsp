<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title></title>
</head>
<body>

<sec:authorize access="isAnonymous()">

    <form:form action="${pageContext.request.contextPath}/user/register" modelAttribute="user" method="POST"
               id="userRegisterForm" cssClass="form-horizontal"
               enctype="utf8" role="form">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <c:if test="${user.signInProvider != null}">
            <form:hidden path="signInProvider"/>
        </c:if>


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

                    <label class="col-sm-2 control-label" for="firstName">
                        <spring:message code="label.user.fullName"/>
                    </label>

                    <div class="col-xs-2">
                        <select name="solutation">
                            <option value="mr"><spring:message code='label.mr'/></option>
                            <option value="mrs"><spring:message code='label.mrs'/></option>
                            <option value="ms"><spring:message code='label.ms'/></option>
                        </select>
                    </div>

                    <div class="col-xs-3">
                        <input type="text" id="user-firstName" name="firstName" class="form-control input-md"
                               placeholder="<spring:message code='label.user.firstName'/>">
                        <form:errors id="error-firstName" path="firstName" cssClass="help-block input-md"/>
                    </div>

                    <div class="col-xs-3 bottom-form-group">
                        <input type="text" id="user-lastName" name="lastName" class="form-control input-md"
                               placeholder="<spring:message code='label.user.lastName'/>">
                        <form:errors id="error-lastName" path="lastName" cssClass="help-block input-md"/>
                    </div>
                </div>
            </div>
        </section>

        <section>
            <div class="container">
                <div class="row">

                    <label class="col-sm-2 control-label" for="user-password">
                        <spring:message code="label.user.password"/>
                    </label>

                    <div class="col-xs-3">
                        <input type="password" id="user-password" name="password"
                               class="form-control input-md"
                               placeholder="<spring:message code='label.user.password'/>">
                    </div>

                    <div class="col-xs-3 form-group">
                        <input type="password" id="user-passwordVerification" name="passwordVerification"
                               class="form-control input-md"
                               placeholder="<spring:message code='passwordVerification'/>">
                        <span class="glyphicon glyphicon-ok form-control-feedback with-success"
                              aria-hidden="true"></span>
                        <span id="inputSuccess2Status" class="sr-only">(success)</span>
                    </div>

                </div>
            </div>
        </section>

        <section>
            <div class="container">
                <div class="row">

                    <label class="col-sm-2 control-label" for="user-email">
                        <spring:message code="label.user.email"/>
                    </label>

                    <div class="col-xs-3 bottom-form-group">
                        <div class="input-group">
                            <span class="input-group-addon" style="padding-top: 2px; padding-bottom: 2px;">@</span>
                            <input type="text" id="user-email" name="email" class="form-control input-md"
                                   placeholder="<spring:message code='emailPlaceholder'/>">
                            <form:errors id="error-email" path="email" cssClass="help-block input-md"/>
                        </div>
                    </div>

                </div>
            </div>
        </section>

        <section>
            <div class="container">
                <div class="row">

                    <label class="col-sm-2 control-label" for="user-mobile">
                        <spring:message code="label.user.mobileNumber"/>
                    </label>

                    <div class="col-xs-3 bottom-form-group">
                        <input type="text" id="user-mobile" name="mobileNumber" class="form-control input-md">
                        <form:errors id="error-mobileNumber" path="mobileNumber" cssClass="help-block input-md"/>
                    </div>

                </div>
            </div>
        </section>


        <section>
            <div class="container">
                <div class="row">
                    <fieldset>
                        <legend><spring:message code="common.address"/></legend>
                    </fieldset>
                </div>
            </div>
        </section>

        <section>
            <div class="container">
                <div class="row">

                    <label class="col-sm-2 control-label">
                        <spring:message code="label.user.mainAddress"/>
                    </label>

                    <div class="col-xs-2">
                        <select name="country">
                            <option value="mr" selected><spring:message code='country.bulgaria'/></option>
                        </select>
                    </div>

                    <div class="col-xs-3">
                        <input type="text" id="user-city" name="city" class="form-control input-md"
                               placeholder="<spring:message code='label.user.city'/>">
                        <form:errors id="error-city" path="city" cssClass="help-block input-md"/>
                    </div>

                    <div class="col-xs-2 bottom-form-group">
                        <input type="text" id="user-postcode" name="postcode" class="form-control input-md"
                               placeholder="<spring:message code='label.user.postcode'/>">
                        <form:errors id="error-postcode" path="postcode" cssClass="help-block input-md"/>
                    </div>

                </div>
            </div>
        </section>

        <section>
            <div class="container">
                <div class="row">

                    <label class="col-sm-2 control-label" for="user-street1">
                        <spring:message code="label.street1"/>
                    </label>

                    <div class="col-xs-5 bottom-form-group">
                        <input type="text" id="user-street1" name="street1" class="form-control input-md"
                               placeholder="<spring:message code='label.street1.descr'/>">
                        <form:errors id="error-street1" path="street1" cssClass="help-block input-md"/>
                    </div>

                </div>
            </div>
        </section>

        <section>
            <div class="container">
                <div class="row">

                    <label class="col-sm-2 control-label" for="user-street2">

                    </label>

                    <div class="col-xs-5 bottom-form-group">
                        <input type="text" id="user-street2" name="street2" class="form-control input-md"
                               placeholder="<spring:message code='label.street1.descr2'/>">
                        <form:errors id="error-street2" path="street2" cssClass="help-block input-md"/>
                    </div>

                </div>
            </div>
        </section>

        <section>
            <div class="container">

                <div class="row">
                    <div class="col-sm-2"></div>

                    <button type="submit" class="btn btn-default"><spring:message
                            code="label.user.registration.submit.button"/></button>

                    <div style="height: 1vmax"></div>
                </div>
            </div>
        </section>

    </form:form>

</sec:authorize>

</body>
</html>