INSERT INTO `users` VALUES (1,'2014-08-08 16:43:06','2014-08-08 16:43:06',1,'thunderbid123@gmail.com','Test','Testov','0883494949',1,'$2a$10$N36DiYG8xoMnosaK5.CVce17ZJJU0CEQF.O6ShACLakm2H2o091re','ROLE_ADMIN',NULL,'thunderbid123');
INSERT INTO `users` VALUES (2,'2014-08-08 16:43:06','2014-08-08 16:43:06',1,'sasho_tgh@abv.bg','Al','Nik','0883444444',1,'$2a$10$N36DiYG8xoMnosaK5.CVce17ZJJU0CEQF.O6ShACLakm2H2o091re','ROLE_USER',NULL,'sasho_tgh');
INSERT INTO `users` VALUES (3,'2014-08-08 16:43:06','2014-08-08 16:43:06',1,'ddimitrov@abv.bg','Dimo','Dim','0883443424',1,'$2a$10$xa1eWIeamuwiMy1uaUWfzeZQtKtKvSAF4dkbf.H0l6sSBWinxdq7a','ROLE_USER',NULL,'ddimitrov');
INSERT INTO `users` VALUES (4,'2014-08-08 16:43:06','2014-08-08 16:43:06',1,'ppetrov@abv.bg','Petar','Petrov','0883443424',1,'$2a$10$xa1eWIeamuwiMy1uaUWfzeZQtKtKvSAF4dkbf.H0l6sSBWinxdq7a','ROLE_BB',NULL,'ppetrov');
INSERT INTO `users` VALUES (5,'2014-08-08 16:43:06','2014-08-08 16:43:06',1,'mj23slam@abv.bg','Michael','Jordan','0883443424',1,'$2a$10$xa1eWIeamuwiMy1uaUWfzeZQtKtKvSAF4dkbf.H0l6sSBWinxdq7a','ROLE_BB',NULL,'mj23slam');

insert into coinPackets values (1,'2014-08-08 16:43:06','2014-08-08 16:43:06',"BGL",20,20,2,1,1);
insert into coinPackets values (2,'2014-08-08 16:43:06','2014-08-08 16:43:06',"BGL",100,100,8,1,1);
insert into coinPackets values (3,'2014-08-08 16:43:06','2014-08-08 16:43:06',"BGL",500,500,30,1,1);
insert into coinPackets values (4,'2014-08-08 16:43:06','2014-08-08 16:43:06',"BGL",2000,2000,80,1,1);
insert into coinPackets values (5,'2014-08-08 16:43:06','2014-08-08 16:43:06',"BGL",10000,10000,200,1,1);

