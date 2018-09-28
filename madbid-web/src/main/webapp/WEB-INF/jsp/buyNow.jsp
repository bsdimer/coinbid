<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fomr" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title></title>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
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


<form:form method="post" action="${pageContext.request.contextPath}/buyNow" modelAttribute="paymentEntity">

    <div>
        <div>
            <h3><spring:message code="common.address.shipping"/></h3>

            <c:set var="address" value="${paymentEntity.address}"/>

            <c:choose>
                <c:when test="${not empty paymentEntity.address}">
                    <div>
                        <div>
                            <span>${address.postcode}</span>
                        </div>

                        <div>
                            <span>${address.city}, ${address.country}</span>
                        </div>

                        <div>
                            <span>${address.street1}</span>
                        </div>

                        <c:if test="${not empty address.street2}">
                            <div>
                                <span>${address.street2}</span>
                            </div>
                        </c:if>

                        <div>
                            <input type="button" onclick="$('#changeAddress_dlg').puidialog('show');"
                                   value="<spring:message code="common.address.change"/>"/>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div>
                        <input type="button" onclick="$('#addAddress_dlg').puidialog('show');"
                               value="<spring:message code="common.address.add"/>"/>
                    </div>
                </c:otherwise>
            </c:choose>

            <h3><spring:message code="label.payment.paymentType"/></h3>

            <div>
                <div>
                    <form:radiobutton value="epay" path="paymentMethod"/><spring:message
                        code="label.payment.paymentType.epay"/>
                </div>
                <div>
                    <form:radiobutton value="paypal" path="paymentMethod"/><spring:message
                        code="label.payment.paymentType.paypal"/>
                </div>
                <div>
                    <form:radiobutton value="cc" path="paymentMethod"/><spring:message
                        code="label.payment.paymentType.cc"/>
                </div>
                <div>
                    <form:radiobutton value="pd" path="paymentMethod"/><spring:message
                        code="label.payment.paymentType.pd"/>
                </div>
                <div>
                    <form:radiobutton value="paypal_expressCheckout" path="paymentMethod"/><spring:message
                        code="label.payment.paymentType.expressCheckout"/>
                </div>
            </div>
        </div>
    </div>
    <%--<input id="addressId" type="hidden" name="addressId" value="${address.id}">--%>
    <input type="submit" value="Next"/>
</form:form>

<div id="changeAddress_dlg" title="<spring:message code="common.address.add"/>">
    <form:form id="changeAddressForm" action="${pageContext.request.contextPath}/buyNow/changeUserAddress"
               method="POST" modelAttribute="paymentEntity">
        <%--
                <form:select path="address" id="activeAddress_sb">
                    <form:option value="" label="Select title"/>
                    <form:options items="${addresses}" itemLabel="city"/>
                </form:select>
                <form:errors path="address" cssClass="error"/>--%>

        <label for="activeAddress_sb"></label>
        <select id="activeAddress_sb" name="selectedAddress">
            <c:forEach items="${paymentEntity.user.addresses}" var="addressItem">
                <option value="${addressItem.id}">${addressItem}</option>
            </c:forEach>
        </select>
        <input type="submit" value="<spring:message code="common.address.change"/>">
        <input type="button" onclick="$('#changeAddress_dlg').puidialog('hide');
               $('#addAddress_dlg').puidialog('show');" value="<spring:message code="common.address.add"/>"/>
    </form:form>
</div>

<div id="addAddress_dlg" title="<spring:message code="common.address.add"/>">
    <form:form id="addAddressForm" action="${pageContext.request.contextPath}/buyNow/addUserAddress"
               modelAttribute="paymentEntity" method="POST">
        <table>
                <%--<div>
                    <c:choose>
                        <c:when test="${fn:length(user.addresses) eq 0}">
                            <form:select path="shipping.address">
                                <form:option value="none">None</form:option>
                            </form:select>
                        </c:when>
                        <c:otherwise>
                            <form:select path="shipping.address" items="${user.addresses}"/>
                        </c:otherwise>
                    </c:choose>
                    <button title="Add">Add new address</button>
                </div>--%>
            <tr>
                <td>
                    <label for="address_country">Country:</label>
                </td>
                <td>
                    <input id="address_country" type="text" required="true"/>
                </td>
            </tr>

            <tr>
                <td>
                    <label for="address_state">State:</label>
                </td>
                <td>
                    <input id="address_state" type="text" required="true"/>
                </td>
            </tr>

            <tr>
                <td>
                    <label for="address_street1">Street1:</label>
                </td>
                <td>
                    <input id="address_street1" type="text" required="true"/>
                </td>
            </tr>

            <tr>
                <td>
                    <label for="address_street2">Street2:</label>
                </td>
                <td>
                    <input id="address_street2" type="text"/>
                </td>
            </tr>

            <tr>
                <td>
                    <label for="address_city">City/Town:</label>
                </td>
                <td>
                    <input id="address_city" type="text" required="true"/>
                </td>
            </tr>

            <tr>
                <td>
                    <label for="address_postcode">postcode:</label>
                </td>
                <td>
                    <input id="address_postcode" type="text" required="true"/>
                </td>
            </tr>

            <tr>
                <td>
                    <input type="submit" onclick="addAddress();" value="Add">
                </td>
            </tr>

            <tr>
                <td colspan="2">
                    <div id="result_div"></div>
                </td>
            </tr>
        </table>
    </form:form>
</div>

<script language="JavaScript">

    $(function () {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });

        $('#addAddress_dlg').puidialog({
            width: 700,
            showEffect: 'fade',
            hideEffect: 'fade',
            minimizable: true,
            maximizable: true,
            modal: true
        });

        $('#changeAddress_dlg').puidialog({
            width: 700,
            showEffect: 'fade',
            hideEffect: 'fade',
            minimizable: true,
            maximizable: true,
            modal: true
        });

    });

    var onAddressSelect = function () {
        var selectedAddress = $("#activeAddress_sb").select();
    };

    var addAddress = function () {
        var frm = $('#addAddressForm');
        frm.submit(function (event) {
            event.preventDefault();
            $.ajax({
                type: "POST",
                url: "addUserAddress",
                data: {
                    "country": $("#address_country").val(),
                    "state": $("#address_state").val(),
                    "city": $("#address_city").val(),
                    "street1": $("#address_street1").val(),
                    "street2": $("#address_street2").val(),
                    "postcode": $("#address_postcode").val()
                },
                success: function (data) {
                    if (data == "0") {
                        $("#result_div").text("Please fill the required fields");
                    } else {
                        $("#result_div").text("");
                        $('#addAddress_dlg').puidialog('hide');
                        location.reload();
                    }
                },
                error: function () {
                    $("#result_div").text("Error in submitting of address");
                }
            });
        });
    }
</script>

<!-- application JS -->
<script src="${pageContext.request.contextPath}/static/js/app/auctions.js"></script>
<script src="${pageContext.request.contextPath}/static/js/app/user.form.js"></script>

</body>
</html>