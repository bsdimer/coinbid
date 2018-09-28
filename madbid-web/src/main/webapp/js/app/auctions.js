var onWinFunction;

function UIManager() {
    var self = this;

    self.onWin = function (eventFunction) {
        onWinFunction = eventFunction;
    };

}
/**
 * Created by dimer on 8/29/14.
 */

function ApplicationModel(stompClient) {
    var self = this;

    self.username = ko.observable("guest");
    self.auctionModel = ko.observable(new AuctionModel(stompClient));
    self.userModel = ko.observable(new UserModel(stompClient));
    self.notifications = ko.observableArray();
    self.uiManager = new UIManager();

    onClickAuction = function (data, event) {
        window.open("auctionDetails/" + data.auctionId(), "_self");
    };

    onAuctionBuyNowAuction = function (data, event) {
        window.open("buyNow/" + data.auctionId(), "_self");
    };

    self.connect = function () {
        stompClient.connect({}, function (frame) {

            console.log('Connected ' + frame);
            self.username(frame.headers['user-name']);

            stompClient.subscribe("/app/auctions", function (message) {
                self.auctionModel().loadAuctions(JSON.parse(message.body));
            });
            stompClient.subscribe("/app/upcomingAuctions", function (message) {
                self.auctionModel().loadUpcomingAuctions(JSON.parse(message.body));
            });
            stompClient.subscribe("/topic/updateAuction", function (message) {
                self.auctionModel().updateAuction(JSON.parse(message.body));
            });
            stompClient.subscribe("/topic/createAuction", function (message) {
                self.auctionModel().addAuction(JSON.parse(message.body));
            });
            stompClient.subscribe("/topic/removeAuction", function (message) {
                self.auctionModel().removeAuction(JSON.parse(message.body));
            });
            stompClient.subscribe("/topic/auctionWin", function (message) {
                self.auctionModel().onAuctionWin(JSON.parse(message.body));
            });
            stompClient.subscribe("/topic/updateAuctions", function (message) {
                self.auctionModel().loadAuctions(JSON.parse(message.body));
            });
            stompClient.subscribe("/app/updateWallet", function (message) {
                self.userModel().updateAvailableCoins(message.body);
            });
            stompClient.subscribe("/user/queue/updateWallet", function (message) {
                self.userModel().updateAvailableCoins(message.body);
            });
            stompClient.subscribe("/user/queue/updateMessage", function (message) {
                var messageData = JSON.parse(message.body);
                self.userModel().messageModel.totalMessageCount(self.userModel().messageModel.totalMessageCount() + 1);
                self.userModel().messageModel.unreadMessageCount(self.userModel().messageModel.unreadMessageCount() + 1);
                self.userModel().messageModel.messages.unshift(new MessageEntity(messageData));
                self.userModel().messageModel.loadPageMessagesFromCache();
            });
            stompClient.subscribe("/topic/updateMessage", function (message) {
                var messageData = JSON.parse(message.body);

                self.userModel().messageModel.totalMessageCount(self.userModel().messageModel.totalMessageCount() + 1);
                self.userModel().messageModel.unreadMessageCount(self.userModel().messageModel.unreadMessageCount() + 1);
                self.userModel().messageModel.messages.unshift(new MessageEntity(messageData));
                self.userModel().messageModel.loadPageMessagesFromCache();
            });
            stompClient.subscribe("/user/queue/messages", function (messages) {
                var messagesData = JSON.parse(messages.body);
                if (messagesData.totalMessagesCount != null
                    && messagesData.totalMessagesCount != ''
                    && messagesData.totalMessagesCount != 0) {
                    self.userModel().messageModel.totalMessageCount(messagesData.totalMessagesCount);
                    self.userModel().messageModel.unreadMessageCount(messagesData.unreadMessagesCount);
                }
                self.userModel().messageModel.loadMessagesInCache(messagesData.messages);
                self.userModel().messageModel.loadPageMessagesFromCache();
                console.log(messagesData.messages.length);
            });
            stompClient.subscribe("/user/queue/sent/messages", function (messages) {
                var messagesData = JSON.parse(messages.body);
                if (messagesData.totalMessagesCount != null
                    && messagesData.totalMessagesCount != ''
                    && messagesData.totalMessagesCount != 0) {
                    self.userModel().messageModel.totalSentMessageCount(messagesData.totalMessagesCount);
                }
                self.userModel().messageModel.loadMessagesInCache(messagesData.messages);
                self.userModel().messageModel.loadPageMessagesFromCache();
                console.log(messagesData.messages.length);
            });
            stompClient.subscribe("/user/queue/sent/message", function (message) {
                var messageData = JSON.parse(message.body);
                if (!!messageData) {
                    self.userModel().messageModel.totalSentMessageCount(self.userModel().messageModel.totalSentMessageCount() + 1);
                }
                self.userModel().messageModel.sentMessages.unshift(new MessageEntity(messageData));
                self.userModel().messageModel.loadPageMessagesFromCache();
            });
            self.userModel().messageModel.loadPageMessages(0);
            /*stompClient.subscribe("/user/queue/errors", function (message) {
             self.pushNotification("Error " + message.body);
             });*/
        }, function (error) {
            console.log("STOMP protocol error " + error);
        });

    };

    self.pushNotification = function (text) {
        self.notifications.push({notification: text});
        if (self.notifications().length > 5) {
            self.notifications.shift();
        }
    };

    self.getAuction = function (id) {
        return self.auctionModel().getAuction(id);
    };

    self.getLastAuction = function () {
        return self.auctionModel().getLastAuction();
    };

}

