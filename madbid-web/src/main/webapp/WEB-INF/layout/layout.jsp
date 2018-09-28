<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Жълтица.com</title>

    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <!-- Typekit -->
    <script src="http://use.typekit.net/wht4ymq.js"></script>
    <script>try {
        Typekit.load();
    } catch (e) {
    }</script>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/nav.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/fixes.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/icons.css">
    <link href="${pageContext.request.contextPath}/iCheck-1.x/skins/flat/yellow.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

    <script src="${pageContext.request.contextPath}/iCheck-1.x/icheck.js"></script>
    <script src="${pageContext.request.contextPath}/js/vendor/knockout/knockout.js"></script>
    <script src="${pageContext.request.contextPath}/js/vendor/stomp/lib/stomp.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/vendor/sockjs/sockjs.min.js"></script>

    <!-- common js logic -->
    <script src="${pageContext.request.contextPath}/js/app/auctions.js"></script>
    <script src="${pageContext.request.contextPath}/js/app/commons.js"></script>

    <!-- Start head part -->
    <sitemesh:write property="head"/>
    <!-- Ends head part -->
</head>
<body>

<form id="logoutForm" action="${pageContext.request.contextPath}/logout" method="POST">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<sec:authorize access="isAnonymous()">
    <header>
        <nav class="navbar navbar navbar-default navbar-custom" style="z-index: 1030;" role="navigation">
            <div class="container">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                            data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">
                        <img src="${pageContext.request.contextPath}/img/Jultica.png" id="brand"
                             class="visible-lg visible-md" alt="">
                            <%--<img src="${pageContext.request.contextPath}/img/Jultica-small.png" id="brand" class="visible-sm visible-xs" alt="">--%>
                    </a>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav pull-right">
                        <li><a href="#">Купи жълтици</a></li>
                        <li><a href="${pageContext.request.contextPath}/login">Влез</a></li>
                        <li><a href="${pageContext.request.contextPath}/user/register">Регистрирай се</a></li>
                        <li><a href="#"><img src="${pageContext.request.contextPath}/img/Mute.png" alt=""></a></li>
                    </ul>
                </div>
                <!-- /.navbar-collapse -->
            </div>
            <!-- /.container-fluid -->
        </nav>

        <div class="clearfix"></div>

        <div class="container" style="margin-top: -67px;">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-4">
                    <ul class="main pull-right">
                        <li><a href="#">Активни търгове</a></li>
                        <li><a href="#">Как работи Жълтица?</a></li>
                        <li><a href="${pageContext.request.contextPath}/faq">Въпроси и отговори</a></li>
                        <li><a href="${pageContext.request.contextPath}/auth/facebook">
                        <img src="${pageContext.request.contextPath}/img/Facebook.png" class="register"
                             alt=""></a></li>
                        <li><a href="${pageContext.request.contextPath}/auth/google">
                            <img src="${pageContext.request.contextPath}/img/GooglePlus-Social.png" class="register"
                                 alt=""></a></li>
                    </ul>
                </div>
            </div>
        </div>
    </header>
</sec:authorize>

<sec:authorize access="isAuthenticated()">
    <nav class="navbar navbar navbar-default navbar-fixed-top navbar-custom" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${pageContext.request.contextPath}/">
                    <img src="${pageContext.request.contextPath}/img/Jultica.png" id="brand"
                         class="visible-lg visible-md" alt="">
                    <img src="${pageContext.request.contextPath}/img/Jultica-small.png" id="brand"
                         class="visible-sm visible-xs" alt="">
                </a>

                <div class="clearfix"></div>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-Qnavbar-collapse-1">
                <ul class="nav navbar-nav pull-right">
                    <li><a href="${pageContext.request.contextPath}/user/userPanel">Профил</a></li>
                    <li><a href="#">Търгове</a></li>
                    <li><a href="#">Купи жълтици</a></li>
                    <li class="larger"><a href="#"><img src="${pageContext.request.contextPath}/img/Avatar.png" alt=""
                                                        class="avatar"></a></li>
                    <li><a href="#"><sec:authentication property="principal.username"/></a></li>
                    <li><a href="javascript:document.forms['logoutForm'].submit()"><u>Изход</u></a></li>
                    <li><a href="#"><img src="${pageContext.request.contextPath}/img/Mute.png" alt=""></a></li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>


    <header>
        <div class="container">
            <div class="row">
                <div class="col-xs-10 col-xs-offset-2">
                    <ul class="main pull-right">
                        <li><a href="#all-auctions">Активни търгове</a></li>
                        <li><a href="#works">Как работи?</a></li>
                        <li><a href="${pageContext.request.contextPath}/faq">Въпроси и отговори</a></li>
                        <li class="user-coins pull-right">
                            <img src="${pageContext.request.contextPath}/img/Coin-menu.png" alt="">
                            <h6 class="pull-right">
                                <span data-bind="text: userModel().coinsAvailable"></span> жълтици <br>
                                <span><a href="#">Купи още</a></span>
                            </h6>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </header>
