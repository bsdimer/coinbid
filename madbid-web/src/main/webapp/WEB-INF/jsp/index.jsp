<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
</head>
<body>


<section>
    <div class="box"></div>
    <div class="container">
        <!-- Start last auction view -->
        <div class="row" data-bind="with: auctionModel().getLastAuction()">
            <div class="col-xs-1 hidden-xs">
                <small>Остават</small>
                <br>
                <span class="btn btn-dark" data-bind="text: timerFull"></span>
            </div>
            <div class="col-xs-1" style="margin-right: 0px;" data-bind="click: onClickAuction">
                <a href="#" data-bind="click: onClickAuction">
                    <img data-bind="attr: { src: pictureUrl }" class="promo-image" alt="" width="510" height="340">
                </a>

                <div class="stamp">
                    <span>-98%</span>
                    <img src="${pageContext.request.contextPath}/img/Stamp.png" alt="">
                </div>
            </div>
            <div class="col-xs-5 col-xs-offset-5">

                <h3><a href="#" class="product-name" data-bind="click: onClickAuction"><span
                        data-bind="text: itemName"></span></a></h3>

                <br>

                <div class="Specification" data-bind="html: itemDescription"></div>

                <span class="Stock-price">Продажна цена: </span>
                <span class="Stock-price" data-bind="text: marketPrice"></span>
                <span class="Stock-price" data-bind="text: marketPriceCurrencyLoc"></span>
                <br>
                <span><spring:message code="label.lastBidder"/></span>
                <span style="font-weight: bold" data-bind="text: lastBidder"></span>

                <%--<div class="current-price">
                    <span>Вземи го за</span><br>
                    <span class="price">
                        <strong data-bind="text: currentBiddingsPriceCore">
                            &lt;%&ndash;<sup> data-bind="text: currentBiddingsPriceSub"></sup>&ndash;%&gt;
                        </strong>
                    </span>
                    <span class="vault" data-bind="text: itemCurrencyLoc"></span>
                </div>--%>

                <div class="current-price">
                    <span>Вземи го за</span><br>
                                    <span class="price">
                                            <strong data-bind="text: currentBiddingsPriceCore"></strong>
                                        <sup data-bind="text: currentBiddingsPriceSub"></sup>
                                    </span>
                    <span class="vault" data-bind="text: itemCurrencyLoc"></span>
                </div>

                <div class="outbid">
                    <!-- <a href="#" class="bid"><span>Наддавай</span></a><br> -->
                    <a href="#" class="button-yellow" data-bind="click: $root.auctionModel().bid">Наддавай</a>

                    <div class="box"></div>
                </div>

                <a href="#" class="promo-outbuy" data-bind="click: onAuctionBuyNowAuction">
                    Купи веднага за
                    <span data-bind="text: buyNowPrice"></span>
                    <span data-bind="text: itemCurrencyLoc"></span>
                </a>


            </div>
        </div>

        <!-- End last auction view -->

        <div class="box"></div>
    </div>
</section>


<%--<sec:authorize access="isAnonymous()">--%>
<%--<section class="highlight">--%>
<%--<div class="container">--%>
<%--<div class="row">--%>
<%--<div class="center-block">--%>
<%--<div class="box-small"></div>--%>
<%--<h1>Включи се в наддаванията! Купувай на най-ниска цена</h1>--%>
<%--<h5>Регистрирай се за безплатен акаунт и започни да наддаваш</h5>--%>
<%--<a href="#" class="not-default">--%>
<%--<img src="${pageContext.request.contextPath}/img/Facebook-Reg.png" class="register" alt="">--%>
<%--</a>--%>
<%--<a href="#" class="not-default">--%>
<%--<img src="${pageContext.request.contextPath}/img/GooglePlus-Reg.png" class="spacing register"--%>
<%--alt="">--%>
<%--</a>--%>
<%--<br><br>--%>
<%--<h6>Регистрирайки се за акаунт в Жълтица.com се съгласяваш с нашите--%>
<%--<a href="" style="text-decoration: underline;">Общи условия</a>.</h6>--%>

