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

<div data-bind="with: auctionModel().getAuction(${auction.id})">
    <div class="box"></div>
    <section class="aligned">
        <div class="container">
            <div class="row">
                <div class="col-xs-12">
                    <div class="breadcrumbs">
                        <ul>
                            <li class="page"><a href="#">Начало</a></li>
                            <li class="divider">&gt;</li>
                            <li class="page"><a href="#">Активни търгове</a></li>
                            <li class="divider">&gt;</li>
                            <li class="page">Wacom Intuos Pen &amp; Touch</li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-2">
                    <ul id="gallery-images">
                        <c:forEach items="${auction.itemInventory.item.pictures}" var="picture">
                            <li class="rounded-image"><img src="/madbid-web/image/id/${picture.id}"/></li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="col-xs-4">
                    <img data-bind="attr: { src: pictureUrl }" class="promo-image-single" alt="">
                </div>
                <div class="col-xs-5 col-xs-offset-1">
                    <h3><span data-bind="text: itemName"></span></h3>

                    <div class="specifications">
                        <ul>
                            <li class="timer" data-bind="text: timerFull">86:06:59</li>
                            <li class="divider">|</li>
                            <li class="coins">x<span data-bind="text: creditsStep"></span></li>
                        </ul>
                    </div>

                    <ul class="prod">
                        <li>Приключва на: 21.11.2014, 22:30 GMT+2</li>
                        <li>Часове за наддаване: 14:00 - 21:00 <a href="">Напомни ми</a></li>
                        <li>Последно наддал: <a href="#"><span data-bind="text: lastBidder"></span></a></li>
                    </ul>

                    <div class="auction-price">
                        <p>Търг:
                            <span class="price">
                                <strong data-bind="text: currentBiddingsPriceCore"></strong>
                                <sup data-bind="text: currentBiddingsPriceSub"></sup>
                            </span>
                            <span class="vault">лв.</span>
                            <a href="#" class="button-yellow" data-bind="click: $root.auctionModel().bid">Наддавай</a>
                        </p>
                    </div>

                    <a href="" class="auto">Автоматично наддаване</a>

                    <span class="light-grey">Не искаш да чакаш?</span>
                    <a href="#" class="button-green-small">Купи веднага</a>
                    <span class="dark-grey-price">Цена:</span>
                    <span data-bind="text: buyNowPrice"></span>
                    <span data-bind="text: itemCurrencyLoc"></span>
                </div>
            </div>
        </div>
    </section>

    <section class="grey">
        <div class="container">
            <div class="row">
                <div class="col-xs-4">
                    <ul class="categories">
                        <li><a href="" class="active">Характеристики</a></li>
                        <li><a href="">Доставка</a></li>
                    </ul>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    <div class="description" data-bind="html: itemLongDescription">

                    </div>
                </div>
            </div>
        </div>
    </section>

   <%-- <section id="all-auctions" class="products">
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
                                <a href="#" class="button-bid" data-bind="click: $root.auctionModel().bid">
                                    Наддавай </a>
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
    </section>--%>

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
                                <img src="img/Hp.png" alt="">
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
                                <img src="img/Canon-Printer.png" alt="">
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
                                <img src="img/Intuos-Tablet.png" alt="">
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
                                <img src="img/Apple-iWatch.png" alt="">
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
        </div>
    </section>--%>

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
                    <form method="" action="" class="form-inline" role="form">
                        <div class="form-group">
                            <div class="input-group">
                                <label class="sr-only" for="exampleInputEmail2">Email address</label>
                                <input type="email" class="form-control input-md" style="margin-left: -5px;"
                                       id="exampleInputEmail2" placeholder="E-mail адрес">
                            </div>
                        </div>
                        <button type="submit" class="button-green">Запиши ме</button>
                    </form>
                </div>
            </div>
        </div>
    </section>

    <script>

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

        };

    </script>

</div>
</body>
</html>