</sec:authorize>

<!-- Start body part -->
<sitemesh:write property="body"/>
<!-- Ends body part -->

<footer>
    <div class="container">
        <div class="row">
            <div class="col-xs-12" style="margin-top: 30px;">
                <ul>
                    <li><a href="#"><img src="${pageContext.request.contextPath}/img/Facebook-Social.png" class="soc"
                                         data-element="1" alt=""></a></li>
                    <li><a href="#"><img src="${pageContext.request.contextPath}/img/GooglePlus-Social.png" class="soc"
                                         data-element="2" alt=""></a></li>
                    <li><a href="#"><img src="${pageContext.request.contextPath}/img/Twitter-Social.png" class="soc"
                                         data-element="3" alt=""></a></li>
                    <li class="pull-right"><a href="#"><img src="${pageContext.request.contextPath}/img/Up.png"
                                                            onmouseover="this.src='img/social/Up-Hover.png'"
                                                            onmouseout="this.src='img/Up.png'" data-element="4" alt=""></a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <ul class="contacts">
                    <li><a href="#">Регистрация</a></li>
                    <li><a href="#">Помощ</a></li>
                    <li><a href="#">Контакти</a></li>
                    <li><a href="#">Общи условия</a></li>
                    <li><a href="#">За Жълтица.com</a></li>
                </ul>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12">
                <ul>
                    <li><a href="#"><img src="${pageContext.request.contextPath}/img/ePay.png" alt=""></a></li>
                    <li><a href="#"><img src="${pageContext.request.contextPath}/img/PayPal.png" alt=""></a></li>
                    <li><a href="#"><img src="${pageContext.request.contextPath}/img/Visa.png" alt=""></a></li>
                    <li><a href="#"><img src="${pageContext.request.contextPath}/img/VisaElectron.png" alt=""></a></li>
                    <li><a href="#"><img src="${pageContext.request.contextPath}/img/Maestro.png" alt=""></a></li>
                    <li><a href="#"><img src="${pageContext.request.contextPath}/img/Mastercard.png" alt=""></a></li>
                </ul>
            </div>
        </div>
    </div>
    <hr class="grey">
    <div class="container">
        <div class="row">
            <div class="col-xs-12">
                <p>Всички права запазени Жълтица.com 2014 | Уеб дизайн и разработка: <a href="#">Coggraphics</a></p>
            </div>
        </div>
    </div>
</footer>

<!-- jQuery 2.1.1.min -->
<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
<!-- Parallax -->
<script src="${pageContext.request.contextPath}/js/stellar.js-master/src/jquery.stellar.js"></script>
<script src="${pageContext.request.contextPath}/js/imakewebthings-jquery-waypoints-415eb55/waypoints.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.easing.1.3.js"></script>

<script>

    $(function () {

        var obj = $('.user-coins');
        try {
            var top = obj.offset().top - parseFloat(obj.css('marginTop').replace(/auto/, 0));
        } catch (e) {

        }

        $(window).scroll(function (event) {
            // what the y position of the scroll is
            var y = $(this).scrollTop();

            // whether that's below the form
            if (y >= top) {
                // if so, ad the fixed class
                obj.addClass('fixed');
                $("#brand").attr('src', '${pageContext.request.contextPath}/img/Jultica-small.png');
                $("#brand").css('marginTop', '-15px');
            } else {
                // otherwise remove it
                obj.removeClass('fixed');
                $("#brand").attr('src', '${pageContext.request.contextPath}/img/Jultica.png');
                $("#brand").css('marginTop', '0px');
            }
        });

        $('.soc').hover(function () {
            var Element = $(this).data('element');
            window.Current = $(this).attr('src');

            var States = {
                1: 'Facebook-Hover.png',
                2: 'GooglePlus-Hover.png',
                3: 'Twitter-Hover.png',
                4: 'Up-Hover.png'
            };

            $(this).attr('src', '${pageContext.request.contextPath}/img/social/' + States[Element]);
        }, function () {
            $(this).attr('src', window.Current);
        });

        $(window).stellar({horizontalScrolling: false, responsive: true});

        init();


    });

</script>


</body>
</html>