<%--<div class="box"></div>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--</section>--%>
<%--</sec:authorize>--%>


<section class="highlight hidden-xs">
    <div class="container">
        <div class="box-small"></div>
        <div class="row">
            <div class="col-xs-12">
                <h1>Последните ни победители</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-3">
                <div class="winner">
                    <div class="thumb">
                        <img src="http://dummyimage.com/200x220" alt="">
                    </div>
                    <div class="prize">
                        <a href="#">
                            <img src="${pageContext.request.contextPath}/img/Hp.png" alt="">
                        </a>
                        <span class="price">781<sup>11</sup> лв.</span>
                    </div>
                    <h5>Деси, Благоевград</h5>
                    <a href="#">
                        <h5>Лаптоп HP G62 Series</h5>
                    </a>
                </div>
            </div>
            <div class="col-xs-3 col-xs-offset-1">
                <div class="winner">
                    <div class="thumb">
                        <img src="http://dummyimage.com/200x220" alt="">
                    </div>
                    <div class="prize">
                        <a href="#">
                            <img src="${pageContext.request.contextPath}/img/Casio-Exilim.png" alt="">
                        </a>
                        <span class="price">45<sup>00</sup> лв.</span>
                    </div>
                    <h5>Ники, Бургас</h5>
                    <a href="#">
                        <h5>Casio Exilim, 18 mp</h5>
                    </a>
                </div>
            </div>
            <div class="col-xs-3 col-xs-offset-1">
                <div class="winner">
                    <div class="thumb">
                        <img src="http://dummyimage.com/200x220" alt="">
                    </div>
                    <div class="prize">
                        <a href="#">
                            <img src="${pageContext.request.contextPath}/img/Intuos-Tablet.png" alt="">
                        </a>
                        <span class="price">45<sup>00</sup> лв.</span>
                    </div>
                    <h5>Ивана, Силистра</h5>
                    <a href="#">
                        <h5>Intuos Pen &amp; Touch</h5>
                    </a>
                </div>
            </div>
        </div>
        <div class="box"></div>
        <img src="${pageContext.request.contextPath}/img/Coins/8.png" data-stellar-ratio="2.0"
             data-stellar-vertical-offset="100"
             style="position: absolute; top: 100px; left: 600px;" alt="">
        <img src="${pageContext.request.contextPath}/img/Coins/9.png" data-stellar-ratio="1.3"
             data-stellar-vertical-offset="200"
             style="position: absolute; top: 120px; left: 630px;" alt="">
        <img src="${pageContext.request.contextPath}/img/Coins/10.png" data-stellar-ratio="0.7"
             data-stellar-vertical-offset="-50"
             style="position: absolute; bottom: 100px; left: 100px;" alt="">
        <img src="${pageContext.request.contextPath}/img/Coins/10.png" data-stellar-ratio="1.3"
             data-stellar-vertical-offset="150"
             style="position: absolute; bottom: 30px; right: 400px;" alt="">
    </div>
</section>