function UserModel(stompClient) {
    var self = this;

    self.coinsAvailable = ko.observable("0");
    self.updateAvailableCoins = function (data) {
        self.coinsAvailable(data);
    };
    self.messageModel = new UserMessageModel(stompClient);

    self.auctions = ko.observableArray([]);
    self.selectedState = ko.observable('WON');//ALL, WON, LOST
    self.selectedAuction = ko.observable({});

    self.setSelectedState = function (state) {
        self.selectedState(state);
    }

    self.loadUserAuctionsInCache = function (auctions) {
        for (var i = 0; i < auctions.length; i++) {
            self.auctions.push(new AuctionEntity(auctions[i]));
        }
    }

    self.getUserAuctions = function (contextPath) {
        //STODO selector
        var selector = "wonAuctions";

        var uri = contextPath + '/rest/' + selector + '/lazyList/0/id/1';
        $.ajax({
            type: "GET",
            url: uri,
            dataType: "json",
            context: this,
            success: function (auctions) {
                self.loadUserAuctionsInCache(auctions);
            },
            error: function (response) {
                //TODO show some message
            }
        });
    }

    self.uploadAuctionWinPicture = function (contextPath) {
        var oMyForm = new FormData();
        oMyForm.append("file", file.files[0]);
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
        $.ajax({
            url: '/madbid-web/user/uploadWinAuctionPicture/' + self.selectedAuction().auctionId(),
            data: oMyForm,
            dataType: 'text',
            processData: false,
            contentType: false,
            type: 'POST',
            success: function (data) {
                console.log(data);
            }
        });
        $('#modalFileUpload').hide();
        self.selectedAuction({});
    }
}

