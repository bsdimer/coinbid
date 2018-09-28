<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
</head>
<body>

<div class="box"></div>

<section class="aligned">
    <div class="container">
        <div class="row">
            <div class="col-xs-10">
                <div class="breadcrumbs">
                    <ul>
                        <li class="page"><a href="#">Начало</a></li>
                        <li class="divider">&gt;</li>
                        <li class="page">Помощ</li>
                    </ul>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-xs-6">
                <ul class="questions">
                    <li><h3>Регистрация</h3></li>
                    <li><a href="">Как да се регистирам?</a></li>
                    <li><a href="">Колко струва регистрацията?</a></li>
                    <li><a href="">Какво получавам с регистрацията си?</a></li>
                    <li><a href="">Общи условия за ползване</a></li>
                    <li><a href="">Защитени ли са данните ми?</a></li>
                    <li><a href="">Как да ползвам акаунта си?</a></li>
                </ul>

                <ul class="questions">
                    <li><h3>Плащане</h3></li>
                    <li><a href="#payment-methods">Какви платежни методи мога да ползвам?</a></li>
                    <li><a href="#is-payment-secure">Сигурно ли е плащането към жълтица.com?</a></li>
                    <li><a href="#help">Помощ: плащането беше прекъснато, таксувана ли е картата ми?</a></li>
                    <li><a href="#terms">Общи проблеми при плащане</a></li>
                </ul>
            </div>
            <div class="col-xs-6">
                <ul class="questions">
                    <li><h3>Наддавания</h3></li>
                    <li><a href="#what-are">Какво представляват наддаванията?</a></li>
                    <li><a href="#how-to-participate">Как мога да се включа в наддаване?</a></li>
                    <li><a href="#jultici">Какво са жълтиците и защо ги ползваме?</a></li>
                    <li><a href="#how-to-buy">Как да си купя жълтици?</a></li>
                    <li><a href="">Стратегии за наддаване?</a></li>
                    <li><a href="">Спечелих търг. Сега какво?</a></li>
                    <li><a href="">Автоматично наддаване - какво представлява и как да го ползвам?</a></li>
                </ul>
            </div>
        </div>
    </div>
</section>

<section class="grey">
    <div class="container">
        <div class="row">
            <div class="col-xs-12">
                <h3>Не намери каквото търсиш?</h3>
                <br>
                <strong>Пиши ни:</strong>
                <br>
							<span class="contact">
								<img src="${pageContext.request.contextPath}/img/Mail.png" alt="">
								<a href="mailto:">ineedhelp@jyltica.com</a>
							</span>
							<span class="contact">
								<img src="${pageContext.request.contextPath}/img/Skype.png" alt="">
								<a href="mailto:">ineedhelp</a>
							</span>
							<span class="contact">
								<ul>
                                    <li><a href="#"><img
                                            src="${pageContext.request.contextPath}/img/Facebook-Social.png" alt=""></a>
                                    </li>
                                    <li><a href="#"><img
                                            src="${pageContext.request.contextPath}/img/GooglePlus-Social.png"
                                            alt=""></a></li>
                                    <li><a href="#"><img src="${pageContext.request.contextPath}/img/Twitter-Social.png"
                                                         alt=""></a></li>
                                </ul>
							</span>
            </div>
        </div>
    </div>
</section>

<section class="work-process">
    <div class="container">
        <div class="row">
            <div class="col-xs-8">
                <h2 class="semi">Регистрация</h2>
                <h4>Как работи сайтът?</h4>

                <p>
                    Make your world a more creative place with this intuitive device that combines pressure-sensitive
                    pen and multi-touch capabilities, so you can express yourself naturally. This medium size allows for
                    broader pen strokes, but still fits conveniently into a laptop bag. Styled in striking silver and
                    black, this slim comfortable tablet is designed for both right and left hand use.
                </p>

                <div class="page-image">
                    <img src="${pageContext.request.contextPath}/img/Text-timer.png" class="text" alt="">
                    <img src="${pageContext.request.contextPath}/img/Help.png" alt="">
                </div>

                <a href="#">Обратно към началото</a>

                <article>
                    <h1>Общи условия за ползване</h1>

                    <p>Make your world a more creative place with this intuitive device that combines pressure-sensitive
                        pen and multi-touch capabilities, so you can express yourself naturally.</p>
                    <a href="#">Обратно към началото</a>
                </article>
            </div>
        </div>
    </div>
</section>