<section id="all-auctions" class="products">
    <div class="container">
        <!-- <div class="box-small"></div> -->
        <div class="row">
            <div class="col-xs-12">
                <h1>Наддавай за тези продукти</h1>
            </div>
        </div>
        <div class="row">

            <div data-bind="foreach: auctionModel().auctions">
                <div class="col-xs-3">
                    <div class="product">
                        <div class="col-xs-9 col-xs-offset-1">
                            <div class="thumb">
                                <a href="#" data-bind="click: onClickAuction">
                                    <img data-bind="attr: { src: pictureUrl }" alt="" width="194" height="137">
                                </a>
                            </div>
                            <hr class="top">
                            <div class="parameters">
                                <ul>
                                    <li class="timer" data-bind="text: timerFull">86:06:59</li>
                                    <li class="coins">x<span data-bind="text: creditsStep"></span></li>
                                </ul>
                            </div>
                            <hr class="bottom">
                            <br>
                        </div>

                        <div class="specs">
                            <a href="#" class="product-name" data-bind="click: onClickAuction">
                                <h4><span data-bind="text: itemName"></span></h4>
                            </a>
                            <span><spring:message code="label.lastBidder"/></span>
                            <span style="font-weight: bold" data-bind="text: lastBidder"></span><br>
                                <span class="price">
                                            <strong data-bind="text: currentBiddingsPriceCore"></strong>
                                        <sup data-bind="text: currentBiddingsPriceSub"></sup>
                                    </span>
                            <span class="price" data-bind="text: itemCurrencyLoc"></span><br>
                            <span class="old-price">Продажна цена </span>
                            <span class="old-price" data-bind="text: marketPrice"></span>
                            <span class="old-price" data-bind="text: marketPriceCurrencyLoc"></span>
                            <br><br>
                            <a href="#" class="button-bid" data-bind="click: $root.auctionModel().bid"> Наддавай </a>
                            <br><br>

                            <div class="boxed-spacer"></div>
                            <a href="#" class="outbuy" data-bind="click: onAuctionBuyNowAuction">
                                Купи веднага за
                                <span data-bind="text: buyNowPrice"></span>
                                <span data-bind="text: itemCurrencyLoc"></span>
                            </a>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <div class="box"></div>
    </div>
</section>

<section id="works" class="highlight hidden-xs" data-stellar-background-ratio="1.5">
    <div class="container">
        <div class="box-small"></div>
        <div class="row">
            <div class="col-xs-12">
                <div class="center-block">
                    <h1>Как работи Жълтица.com?</h1>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <div class="how-it-works center-block">
                    <div class="col-xs-6 left-max">
                        <div style="height: 120px;">
                            <img src="${pageContext.request.contextPath}/img/Facebook-Login.png" alt="">
                        </div>
                        <h4>Регистирай се. Безплатно е!</h4>

                        <p>Необходим ти е само Facebook или Google+ акаунт.</p>
                    </div>
                    <div class="col-xs-6 in-the-middle">
                        <div style="height: 120px;">
                            <img src="${pageContext.request.contextPath}/img/Jultica-Buy.png" alt="">
                        </div>
                        <h4>Купи си жълтици и наддавай</h4>

                        <p>Избери си стратегия и започни да наддаваш.</p>
                    </div>
                    <div class="col-xs-6 right-max">
                        <div style="height: 120px;">
                            <img src="${pageContext.request.contextPath}/img/Laptop-buy.png" alt="">
                        </div>
                        <h4>Печели търгове</h4>

                        <p>Взимай продукти на цена до 99% по-ниска от пазарната. </p>
                    </div>
                </div>
            </div>
        </div>
        <div class="box"></div>
        <img src="${pageContext.request.contextPath}/img/Coins/1.png" data-stellar-ratio="1.4"
             data-stellar-vertical-offset="300"
             style="position: absolute; top: 100px; left: 800px;" alt="">
        <img src="${pageContext.request.contextPath}/img/Coins/2.png" data-stellar-ratio="2.5"
             data-stellar-vertical-offset="-152"
             style="position: absolute; top: 100px; right: 200px;" alt="">
    </div>
</section>

