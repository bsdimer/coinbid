<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<%--<head>
    <meta charset="utf-8">
    <title>Жълтица.com</title>

    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">

    <!-- Typekit -->
    <script src="http://use.typekit.net/wht4ymq.js"></script>
    <script>try {
        Typekit.load();
    } catch (e) {
    }</script>

    <!-- Custom stylesheet -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/nav.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/fixes.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/icons.css">
    <link href="${pageContext.request.contextPath}/iCheck-1.x/skins/flat/yellow.css" rel="stylesheet">
    <!-- jQuery 2.1.1.min -->
    <script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/iCheck-1.x/icheck.js"></script>
    <script>
        $(document).ready(function () {
            $('input').iCheck({
                checkboxClass: 'icheckbox_flat-yellow',
                radioClass: 'iradio_flat-yellow'
            });
        });
    </script>
</head>--%>

<body>

<%--<header>
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
                    &lt;%&ndash;<img src="${pageContext.request.contextPath}/img/Jultica-small.png" id="brand" class="visible-sm visible-xs" alt="">&ndash;%&gt;
                </a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav pull-right">
                    <li><a href="#">Купи жълтици</a></li>
                    <li><a href="#">Влез</a></li>
                    <li><a href="#">Регистрирай се</a></li>
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
                    <li><a href="">Активни търгове</a></li>
                    <li><a href="">Как работи Жълтица?</a></li>
                    <li><a href="">Въпроси и отговори</a></li>
                    <li><a href=""><img src="${pageContext.request.contextPath}/img/Facebook.png" class="register"
                                        alt=""></a></li>
                </ul>
            </div>
        </div>
    </div>
</header>--%>


<section>
    <div class="container">
        <div class="box"></div>
        <div class="row">

            <c:if test="${param.error eq 'invalidCredentials'}">
                <div class="col-xs-9">
                    <div class="alert alert-danger alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span
                                class="sr-only">Close</span></button>
                        <div class="col-xs-1 pull-left">
                            <i class="glyphicon glyphicon-remove-circle"></i>
                        </div>
                        <div class="col-xs-10">
                            <h5 class="general"><spring:message code="message.oops"/>!</h5>
                            <spring:message code="login.error.invalid.credentials"/>
                        </div>
                        &nbsp;
                    </div>
                </div>
            </c:if>

            <c:if test="${param.error eq 'maximumExceeded'}">
                <div class="col-xs-9">
                    <div class="alert alert-danger alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span
                                class="sr-only">Close</span></button>
                        <div class="col-xs-1 pull-left">
                            <i class="glyphicon glyphicon-remove-circle"></i>
                        </div>
                        <div class="col-xs-10">
                            <h5 class="general"><spring:message code="message.oops"/>!</h5>
                            <spring:message code="login.error.maximum.exceeded"/>
                        </div>
                        &nbsp;
                    </div>
                </div>

            </c:if>
            <c:if test="${param.error eq 'inactiveUser'}">
                <div class="col-xs-9">
                    <div class="alert alert-danger alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span
                                class="sr-only">Close</span></button>
                        <div class="col-xs-1 pull-left">
                            <i class="glyphicon glyphicon-remove-circle"></i>
                        </div>
                        <div class="col-xs-10">
                            <h5 class="general"><spring:message code="message.oops"/>!</h5>
                            <spring:message code="login.error.inactive.user"/>
                        </div>
                        &nbsp;
                    </div>
                </div>
            </c:if>
            <c:if test="${param.error eq 'loginError'}">
                <div class="col-xs-9">
                    <div class="alert alert-danger alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span
                                class="sr-only">Close</span></button>
                        <div class="col-xs-1 pull-left">
                            <i class="glyphicon glyphicon-remove-circle"></i>
                        </div>
                        <div class="col-xs-10">
                            <h5 class="general"><spring:message code="message.oops"/>!</h5>
                            <spring:message code="login.error"/>
                        </div>
                        &nbsp;
                    </div>
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/login/authenticate" method="POST" role="form">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <div class="row">
                    <div id="form-group-email" class="form-group col-lg-4">
                        <label class="control-label" for="user-email"><spring:message code="label.user.email"/>:</label>
                        <input id="user-email" name="username" type="text" class="form-control input-md"/>
                    </div>
                </div>

                <div class="row">
                    <div id="form-group-password" class="form-group col-lg-4">
                        <label class="control-label" for="user-password"><spring:message
                                code="label.user.password"/>:</label>
                        <input id="user-password" name="password" type="password" class="form-control input-md"/>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-lg-4">
                        <button type="submit" class="btn btn-green"><spring:message
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
</section>

