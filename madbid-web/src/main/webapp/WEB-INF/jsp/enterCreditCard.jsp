<%@ page import="java.util.ArrayList" %>
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

<form:form method="post" action="${pageContext.request.contextPath}/submitCreditCard" modelAttribute="creditCardEntity">

    <div class="panel panel-default">
        <div class="panel-body">

            <div class="row">
                <div id="form-group-creditCardType" class="form-group col-lg-1">
                    <label class="control-label" for="creditCardType"><spring:message
                            code="label.creditCard.type"/>:</label>
                    <form:select id="creditCardType" path="creditCardType">
                        <option value="Select" label="Select a card type"></option>
                        <form:options items="${creditCardTypes}" itemValue="value" itemLabel="value"/>
                    </form:select>
                    <form:errors id="error-creditCardType" path="creditCardType" cssClass="help-block"/>
                </div>
            </div>

            <div class="row">
                <div id="form-group-creditCardNumber" class="form-group col-lg-1">
                    <label class="control-label" for="creditCardNumber"><spring:message
                            code="label.creditCard.number"/>:</label>
                    <form:input id="creditCardNumber" path="creditCardNumber"/>
                    <form:errors id="error-creditCardNumber" path="creditCardNumber" cssClass="help-block"/>
                </div>
            </div>

            <label class="control-label" for="creditCardExpireMonth"><spring:message
                    code="label.creditCard.expireDate"/>:</label>

            <div class="row">
                <div id="form-group-expireMonth" class="form-group col-lg-1">
                    <form:select id="creditCardExpireMonth" path="expireMonth">
                        <option value="Select" label="-- expire month --"></option>
                        <form:options items="${months}"/>
                    </form:select>
                </div>
                <div id="form-group-expireYear" class="form-group col-lg-1">
                    <form:select id="creditCardExpireYear" path="expireYear">
                        <option value="Select" label="-- expire year --"></option>
                        <form:options items="${years}"/>
                    </form:select>
                </div>
            </div>

            <div class="row">
                <div id="form-group-ccv2" class="form-group col-lg-1">
                    <label class="control-label" for="ccv2">ccv2:</label>
                    <form:input id="ccv2" path="ccv2" maxlength="3" onkeypress="return isNumberKey(event)"/>
                    <form:errors id="error-ccv2" path="ccv2" cssClass="help-block"/>
                </div>
            </div>

            <div class="row">
                <div id="form-group-cardHolder" class="form-group col-lg-1">
                    <label class="control-label" for="cardHolder">
                        <spring:message code="label.creditCard.cardHolder"/>:</label>
                    <form:input id="cardHolder" path="cardHolder" maxlength="128"/>
                    <form:errors id="error-cardHolder" path="cardHolder" cssClass="help-block"/>
                </div>
            </div>

        </div>
    </div>
    <input type="submit" value="Submit">

</form:form>
<script type="text/javascript">

    function isNumberKey(evt) {
        var charCode = (evt.which) ? evt.which : event.keyCode;
        if (charCode > 31 && (charCode < 48 || charCode > 57))
            return false;

        return true;
    }

</script>

</body>
</html>