<%--<section class="auctions">
    <div class="container">
        <div class="row">
            <div class="col-xs-12">
                <h1>Предстоящи търгове</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-3">
                <div class="product">
                    <div class="col-xs-9 col-xs-offset-1">
                        <div class="thumb">
                            <a href="#">
                                <img src="${pageContext.request.contextPath}/img/Hp.png" alt="">
                            </a>
                        </div>
                        <hr class="top">
                        <div class="parameters">
                            <ul>
                                <li class="timer fix" style="margin-left: 0;">00:52:24</li>
                            </ul>
                        </div>
                        <hr class="bottom">
                        <br>
                    </div>

                    <div class="specs">
                        <a href="#" class="product-name">
                            <h4>Лаптоп HP G62 Series, <br>15.6 inch, 12 Gb RAM</h4>
                        </a>
                    </div>
                </div>
            </div>
            <div class="col-xs-3">
                <div class="product">
                    <div class="col-xs-9 col-xs-offset-1">
                        <div class="thumb">
                            <a href="#">
                                <img src="${pageContext.request.contextPath}/img/Canon-Printer.png" alt="">
                            </a>
                        </div>
                        <hr class="top">
                        <div class="parameters">
                            <ul>
                                <li class="timer fix" style="margin-left: 0;">06:18:02</li>
                            </ul>
                        </div>
                        <hr class="bottom">
                        <br>
                    </div>

                    <div class="specs">
                        <a href="#" class="product-name">
                            <h4>Мастилноструйно устройство <br> 3в1, Canon Pixima</h4>
                        </a>
                    </div>
                </div>
            </div>
            <div class="col-xs-3">
                <div class="product">
                    <div class="col-xs-9 col-xs-offset-1">
                        <div class="thumb">
                            <a href="#">
                                <img src="${pageContext.request.contextPath}/img/Intuos-Tablet.png" alt="">
                            </a>
                        </div>
                        <hr class="top">
                        <div class="parameters">
                            <ul>
                                <li class="timer fix" style="margin-left: 0;">45:23:36</li>
                            </ul>
                        </div>
                        <hr class="bottom">
                        <br>
                    </div>

                    <div class="specs">
                        <a href="#" class="product-name">
                            <h4>Wacom Intuos Pen t&amp; Touch Medium</h4>
                        </a>
                    </div>
                </div>
            </div>
            <div class="col-xs-3">
                <div class="product">
                    <div class="col-xs-9 col-xs-offset-1">
                        <div class="thumb">
                            <a href="#">
                                <img src="${pageContext.request.contextPath}/img/Apple-iWatch.png" alt="">
                            </a>
                        </div>
                        <hr class="top">
                        <div class="parameters">
                            <ul>
                                <li class="timer fix" style="margin-left: 0;">01:18:04</li>
                            </ul>
                        </div>
                        <hr class="bottom">
                        <br>
                    </div>

                    <div class="specs">
                        <a href="#" class="product-name">
                            <h4>iWatch, златен</h4>
                        </a>
                    </div>
                </div>
            </div>
        </div>
        <img src="${pageContext.request.contextPath}/img/Coins/3.png" data-stellar-ratio="2.0"
             data-stellar-vertical-offset="100"
             style="position: absolute; top: 200px; left: 300px;" alt="">
        <img src="${pageContext.request.contextPath}/img/Coins/4.png" data-stellar-ratio="2.0"
             data-stellar-vertical-offset="-200"
             style="position: absolute; top: 0px; right: 400px;" alt="">
        <img src="${pageContext.request.contextPath}/img/Coins/5.png" data-stellar-ratio="0.4"
             data-stellar-vertical-offset="300"
             style="position: absolute; top: 150px; right: 100px;" alt="">
        <img src="${pageContext.request.contextPath}/img/Coins/6.png" data-stellar-ratio="1.4"
             data-stellar-vertical-offset="-87"
             style="position: absolute; top: 180px; left: 100px;" alt="">
        <img src="${pageContext.request.contextPath}/img/Coins/7.png" data-stellar-ratio="1.8"
             data-stellar-vertical-offset="-83"
             style="position: absolute; top: 190px; left: 120px;" alt="">
    </div>
</section>--%>

<section class="auctions">
    <div class="container">
        <div class="row">
            <div class="col-xs-12">
                <h1>Предстоящи търгове</h1>
            </div>
        </div>
        <div class="row" data-bind="foreach: auctionModel().upcomingAuctions">

            <div class="col-xs-3">
                <div class="product">
                    <div class="col-xs-9 col-xs-offset-1">
                        <div class="thumb">
                            <img data-bind="attr: { src: pictureUrl }" alt="" width="194" height="137">
                        </div>
                        <hr class="top">
                        <div class="parameters">
                            <ul>
                                <li class="timer fix" style="margin-left: 0;" data-bind="text: upcomfingTimerFull">
                                    00:52:24
                                </li>
                            </ul>
                        </div>
                        <hr class="bottom">
                        <br>
                    </div>

                    <div class="specs">
                        <a href="#" class="product-name" data-bind="click: onClickAuction">
                            <h4 data-bind="text: itemName"></h4>
                        </a>
                    </div>
                </div>
            </div>

        </div>
    </div>