function UserMessageModel(stompClient) {
    var self = this;

    self.pageSize = ko.observable("10");
    self.page = ko.observable("0");
    self.messages = [];//All cached messages
    self.sentMessages = [];//All sent cached messages
    self.totalMessageCount = ko.observable("0");
    self.totalSentMessageCount = ko.observable("0");
    self.unreadMessageCount = ko.observable("0");
    self.pageMessages = ko.observableArray([]);
    self.selectedMessage = ko.observable({});
    self.messageType = ko.observable("RECEIVED");//Types are RECEIVED, SENT
    self.composedSubject = ko.observable();
    self.composedText = ko.observable();
    self.composedReplyText = ko.observable();

    self.reply = function () {
        //STODO i18n
        var newLine = "&#13;&#10;";
        var replySubject = "Re: " + self.selectedMessage().subject();
        var replyText = newLine
            + "---------- Оригинално писмо ----------" + newLine
            + "От: <" + self.selectedMessage().sender() + ">" + newLine
            + "Дата: " + self.selectedMessage().date() + newLine
            + "Тема: " + self.selectedMessage().subject() + newLine + newLine
            + self.selectedMessage().text();

        self.composedSubject(replySubject);
        self.composedReplyText(replyText);

        $('#modalCompose').show();
    };

    self.setMessageType = function (messageType) {
        self.messageType(messageType);
        self.loadPageMessages(0);
    };

    self.sendMessage = function (message) {
        $('#modalCompose').hide();
        var text = self.composedText() + "&#13;&#10;" + self.composedReplyText();
        var messageRequest = {
            "subject": self.composedSubject(),
            "text": text
        };
        stompClient.send("/app/message/send", {}, JSON.stringify(messageRequest));
        self.composedSubject("");
        self.composedText("");
    };

    var markMessageAsRead = function (message) {
        if (self.messageType() === 'RECEIVED' && !message.read()) {
            message.read(true);
            self.unreadMessageCount(self.unreadMessageCount() - 1);
            var messageRequest = {
                "id": message.id()
            };
            stompClient.send("/app/message/read", {}, JSON.stringify(messageRequest));
        }
    };

    self.setSelectedMessage = function (message) {
        if (message != null && message != '') {
            markMessageAsRead(message);
        }
        self.selectedMessage(message);
    };

    //STODO refactorate this method
    self.loadPageMessages = function (direction) {
        if (direction === 0) {
            //direction = 0 => returns first page
            self.page(direction);
            if (self.messageType() === 'RECEIVED' && self.messages.length === 0) {
                self.sendMessagesRequest();
            } else if (self.messageType() === 'SENT' && self.sentMessages.length === 0) {
                self.sendSentMessagesRequest();
            } else {
                self.loadPageMessagesFromCache();
            }
        } else if (direction === 1) {
            //direction = 1 => returns next page
            self.page(self.page() + 1);
            var startIndex = self.page() * self.pageSize();
            var endIndex = startIndex + parseInt(self.pageSize());

            if (self.messageType() === 'RECEIVED' && endIndex > self.totalMessageCount()) {
                endIndex = self.totalMessageCount();
            } else if (self.messageType() === 'SENT' && endIndex > self.totalSentMessageCount()) {
                endIndex = self.totalSentMessageCount();
            }

            if (self.messageType() === 'RECEIVED') {
                if (self.messages.length >= endIndex) {
                    self.loadPageMessagesFromCache();
                } else {
                    self.sendMessagesRequest();
                }
            } else {
                if (self.sentMessages.length >= endIndex) {
                    self.loadPageMessagesFromCache();
                } else {
                    self.sendSentMessagesRequest();
                }
            }

        } else if (direction === -1) {
            //direction = -1 => returns previous page
            self.page(self.page() - 1);
            self.loadPageMessagesFromCache();
        } else {
            //STODO exception
        }
    };

    self.sendMessagesRequest = function () {
        var messageCriteria = {
            "page": self.page(),
            "pageSize": self.pageSize()
        };
        stompClient.send("/app/messages", {}, JSON.stringify(messageCriteria));
    };

    self.sendSentMessagesRequest = function () {
        var messageCriteria = {
            "page": self.page(),
            "pageSize": self.pageSize()
        };
        stompClient.send("/app/sent/messages", {}, JSON.stringify(messageCriteria));
    };

    self.loadMessagesInCache = function (messages) {
        for (var i = 0; i < messages.length; i++) {
            var row = new MessageEntity(messages[i]);
            if (self.messageType() === 'RECEIVED') {
                self.messages.push(row);
            } else if (self.messageType() === 'SENT') {
                self.sentMessages.push(row);
            } else {
                //STODO exception
            }
        }
    };

    self.loadSentMessagesInCache = function (messages) {
        for (var i = 0; i < messages.length; i++) {
            var row = new MessageEntity(messages[i]);
            self.sentMessages.push(row);
        }
    };

    self.loadPageMessagesFromCache = function () {
        self.pageMessages([]);
        var startIndex = self.page() * self.pageSize();
        var pageSize = parseInt(self.pageSize());
        var endIndex = startIndex + pageSize;
        for (var i = startIndex; i < endIndex; i++) {
            if (!(!self.messages[i]) && self.messageType() === 'RECEIVED') {
                self.pageMessages.push(self.messages[i]);
            } else if (!(!self.sentMessages[i]) && self.messageType() === 'SENT') {
                self.pageMessages.push(self.sentMessages[i]);
            }
        }
    };

    self.getStartIndex = function () {
        return self.page() * self.pageSize();
    }
}

function MessageEntity(data) {
    var self = this;

    self.id = ko.observable(data.id);
    self.subject = ko.observable(data.subject);
    self.text = ko.observable(data.text);
    self.read = ko.observable(data.read);
    self.date = ko.observable(data.date);
    if (!!data.sender) {
        self.sender = ko.observable(data.sender);
    } else {
        self.sender = ko.observable('ADMINISTRATOR');
    }

//    self.receiver = ko.observable(data.receiver);
}