<section class="articles grey">
    <div class="container">
        <div class="row">
            <div class="col-xs-8">
                <h2 class="semi">Наддавания</h2>

                <article id="what-are">
                    <h1>Какво представляват наддаванията?</h1>

                    <p>Make your world a more creative place with this intuitive device that combines pressure-sensitive
                        pen and multi-touch capabilities, so you can express yourself naturally. This medium size allows
                        for broader pen strokes, but still fits conveniently into a laptop bag. Styled in striking
                        silver and black, this slim comfortable tablet is designed for both right and left hand use.</p>
                    <a href="#">Обратно към началото</a>
                </article>

                <article id="how-to-participate">
                    <h1>Как мога да се включа в наддаване?</h1>

                    <p>Make your world a more creative place with this intuitive device that combines pressure-sensitive
                        pen and multi-touch capabilities, so you can express yourself naturally. This medium size allows
                        for broader pen strokes, but still fits conveniently into a laptop bag. Styled in striking
                        silver and black, this slim comfortable tablet is designed for both right and left hand use.</p>
                    <a href="#">Обратно към началото</a>
                </article>

                <article id="jultici">
                    <h1>Какво са жълтиците и защо ги ползваме?</h1>

                    <p>Make your world a more creative place with this intuitive device that combines pressure-sensitive
                        pen and multi-touch capabilities, so you can express yourself naturally. This medium size allows
                        for broader pen strokes, but still fits conveniently into a laptop bag. Styled in striking
                        silver and black, this slim comfortable tablet is designed for both right and left hand use.</p>
                    <a href="#">Обратно към началото</a>
                </article>

                <article id="how-to-buy">
                    <h1>Как да си купя жълтици?</h1>

                    <p>Make your world a more creative place with this intuitive device that combines pressure-sensitive
                        pen and multi-touch capabilities, so you can express yourself naturally. This medium size allows
                        for broader pen strokes, but still fits conveniently into a laptop bag. Styled in striking
                        silver and black, this slim comfortable tablet is designed for both right and left hand use.</p>
                    <a href="#">Обратно към началото</a>
                </article>
            </div>
        </div>
    </div>
    <div class="box"></div>
</section>

<section class="articles">
    <div class="container">
        <div class="row">
            <div class="col-xs-8">
                <h2 class="semi">Плащане</h2>

                <article id="payment-methods">
                    <h1>Какви платежни методи мога да използвам?</h1>

                    <p>Make your world a more creative place with this intuitive device that combines pressure-sensitive
                        pen and multi-touch capabilities, so you can express yourself naturally. This medium size allows
                        for broader pen strokes, but still fits conveniently into a laptop bag. Styled in striking
                        silver and black, this slim comfortable tablet is designed for both right and left hand use.</p>
                    <a href="#">Обратно към началото</a>
                </article>

                <article id="is-payment-secure">
                    <h1>Сигурно ли е плащането към жълтица.com?</h1>

                    <p>Make your world a more creative place with this intuitive device that combines pressure-sensitive
                        pen and multi-touch capabilities, so you can express yourself naturally. This medium size allows
                        for broader pen strokes, but still fits conveniently into a laptop bag. Styled in striking
                        silver and black, this slim comfortable tablet is designed for both right and left hand use.</p>
                    <a href="#">Обратно към началото</a>
                </article>

                <article id="help">
                    <h1>Помощ: плащането беше прекъснато, таксувана ли е картата ми?</h1>

                    <p>Make your world a more creative place with this intuitive device that combines pressure-sensitive
                        pen and multi-touch capabilities, so you can express yourself naturally. This medium size allows
                        for broader pen strokes, but still fits conveniently into a laptop bag. Styled in striking
                        silver and black, this slim comfortable tablet is designed for both right and left hand use.</p>
                    <a href="#">Обратно към началото</a>
                </article>

                <article id="terms">
                    <h1>Общи проблеми при плащане</h1>

                    <p>Make your world a more creative place with this intuitive device that combines pressure-sensitive
                        pen and multi-touch capabilities, so you can express yourself naturally. This medium size allows
                        for broader pen strokes, but still fits conveniently into a laptop bag. Styled in striking
                        silver and black, this slim comfortable tablet is designed for both right and left hand use.</p>
                    <a href="#">Обратно към началото</a>
                </article>
            </div>
        </div>
    </div>
    <div class="box"></div>
</section>

<%--
<!-- jQuery 2.1.1.min -->
<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
<script>
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
</script>--%>

</body>
</html>