</section>

<sec:authorize access="isAuthenticated()">
    <section class="yellow">
        <div class="container">
            <div class="row">
                <div class="col-sx-12">
                    <h3>Никога вече не изпускай оферта!</h3>

                    <p>Запиши се и получавай всички нови оферти на мейла си</p>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    <form id="subscribe-form" method="POST"
                          action="${pageContext.request.contextPath}/notification/subscribe"
                          class="form-inline" role="form">
                        <div class="form-group">
                            <div class="input-group">
                                <label class="sr-only" for="email">Email address</label>
                                <input type="email" class="form-control input-md" style="margin-left: -5px;"
                                       id="email" name="email" placeholder="E-mail адрес">
                            </div>
                        </div>
                        <button type="submit" onclick="subscribeEmail()" class="button-green">Запиши ме</button>
                    </form>
                    <div id="subscribe-successfull-message" style="display:none;" class="col-xs-9 col-xs-offset-2">
                        <div class="alert alert-success alert-dismissible" role="alert">
                            <button type="button" onclick="showSubscribeSuccessMessage(false);" class="close">
                                <span>×</span><span class="sr-only">Close</span></button>
                            <div class="col-xs-1 pull-left">
                                <i class="glyphicon glyphicon-ok-circle"></i>
                            </div>
                            <div class="col-xs-10">
                                <h5 class="general">Вие успешно се абонирахте</h5>
                            </div>
                            &nbsp;
                        </div>
                    </div>
                    <div id="subscribe-error-message" class="col-xs-9 col-xs-offset-2" style="display:none;">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" onclick="showSubscribeErrorMessage(false);" class="close">
                                <span>×</span><span class="sr-only">Close</span></button>
                            <div class="col-xs-1 pull-left">
                                <i class="glyphicon glyphicon-remove-circle"></i>
                            </div>
                            <div class="col-xs-10">
                                <h5 class="general">Възника проблем по време на абонирането</h5>
                            </div>
                            &nbsp;
                        </div>
                    </div>
                </div>
            </div>
            <img src="${pageContext.request.contextPath}/img/Coins/12.png" data-stellar-ratio="0.8"
                 data-stellar-vertical-offset="-100"
                 style="position: absolute; top: 100px; left: 400px;" alt="">
            <img src="${pageContext.request.contextPath}/img/Coins/12.png" data-stellar-ratio="0.8"
                 data-stellar-vertical-offset="-100"
                 style="position: absolute; top: 200px; right: 300px;" alt="">
        </div>
    </section>
</sec:authorize>

<script>

    var subscribeEmail = function () {
        $('#subscribe-form').unbind("submit").submit(function (event) {
            event.preventDefault();
            var email = $('#email').val();
            $.ajax({
                url: "notification/subscribe?email=" + email,
                type: "POST",
                dataType: 'json',
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    if (data.subscribe) {
                        showSubscribeSuccessMessage(true);
                    } else {
                        showSubscribeErrorMessage(true);
                    }
                },
                error: function (data) {
                    showSubscribeErrorMessage(true);
                }
            });
        });
    };

    var showSubscribeErrorMessage = function (show) {
        if (show) {
            $("#subscribe-error-message").show();
        } else {
            $("#subscribe-error-message").hide();
        }
    };

    var showSubscribeSuccessMessage = function (show) {
        if (show) {
            $("#subscribe-successfull-message").show();
        } else {
            $("#subscribe-successfull-message").hide();
        }
    };

    // Dimer: This init() function should be used in each view to execute login on DOM ready event
    var init = function () {

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });

        try {
            var socket = new SockJS('/madbid-web/stompEndpoint');
            var stompClient = Stomp.over(socket);

            var appModel = new ApplicationModel(stompClient);
            ko.applyBindings(appModel);

            appModel.connect();
            appModel.uiManager.onWin(function (auction) {
                alert("auction win");
            });
        } catch (e) {
        }

    };

</script>

</body>
</html>