function AuctionModel(stompClient) {
    var self = this;

    // ToDo: must optimze cby using map not array
    self.auctions = ko.observableArray([]);
    self.upcomingAuctions = ko.observableArray([]);

    self.onClickAuction = function (data, event) {
        alert(data);
    };

    self.bid = function () {
        var bidResponse = {
            "id": this.auctionId()
        };
        stompClient.send("/app/bid", {}, JSON.stringify(bidResponse));
        stompClient.send("/app/updateWallet", {}, JSON.stringify(bidResponse));
    };

    self.loadAuctions = function (array) {
        for (var i = 0; i < array.length; i++) {
            var row = new AuctionEntity(array[i]);
            self.auctions.push(row);
            row.startTimer();
        }
    };

    self.loadUpcomingAuctions = function (array) {
        for (var i = 0; i < array.length; i++) {
            var row = new AuctionEntity(array[i]);
            self.upcomingAuctions.push(row);
            row.startTimer();
        }
    };

    self.updateAuction = function (data) {
        var updated = new AuctionEntity(data);
        for (var i = 0; i < self.auctions().length; i++) {
            if (self.auctions()[i].auctionId() == updated.auctionId()) {
                /*self.auctions()[i].bidsLen(updated.bidsLen());
                 self.auctions()[i].lastBidder(updated.lastBidder());*/
                self.auctions()[i].update(data);
                self.auctions()[i].resetTimer(updated.timer());
                self.selectedAuction(self.auctions[i]);
                break;
            }
        }
    };

    self.selectedAuction = ko.observable({});

    self.addAuction = function (data) {
        var entry = new AuctionEntity(data);
        self.auctions().push(entry);
    };

    self.removeAuction = function (data) {
        var updated = new AuctionEntity(data);
        for (var i = 0; i < self.auctions().length; i++) {
            if (self.auctions()[i].auctionId() == updated.auctionId()) {
                self.auctions().splice(i, i + 1);
                break;
            }
        }
    };

    self.onAuctionWin = function (data) {
        var updated = new AuctionEntity(data);
        for (var i = 0; i < self.auctions().length; i++) {
            if (self.auctions()[i].auctionId() == updated.auctionId()) {
                if (updated.state() == "WON") {
                    var auction = self.auctions().splice(i, i + 1);
                    onWinFunction(auction);
                }
            }
        }
    };

    self.getAuction = function (id) {
        for (var i = 0; i < self.auctions().length; i++) {
            if (self.auctions()[i].auctionId() == id) {
                return self.auctions()[i];
            }
        }
        return null;
    };

    self.getLastAuction = function () {
        return self.auctions()[self.auctions().length - 1];
    };


}

var currencies = {
    "BGN": "лв.",
    "USD": "$",
    "EUR": "€"
};

