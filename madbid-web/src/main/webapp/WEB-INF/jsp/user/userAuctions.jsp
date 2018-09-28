<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<aside class="col-md-2 pad-right-0">
    <ul class="nav nav-pills nav-stacked">
        <li data-bind="css: { active: userModel().selectedState() === 'ALL' }">
            <a href="javascript:;"
                data-bind="click: function () { userModel().setSelectedState('ALL'); }">
                <spring:message code="common.all"/>
            </a>
        </li>
        <li data-bind="css: { active: userModel().selectedState() === 'WON' }">
            <a href="javascript:;"
               data-bind="click: function () { userModel().setSelectedState('WON'); }">
                <spring:message code="common.won"/>
            </a>
        </li>
        <li data-bind="css: { active: userModel().selectedState() === 'LOST' }">
            <a href="javascript:;"
               data-bind="click: function () { userModel().setSelectedState('LOST'); }">
                <spring:message code="common.lost"/>
            </a>
        </li>
    </ul>
</aside>
<div class="col-md-10 inbox">
    <!--inbox toolbar-->
    <div class="row">
        <div class="col-xs-12">
            <div class="pull-right">
            <span class="text-muted">
                <b><span data-bind="text: userModel().auctions().length > 0 ? 1 : 0"></span></b>
                -
                <b>
                    <span data-bind="text: userModel().auctions().length"></span>
                </b> <spring:message code="common.of"/> <span
                    data-bind="text: userModel().auctions().length"></span></span>

                <div class="btn-group btn-group">
                    <button type="button"
                            data-bind="click: function () { }, css: { disabled: true }"
                            class="btn btn-default btn-lg disabled">
                        <span class="glyphicon glyphicon-chevron-left"></span>
                    </button>
                    <button type="button"
                            data-bind="click: function () { },
                            css: { disabled: true }"
                            class="btn btn-default btn-lg">
                        <span class="glyphicon glyphicon-chevron-right"></span>
                    </button>
                </div>
            </div>
        </div>
        <!--/col-->
        <div class="col-xs-12 spacer5"></div>
    </div>
    <!--/row-->
    <!--/inbox toolbar-->
    <div class="panel panel-default inbox" id="inboxPanel">
        <!--message list-->
        <div class="table-responsive">
            <table class="table table-striped table-hover refresh-container pull-down">
                <thead class="hidden-xs">
                <tr>
                    <td class="col-sm-3 col-xs-4 text-center">
                        <a href="javascript:;">
                            <strong><spring:message code="common.id"/></strong>
                        </a>
                    </td>
                    <td class="col-sm-2 col-xs-4 text-center">
                        <a href="javascript:;">
                            <strong><spring:message code="common.state"/></strong>
                        </a>
                    </td>
                    <td class="col-sm-4 col-xs-6 text-center">
                        <a href="javascript:;">
                            <strong><spring:message code="common.add.picture"/></strong>
                        </a>
                    </td>
                </tr>
                </thead>
                <tbody data-bind="foreach: $root.userModel().auctions()">
                <!-- ngRepeat: item in pagedItems[currentPage] | orderBy:sortingOrder:reverse -->
                    <tr>
                        <td class="col-sm-3 col-xs-4">
                            <span data-bind="text: auctionId"></span>
                        </td>
                        <td class="col-sm-2 col-xs-4">
                            <span data-bind="text: state"
                                class="ng-binding strong"></span>
                        </td>
                        <td class="col-sm-1 col-xs-4">
                            <a data-bind="click: function () { $('#modalFileUpload').show(); $root.userModel().selectedAuction($data);}"
                                title="<spring:message code="common.add.picture"/>"
                                href="javascript:;" class="strong">
                                <i class="glyphicon glyphicon-picture"></i>
                            </a>
                        </td>
                    </tr>
                <!-- end ngRepeat: item in pagedItems[currentPage] | orderBy:sortingOrder:reverse -->
                </tbody>
            </table>
        </div>
    </div>
    <!--/inbox panel-->
    <div class="modal" id="modalFileUpload">
        <div class="modal-dialog">
            <div class="modal-content">
                <form role="form" enctype="multipart/form-data"
                      data-bind="submit: userModel().uploadAuctionWinPicture">
                    <div class="modal-header">
                        <button type="button" data-bind="click: function () { $('#modalFileUpload').hide(); $root.userModel().selectedAuction({});}"
                                class="close" data-dismiss="modal" aria-hidden="true">x
                        </button>
                        <h4 class="modal-title"><spring:message code="common.add.picture"/></h4>
                    </div>
                    <div class="modal-body">
                        <spring:message code="common.choose.picture"/>
                        <input type="file" name="file" id="file"/><br />
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary "><spring:message code="common.send"/><i
                                class="fa fa-arrow-circle-right fa-lg"></i></button>
                    </div>
                </form>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
</div>

<script type="text/javascript">

    $(function () {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });

//        $("input:radio[name=selector]").click(function () {
//            var value = $(this).val();
//            switch (value) {
//                case "1":
//                    selector = "userAuctions";
//                    break;
//                case "2":
//                    selector = "wonAuctions";
//                    break;
//                case "3":
//                    selector = "nonWonAuctions";
//                    break;
//                default:
//                    selector = "userAuctions";
//            }
//            // $('#userAuctionsTable').puidatatable('reset');
//            $('#userAuctionsTable').puidatatable("sort","id",1);
//        });

    });

//    var getUserAuctions = function () {
//        $('#userAuctionsForm').unbind("submit").submit(function (event) {
//            event.preventDefault();
//            $.ajax({
//                type: "POST",
//                url: "userAuctions",
//                success: function (data) {
//                    $("#result_div").text(data);
//                },
//                error: function () {
//                    $("#result_div").text("Error in submitting of address");
//                }
//            });
//        });
//    }
</script>