insert into notification_templates values (1, 'itemPaymentReminder', 'Скъпи ${User_firstName} ${User_lastName},<br/>За плащането на спечеленената от вас стока остават ${days_remain} дни<br/><br/>Благодарим Ви,<br/>Администратор<br/>');
insert into notification_templates values (2, 'lossItem', 'Скъпи ${User_firstName} ${User_lastName},<br/>Тъй като минаха ${days} дни, в които трябваше да платите.... вие губите този продукт <br/><br/>Искрено Ваш,<br/>Администратор<br/>');
insert into notification_templates values (3, 'userRegistrationConfirmation', 'Скъпи ${User_firstName} ${User_lastName},<br/><br/>Вие се регистрирахте успешно! За да потвърдите Вашата регистрация отворете линкът по-долу:<br/>${confirmation_link}<br/><br/>Благодарим Ви,<br/>Администратор<br/>');
insert into notification_templates values (4, 'winningAuction', 'Поздравления ${User_firstName} ${User_lastName}, Вие спечелихте аукцион с ИД:${Auction_id} <br/>Артикул ${Item_name} <br/>Можете да закупите вашия артикул в срок от 14 дни. След изтичането на този срок артикулът се счита за загубен.<br/>След успешното получаване на вашия артикул можете да изпратите снимка, в която сте с артикула и ние ще ви подарим 10 кредита.<br/>За да изпратите снимката си отидете на Профил->Аукционни->Добави снимка.<br/>След удобрението на снимката от наш сътрудник вие ще получите 10 кредита.<br/><br/>Благодарим Ви,<br/>Администратор<br/>');
insert into notification_templates values (5, 'currentAuctionsCampaign', '<div style="background-color:#ef7a00" bgcolor="#EF7A00"><div style="background-color:#ef7a00" bgcolor="#EF7A00"><center><table width="700" cellspacing="0" cellpadding="0" style="margin:0px auto"><tbody><tr><td colspan="2" height="40" valign="middle"><div style="text-align:left;padding-left:20px;font-family:verdana,sans-serif;font-size:11px;line-height:1.1">За отписване(TODO) <a href="" style="color:#fff;text-decoration:none" target="_blank">кликнете тук</a>.</div></td></tr><tr>		<td colspan="2" valign="middle" align="center" height="80" bgcolor="#ffffff"><a href="http://mallbg.com/?utm_source=mallbg.com&amp;utm_medium=mail&amp;utm_term=промоция&amp;utm_campaign=weekly_20141104" style="color:#ffffff;font-size:0px;text-decoration:none" target="_blank"><img src="https://ci6.googleusercontent.com/proxy/EokUwIAPrg07Zg3MPX-2Wj--EAJ3KXiIu8Syz6-17qOAt2rpzRYh3Bo31cg2MlchLu3K7VvXAIE_w5BoBgw_=s0-d-e1-ft#http://mallbg.com//images/mallbg_logo.jpg" border="0" alt="Mallbg.com" height="100" style="min-height:100px;border:0px solid;margin:0px;padding:0px" class="CToWUd"></a></td>	</tr><tr><td colspan="2" bgcolor="#ffffff" style="padding:0 20px"><table style="margin-top:10px" cellspacing="0" cellpadding="0" border="0"><tbody>#foreach( $auction in $auctions )<tr><td width="180" style="padding-bottom:10px;padding-right:15px"><a href="http://mallbg.com//Мастилоструен-принтер-Canon-PIXMA-iX6850-BS8747B006AA_45173.html?utm_source=mallbg.com&amp;utm_medium=mail&amp;utm_term=промоция&amp;utm_campaign=weekly_20141104" target="_blank"><img src="${image}$auction.itemInventory.item.pictures.get(0).id" border="0" height="150" alt="" class="CToWUd"></a></td><td valign="top"><a href="http://mallbg.com//Мастилоструен-принтер-Canon-PIXMA-iX6850-BS8747B006AA_45173.html?utm_source=mallbg.com&amp;utm_medium=mail&amp;utm_term=промоция&amp;utm_campaign=weekly_20141104" style="font-size:18px;font-family:arial,sans-serif;text-decoration:none;color:#167bbf" target="_blank">$auction.itemInventory.item.name										</a><table style="margin-top:5px"><tbody><tr><td style="font-family:arial,sans-serif;font-size:14px;padding-right:10px" align="right">Продължителност:</td><td style="font-family:arial,sans-serif;font-size:14px"><b>$auction.auctionDuration</b></td></tr><tr><td style="font-family:arial,sans-serif;font-size:14px;padding-right:10px" align="right">Стъпка:</td><td style="font-family:arial,sans-serif;font-size:18px"><b>$auction.creditsStep</b></td></tr></tbody></table></td></tr>#end</tbody></table>					</td></tr><tr><td colspan="2" valign="middle"><div style="text-align:left;padding-left:20px;font-family:verdana,sans-serif;font-size:11px;line-height:1.1">Съгласно закона за електронна търговия Чл. 6, ал. 1 Ви уведомяваме, че е възможно това да е непоискано търговско съобщение. <br>Вие получавате този мейл като наш клиент пазарувал поне веднъж от <a href="http://mallbg.com" target="_blank">mallbg.com</a><br>Извиняваме се за причиненото неудобство, ако сме Ви притеснили с нашето предложение. <br>Ако не желаете да получавате повече съобщения от нас, моля посетете <a href="*|UNSUB|*" style="font-size:18px;font-family:arial,sans-serif;text-decoration:none;color:#167bbf" target="_blank">този линк</a>.</div></td></tr></tbody></table></center><div class="yj6qo"></div><div class="adL"></div></div><div class="adL"></div></div>');
insert into notification_templates values (6, 'winnerPictureReceived', 'Добавена е снимка от победител на аукцион.<br/><br/>Име: ${User_firstName} ${User_lastName}<br/>Потребителско име: ${User_username}<br/>Емайл: ${User_email}<br/><br/>Аукцион<br/>Номер: ${Auction_id}<br/>Стратиран на: ${Auction_startDateTime}<br/>Приключил на: ${Auction_endDateTime}<br/><br/>Артикул<br/>Номер: ${ItemInventory_serialNumber}<br/>Име: ${Item_name}<br/>Цена: ${Item_retailPrice}<br/>Валута: ${Item_retailPriceCurrency}<br/><br/>Снимка<br/><img src="${image}" border="0" height="350"><br/><br/><a href="${approve_winner_picture_link}">Одобри снимката</а>');