function AuctionEntity(data) {
    var self = this;

    self.auctionId = ko.observable(data.id);
    self.startDateTime = ko.observable(data.startDateTime);
    self.endDateTime = ko.observable(data.endDateTime);
    self.auctionDuration = ko.observable(data.auctionDuration);
    self.creditsStep = ko.observable(data.creditsStep);
    self.startPrice = ko.observable(data.startPrice);
    self.currentBiddingsPrice = ko.observable(data.currentBiddingsPrice);
    self.state = ko.observable(data.state);
    self.pictures = ko.observableArray(data.pictures);
    self.bidsLen = ko.observable(data.bidsLen);
    self.itemCurrency = ko.observable(data.itemCurrency);
    self.itemName = ko.observable(data.itemName);
    self.itemDescription = ko.observable(data.itemDescription);
    self.itemLongDescription = ko.observable(data.itemLongDescription);
    self.retailPrice = ko.observable(data.retailPrice);
    self.timer = ko.observable(data.timer);
    self.upcomingTimer = ko.observable(data.upcomingTimer);
    self.lastBidder = ko.observable(data.lastBidder);
    self.pictureUrl = ko.observable(data.pictureUrl);
    self.lastBids = ko.observableArray([]);
    self.auctionPrice = ko.observable(data.auctionPrice);

    self.timerFull = ko.computed(function () {
        return getFullTime(self.timer());
    });

    self.upcomfingTimerFull = ko.computed(function () {
        return getFullTime(self.upcomingTimer());
    });

    function getFullTime(source) {
        var totalSec = source;
        var hours = parseInt(totalSec / 3600) % 24;
        var minutes = parseInt(totalSec / 60) % 60;
        var seconds = totalSec % 60;

        return (hours < 10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
    }

    self.currentBiddingsPrice = ko.observable(data.currentBiddingsPrice);

    self.currentBiddingsPriceCore = ko.computed(function () {
        var stringPrice = self.currentBiddingsPrice().toString();
        return stringPrice.split(".")[0];
    }, this);

    self.currentBiddingsPriceSub = ko.computed(function () {
        var stringPrice = self.currentBiddingsPrice().toString().split(".")[1];
        if (typeof(stringPrice) !== undefined ) {
            return (stringPrice.length == 1) ? stringPrice.concat("0") : stringPrice;
        }
        return "00";
    }, this);

    self.itemPrice = ko.observable(data.itemPrice);
    self.itemPriceCore = ko.computed(function () {
        return self.itemPrice();
    }, this);
    self.itemPriceSub = ko.computed(function () {
        return self.itemPrice();
    }, this);
    self.itemCurrency = ko.observable(data.itemCurrency);
    self.itemCurrencyLoc = ko.computed(function () {
        return currencies[self.itemCurrency()];
    }, this);

    self.marketPrice = ko.observable(data.marketPrice);
    self.marketPriceCore = ko.computed(function () {
        return self.marketPrice();
    }, this);

    self.marketPriceSub = ko.computed(function () {
        return self.marketPrice();
    }, this);
    self.marketPriceCurrency = ko.observable(data.marketPriceCurrency);
    self.marketPriceCurrencyLoc = ko.computed(function () {
        return currencies[self.marketPriceCurrency()];
    }, this);

    self.buyNowPrice = ko.observable(data.buyNowPrice);
    self.buyNowPriceCore = ko.computed(function () {
        return self.buyNowPrice();
    }, this);

    self.buyNowPriceSub = ko.computed(function () {
        return self.buyNowPrice();
    }, this);
    self.buyNowCurrency = ko.observable(data.buyNowCurrency);

    self.fullBuyNowPrice = ko.computed(function () {
        return self.buyNowPrice() + " " + self.buyNowCurrency();
    }, this);


    self.startTimer = function () {
        setInterval(function () {
            if (self.timer() > 0 && self.state() == 'STARTED') {
                self.timer(self.timer() - 1);
            }
        }, 1000);
    };

    self.startUpcomingTimer = function () {
        setInterval(function () {
            if (self.upcomingTimer() > 0 && self.state() == 'NOT_STARTED') {
                self.upcomingTimer(self.upcomingTimer() - 1);
            }
        }, 1000);
    };

    self.resetTimer = function (value) {
        if (self.state().indexOf("STARTED") == 0) {
            self.timer(value);
        }
    };

    self.update = function (newData) {
        // self.auctionId = ko.observable(data.id);
        self.startDateTime(newData.startDateTime);
        self.endDateTime(newData.endDateTime);
        self.auctionDuration(newData.auctionDuration);
        self.creditsStep(newData.creditsStep);
        self.startPrice(newData.startPrice);
        self.currentBiddingsPrice(newData.currentBiddingsPrice);
        self.state(newData.state);
        self.pictures(newData.pictures);
        self.bidsLen(newData.bidsLen);
        self.itemCurrency(newData.itemCurrency);
        self.itemName(newData.itemName);
        self.itemDescription(newData.itemDescription);
        self.itemLongDescription(newData.itemLongDescription);
        self.retailPrice(newData.retailPrice);
        self.timer(newData.timer);
        self.upcomingTimer(newData.upcomingTimer);
        self.lastBidder(newData.lastBidder);
        self.pictureUrl(newData.pictureUrl);
        self.auctionPrice(newData.auctionPrice);
        self.itemPrice(newData.itemPrice);
        self.buyNowPrice(newData.buyNowPrice);
        self.buyNowCurrency(newData.buyNowCurrency);
        self.marketPrice(newData.marketPrice);
        self.marketPriceCurrency(newData.marketPriceCurrency);

        self.lastBids().unshift({bidder: self.lastBidder()});
        if (self.lastBids().length >= 10) {
            self.lastBids().pop();
        }
    };
}

