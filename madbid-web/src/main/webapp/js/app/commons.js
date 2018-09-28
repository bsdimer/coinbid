/**
 * Created by dimer on 1/1/15.
 */
var onLoad = function () {
    var self = this;

    $('input').iCheck({
        checkboxClass: 'icheckbox_flat-yellow',
        radioClass: 'iradio_flat-yellow'
    });

    $('#gallery').puigalleria();

    $('#tabViewAuctionDetails').puitabview();

    $('#userPanelTab a:first').tab('show');

    $('#buyCredits_dlg').puidialog({
        width: 700,
        showEffect: 'fade',
        hideEffect: 'fade',
        minimizable: true,
        maximizable: true,
        modal: true
    });

    var pays4coins = $("#pay4coinsForm-form");
    pays4coins.children("div").steps({
        headerTag: "h3",
        bodyTag: "section",
        transitionEffect: "slideLeft",
        saveState: false,

        onFinished: function (event, currentIndex) {
            $.ajax({
                type: "GET",
                url: "getInvoiceId",
                data: {"amount": self.amountValue, "paymentType": self.paymentType},
                success: function (data) {
                    $('#pt_invoice').val(data.id);
                    $('#pt_invoice_view').val(data.id);
                    $('#pt_expTime').val(data.expireAt);
                    $('#pt_expTime_view').val(data.expireAt);

                    $('#buyCredits_dlg').puidialog("hide");
                    submitForm.submit();
                },
                error: function () {
                    alert("Error getting the Invoice ID");
                }
            });
        }
    });

    $("#packages_div").buttonset();
    $("#paymentTypes_div").buttonset();
    $('#paymentType_accrd').puiaccordion({});

    $("input:radio[name=packRadio]").click(function () {
        self.amountValue = $(this).attr("value");
        $('#pt_amount').val(self.amountValue);
        $('#pt_amount_view').val(self.amountValue);
    });

    $("input:radio[name=paymentType]").click(function () {
        self.paymentType = $(this).attr("value");
        var ptPage = $('#pt_page');

        switch (self.paymentType) {
            case "dc":
                ptPage.val("paylogin");
                break;
            case "cc":
                ptPage.val("credit_paydirect");
                break;
            default:
        }
    });

    addValidationErrorClassesToForm();

    function addValidationErrorClassesToForm() {
        $("#userRegisterForm").find(".form-group").each(function () {
            var errorMessage = $(this).find(".help-block").text();

            if (errorMessage) {
                $(this).addClass("has-error");
            }
        })
    }

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

        $(window).stellar({horizontalScrolling: false, responsive: true});

        $('.soc').hover(function () {
            var Element = $(this).data('element');
            window.Current = $(this).attr('src');

            var States = {
                1: 'Facebook-Hover.png',
                2: 'GooglePlus-Hover.png',
                3: 'Twitter-Hover.png',
                4: 'Up-Hover.png'
            };

            $(this).attr('src', 'img/social/' + States[Element]);
        }, function () {
            $(this).attr('src', window.Current);
        });
    });

};