insert into placeholder_references values (1,'com.madbid.core.model.User', 1);
insert into placeholder_references values (2,'com.madbid.core.model.User', 2);
insert into placeholder_references values (3,'com.madbid.core.model.User', 3);
insert into placeholder_references values (4,'com.madbid.core.model.Auction', 4);
insert into placeholder_references values (5,'com.madbid.core.model.Item', 4);
insert into placeholder_references values (6,'com.madbid.core.model.User', 4);
insert into placeholder_references values (7,'com.madbid.core.model.Auction', 5);
insert into placeholder_references values (8,'com.madbid.core.model.Auction', 6);
insert into placeholder_references values (9,'com.madbid.core.model.Item', 6);
insert into placeholder_references values (10,'com.madbid.core.model.ItemInventory', 6);
insert into placeholder_references values (11,'com.madbid.core.model.User', 6);

insert into notification_campaigns values (1, '0 0 8 ? * Mon', 'com.madbid.admin.schedule.notification.CurrentAuctionsJob', 'current_auctions_campaign', 0);
insert into notification_campaigns values (2, '0 0 8 ? * Sun', 'com.madbid.admin.schedule.notification.AllWeekEventsJob', 'all_week_events', 0);

INSERT INTO messages VALUES (1, '2014-08-08 16:43:06', null, 'test subject1', 'test text1',2);
INSERT INTO messages VALUES (2, '2014-08-09 16:43:06', null, 'test subject2', 'test text2',2);
INSERT INTO messages VALUES (3, '2014-08-10 16:43:06', null, 'test subject3', 'test text3',2);
INSERT INTO messages VALUES (4, '2014-08-11 16:43:06', null, 'test subject4', 'test text4',2);
INSERT INTO messages VALUES (5, '2014-08-12 16:43:06', null, 'test subject5', 'test text5',2);
INSERT INTO messages VALUES (6, '2014-08-13 16:43:06', null, 'test subject6', 'test text6',2);
INSERT INTO messages VALUES (7, '2014-08-14 16:43:06', null, 'test subject7', 'test text7',2);
INSERT INTO messages VALUES (8, '2014-08-08 16:43:06', null, 'test subject admin1', 'test text admin1',1);
INSERT INTO messages VALUES (9, '2014-08-08 16:43:06', null, 'test subject admin2', 'test text admin2',1);

INSERT INTO user_messages VALUES (1, '2014-08-08 16:43:06', null, 0, 1, 1);
INSERT INTO user_messages VALUES (2, '2014-08-09 11:43:06', null, 0, 2, 1);
INSERT INTO user_messages VALUES (3, '2014-08-10 12:43:06', null, 0, 3, 1);
INSERT INTO user_messages VALUES (4, '2014-08-11 16:43:06', null, 0, 4, 1);
INSERT INTO user_messages VALUES (5, '2014-08-12 16:43:06', null, 0, 5, 1);
INSERT INTO user_messages VALUES (6, '2014-08-13 16:43:06', null, 0, 6, 1);
INSERT INTO user_messages VALUES (7, '2014-08-14 16:43:06', null, 0, 7, 1);
INSERT INTO user_messages VALUES (8, '2014-08-08 16:43:06', null, 0, 8, 2);
INSERT INTO user_messages VALUES (9, '2014-08-08 16:43:06', null, 0, 9, 2);

