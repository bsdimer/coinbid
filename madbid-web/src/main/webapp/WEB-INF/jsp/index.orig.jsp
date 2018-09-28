<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <div data-bind="click: onClickAuction" style="cursor: pointer;">
                <img data-bind="attr: { src: pictureUrl }" width="100" height="100"/>
            </div>
            <button data-bind="click: $root.auctionModel().bid">+<span data-bind="text: creditsStep"></span>
            </button>
            <button data-bind="click: onAuctionBuyNowAuction">
                Buy now: <span data-bind="text: fullBuyNowPrice"></span>
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

</body>
</html>