<div id="notifications_dlg" title="<spring:message code="label.navigation.notifications"/>">
    <div class="row ng-scope" ng-controller="inboxCtrl">
        <aside class="col-md-2 pad-right-0">
            <ul class="nav nav-pills nav-stacked">
                <li data-bind="css: { active: userModel().messageModel.messageType() === 'RECEIVED' }"><a
                        href="javascript:;"
                        data-bind="click: function () { userModel().messageModel.setMessageType('RECEIVED'); $root.userModel().messageModel.setSelectedMessage(''); }"><span
                        data-bind="text: userModel().messageModel.unreadMessageCount()"
                        class="badge pull-right ng-binding"></span> <spring:message code="common.Inbox"/> </a></li>
                <li data-bind="css: { active: userModel().messageModel.messageType() === 'SENT' }"><a
                        href="javascript:;"
                        data-bind="click: function () { userModel().messageModel.setMessageType('SENT'); $root.userModel().messageModel.setSelectedMessage('');}"><spring:message
                        code="common.Sent"/></a></li>
            </ul>
        </aside>
        <div class="col-md-10 inbox">
            <!--inbox toolbar-->
            <div class="row" ng-show="!isMessageSelected()">
                <div class="col-xs-12">
                    <button data-bind="click: function () { $('#modalCompose').show(); }" class="btn btn-default btn-lg"
                            title="<spring:message code="common.send.message"/>" data-toggle="modal" data-target="#modalCompose">
                        <spring:message code="common.send.message"/>
                        <span class="fa fa-edit fa-lg"></span>
                    </button>
                    <div class="btn-group btn-group-lg">
                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                            <spring:message code="common.more"/> <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="javascript:;" ng-click="readAll()"><spring:message
                                    code="common.mark.all.as.read"/></a></li>
                            <li class="divider"></li>
                            <li>
                                <a href="" data-toggle="modal" data-target="#modalCompose">
                                    <spring:message code="common.send.message"/>
                                </a>
                            </li>
                        </ul>
                    </div>
                    <div class="pull-right">
                <span class="text-muted">
                    <b><span data-bind="text: userModel().messageModel.getStartIndex() + 1"></span></b>
                    -
                    <b>
                        <span data-bind="text: userModel().messageModel.getStartIndex() + userModel().messageModel.pageMessages().length"></span>
                    </b> <spring:message code="common.of"/> <span
                        data-bind="text: userModel().messageModel.messageType() === 'RECEIVED' ? userModel().messageModel.totalMessageCount() : userModel().messageModel.totalSentMessageCount()"></span></span>

                        <div class="btn-group btn-group">
                            <button type="button"
                                    data-bind="click: function () { userModel().messageModel.loadPageMessages(-1); }, css: { disabled: userModel().messageModel.getStartIndex() === 0 }"
                                    class="btn btn-default btn-lg disabled" ng-class="{disabled: currentPage == 0}"
                                    ng-click="prevPage()">
                                <span class="glyphicon glyphicon-chevron-left"></span>
                            </button>
                            <button type="button"
                                    data-bind="click: function () { userModel().messageModel.loadPageMessages(1); },
                                css: { disabled: userModel().messageModel.messageType() === 'RECEIVED' ? (((userModel().messageModel.page() + 1) * userModel().messageModel.pageSize()) >= userModel().messageModel.totalMessageCount()) : (((userModel().messageModel.page() + 1) * userModel().messageModel.pageSize()) >= userModel().messageModel.totalSentMessageCount()) }"
                                    class="btn btn-default btn-lg"
                                    ng-class="{disabled: currentPage == pagedItems.length - 1}" ng-click="nextPage()">
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
                <div class="table-responsive" data-bind="with: !userModel().messageModel.selectedMessage().text"
                     ng-show="!isMessageSelected()">
                    <table class="table table-striped table-hover refresh-container pull-down">
                        <thead class="hidden-xs">
                        <tr>
                            <td class="col-sm-1 col-xs-4 text-center"><input type="checkbox" title="<spring:message code="common.mark.all"/>"></td>
                            <td class="col-sm-3 col-xs-4 text-center"><a href="javascript:;"><strong><spring:message
                                    code="common.Date"/></strong></a></td>
                            <td class="col-sm-2 col-xs-4 text-center"><a href="javascript:;"><strong><spring:message
                                    code="common.From"/></strong></a></td>
                            <td class="col-sm-4 col-xs-6 text-center"><a href="javascript:;"><strong><spring:message
                                    code="common.subject"/></strong></a></td>
                            <td class="col-sm-1 col-xs-4 text-center"></td>
                        </tr>
                        </thead>
                        <tbody data-bind="foreach: $root.userModel().messageModel.pageMessages()">
                        <!-- ngRepeat: item in pagedItems[currentPage] | orderBy:sortingOrder:reverse -->
                        <tr data-bind="click: function () { $root.userModel().messageModel.setSelectedMessage($data); }">
                            <td class="col-sm-1 col-xs-4"><input type="checkbox" title="<spring:message code="common.mark"/>"></td>
                            <td class="col-sm-3 col-xs-4" ng-click="readMessage($index)"><span
                                    data-bind="text: date, css: { 'text-bolder': !read()}"></span></td>
                            <td class="col-sm-2 col-xs-4" ng-click="readMessage($index)"><span
                                    data-bind="text: sender, css: { 'text-bolder': !read()}"
                                    ng-class="{strong: !item.read}" class="ng-binding strong"></span></td>
                            <td class="col-sm-4 col-xs-6" ng-click="readMessage($index)"><span
                                    data-bind="text: subject, css: { 'text-bolder': !read()}"
                                    ng-class="{strong: !item.read}" class="ng-binding strong"></span></td>
                            <td class="col-sm-1 col-xs-4"><a ng-class="{strong: !item.read}"
                                                             ng-click="deleteItem($index)" title="<spring:message code="common.delete"/>"
                                                             href="javascript:;" class="strong"><i
                                    class="glyphicon glyphicon-trash"></i></a> <a title="<spring:message code="common.mark"/>" href="javascript:;"><i
                                    class="fa fa-flag"></i></a></td>
                        </tr>
                        <!-- end ngRepeat: item in pagedItems[currentPage] | orderBy:sortingOrder:reverse -->
                        </tbody>
                    </table>
                </div>
                <!--message detail-->
                <div class="container-fluid" data-bind="with: userModel().messageModel.selectedMessage().text"
                     ng-show="isMessageSelected()">
                    <div class="row" ng-controller="messageCtrl">
                        <div class="col-xs-12">
                            <h3 title="<spring:message code="common.subject"/>">
                                <button type="button" class="close pull-right"
                                        data-bind="click: function () { $root.userModel().messageModel.setSelectedMessage(''); }"
                                        ng-click="closeMessage()" aria-hidden="true">x
                                </button>
                                <a href="javascript:;"
                                   data-bind="click: function () { $root.userModel().messageModel.setSelectedMessage(''); }"
                                   ng-click="groupToPages()"><spring:message code="common.Inbox"/></a>
                                &gt; <span
                                    data-bind="text: $root.userModel().messageModel.selectedMessage().subject"></span>
                            </h3>
                        </div>
                        <div class="col-md-8">
                            <blockquote class="bg-info small">
                                <strong><span
                                        data-bind="text: $root.userModel().messageModel.selectedMessage().sender"></span></strong>
                                <spring:message code="common.from"/> <span
                                    data-bind="text: $root.userModel().messageModel.selectedMessage().date"></span>
                            </blockquote>
                            <span data-bind="html: $root.userModel().messageModel.selectedMessage().text"
                                  class="preformatted"></span>
                        </div>
                        <div class="col-md-4">
                            <div class="btn-group btn-group-lg pull-right">
                                <button data-bind="click: function () { $root.userModel().messageModel.reply(); }"
                                        class="btn btn-primary" title="<spring:message code="common.Reply"/>" data-toggle="tooltip">
                                    <i class="icon-reply"></i> <spring:message code="common.Reply"/>
                                </button>
                            </div>
                            <div class="spacer5 pull-right"></div>
                            <button class="btn btn-lg btn-primary pull-right" ng-click="deleteItem(selected.$index)"
                                    title="<spring:message code="common.delete"/>" data-toggle="tooltip">
                                <i class="glyphicon glyphicon-trash"></i>
                            </button>
                        </div>
                        <div class="col-xs-12">
                            <hr>
                        </div>
                        <div class="col-xs-12">
                            <!--message body-->
                            <div ng-bind-html="renderMessageBody(selected.body)"></div>
                            <!--/message body-->
                        </div>
                        <div class="col-xs-12 clearfix">
                            <hr>
                        </div>
                    </div>
                    <!--/row-->
                </div>
            </div>
            <!--/inbox panel-->
        </div>
        <!--/col-9-->
        <!-- /.modal compose message -->
        <div class="modal" id="modalCompose">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form role="form" class="form-horizontal ng-pristine ng-valid"
                          data-bind="submit: userModel().messageModel.sendMessage">
                        <div class="modal-header">
                            <button type="button" data-bind="click: function () { $('#modalCompose').hide(); }"
                                    class="close" data-dismiss="modal" aria-hidden="true">x
                            </button>
                            <h4 class="modal-title"><spring:message code="common.send.message"/></h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label class="col-sm-2"><spring:message code="common.To"/></label>

                                <div class="col-sm-10"><spring:message code="ROLE_ADMIN"/></div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2" for="inputSubject"><spring:message
                                        code="common.subject"/></label>

                                <div class="col-sm-10"><input type="text" class="form-control" id="inputSubject"
                                                              data-bind="value: userModel().messageModel.composedSubject"
                                                              placeholder="subject"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-12" for="inputBody"><spring:message code="common.message"/></label>

                                <div class="col-sm-12"><textarea class="form-control" id="inputBody"
                                                                 data-bind="value: userModel().messageModel.composedText"
                                                                 rows="12"></textarea></div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button data-bind="click: function () { $('#modalCompose').hide(); }" type="button"
                                    class="btn btn-default pull-left" data-dismiss="modal"><spring:message
                                    code="common.cancel"/></button>
                            <button type="submit" class="btn btn-primary "><spring:message code="common.send"/><i
                                    class="fa fa-arrow-circle-right fa-lg"></i></button>
                        </div>
                    </form>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>
        <!-- /.modal compose message -->
        <div><!--/row ng-controller-->

        </div>
        <!--/container-->

    </div>
</div>