<section class="highlight">
    <div class="container">
        <div class="row">
            <div class="center-block">
                <div class="box-small"></div>
                <h1>Включи се в наддаванията! Купувай на най-ниска цена</h1>
                <h5>Регистрирай се за безплатен акаунт и започни да наддаваш</h5>
                <a href="#" class="not-default">
                    <img src="${pageContext.request.contextPath}/img/Facebook-Reg.png" class="register" alt="">
                </a>
                <a href="#" class="not-default">
                    <img src="${pageContext.request.contextPath}/img/GooglePlus-Reg.png" class="spacing register"
                         alt="">
                </a>
                <br><br>
                <h6>Регистрирайки се за акаунт в Жълтица.com се съгласяваш с нашите <a href=""
                                                                                       style="text-decoration: underline;">Общи
                    условия</a>.</h6>

                <div class="box"></div>
            </div>
        </div>
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
                                <li class="timer">86:06:59</li>
                                <li class="coins">x8</li>
                            </ul>
                        </div>
                        <hr class="bottom">
                        <br>
                    </div>

                    <div class="specs">
                        <a href="#" class="product-name">
                            <h4>Лаптоп HP G62 Series, 15.6 inch, 12 Gb RAM</h4>
                        </a>
                        <span class="price"> 781<sup>11</sup> лв. </span><br>
                        <span class="old-price">Продажна цена 258 лв.</span>
                        <br><br>
                        <a href="#" class="button-bid"> Наддавай </a>
                        <br><br>

                        <div class="boxed-spacer"></div>
                        <a href="" class="outbuy">Купи веднага за 1211<sup>54</sup> лв.</a>
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
                                <li class="timer">86:06:59</li>
                                <li class="coins">x8</li>
                            </ul>
                        </div>
                        <hr class="bottom">
                        <br>
                    </div>

                    <div class="specs">
                        <a href="#" class="product-name">
                            <h4>Wacom Intuos Pen t&amp; Touch Medium</h4>
                        </a>
                        <span class="price"> 158<sup>36</sup> <span class="smaller">лв.</span> </span><br>
                        <span class="old-price">Продажна цена 258 лв.</span>
                        <br><br>
                        <a href="#" class="button-bid"> Наддавай </a>
                        <br><br>

                        <div class="boxed-spacer"></div>
                        <a href="" class="outbuy">Купи веднага за 141<sup>05</sup> лв.</a>
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
                                <li class="timer">86:06:59</li>
                                <li class="coins">x8</li>
                            </ul>
                        </div>
                        <hr class="bottom">
                        <br>
                    </div>

                    <div class="specs">
                        <a href="#" class="product-name">
                            <h4>Мастилно устройство 3в1, Canon Pixima</h4>
                        </a>
                        <span class="price"> 101<sup>95</sup> <span class="smaller">лв.</span> </span><br>
                        <span class="old-price">Продажна цена 258 лв.</span>
                        <br><br>
                        <a href="#" class="button-bid"> Наддавай </a>
                        <br><br>

                        <div class="boxed-spacer"></div>
                        <a href="" class="outbuy">Купи веднага за 131<sup>98</sup> лв.</a>
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
                                <li class="timer">86:06:59</li>
                                <li class="coins">x8</li>
                            </ul>
                        </div>
                        <hr class="bottom">
                        <br>
                    </div>

                    <div class="specs">
                        <a href="#" class="product-name">
                            <h4>iWatch, златен</h4>
                        </a>
                        <span class="price"> 145<sup>05</sup> <span class="smaller">лв.</span> </span><br>
                        <span class="old-price">Продажна цена 258 лв.</span>
                        <br><br>
                        <a href="#" class="button-bid"> Наддавай </a>
                        <br><br>

                        <div class="boxed-spacer"></div>
                        <a href="" class="outbuy">Купи веднага за 236<sup>54</sup> лв.</a>
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

<section class="auctions">
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
</section>

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
        <img src="${pageContext.request.contextPath}/img/Coins/12.png" data-stellar-ratio="0.8"
             data-stellar-vertical-offset="-100"
             style="position: absolute; top: 100px; left: 400px;" alt="">
        <img src="${pageContext.request.contextPath}/img/Coins/12.png" data-stellar-ratio="0.8"
             data-stellar-vertical-offset="-100"
             style="position: absolute; top: 200px; right: 300px;" alt="">
    </div>
</section>

<%--
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
<script>
    $(document).ready(function () {
        var $obj = $('.user-coins');
        var top = $obj.offset().top - parseFloat($obj.css('marginTop').replace(/auto/, 0));

        $(window).scroll(function (event) {
            // what the y position of the scroll is
            var y = $(this).scrollTop();

            // whether that's below the form
            if (y >= top) {
                // if so, ad the fixed class
                $obj.addClass('fixed');
                $("#brand").attr('src', '${pageContext.request.contextPath}/img/Jultica-small.png');
                $("#brand").css('marginTop', '-15px');
            } else {
                // otherwise remove it
                $obj.removeClass('fixed');
                $("#brand").attr('src', '${pageContext.request.contextPath}/img/Jultica.png');
                $("#brand").css('marginTop', '0px');
            }
        });
    });

    $(function () {
        $('.soc').hover(function () {
            var Element = $(this).data('element');
            window.Current = $(this).attr('src');

            var States = {1: 'Facebook-Hover.png', 2: 'GooglePlus-Hover.png', 3: 'Twitter-Hover.png', 4: 'Up-Hover.png'};

            $(this).attr('src', 'img/social/' + States[Element]);
        }, function () {
            $(this).attr('src', window.Current);
        });
    });
</script>

<script src="${pageContext.request.contextPath}/js/stellar.js-master/src/jquery.stellar.js"></script>
<script src="${pageContext.request.contextPath}/js/imakewebthings-jquery-waypoints-415eb55/waypoints.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.easing.1.3.js"></script>
<script>
    $(function () {
        $(window).stellar({horizontalScrolling: false, responsive: true});
    });
</script>--%>

</body>
</html>