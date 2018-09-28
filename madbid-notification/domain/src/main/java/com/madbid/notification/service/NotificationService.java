package com.madbid.notification.service;

import com.madbid.notification.model.Notification;
import com.madbid.notification.model.NotificationStatus;
import com.madbid.notification.model.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface NotificationService {

    void submitNotification(String subject, String text, String sender, String receiver, NotificationType type);

    List<Notification> findNotifications(NotificationStatus status, Pageable notificationsCount);

    void delete(Iterable<? extends Notification> entities);

    List<Notification> findAll();

    void deleteAllInBatch();

    Notification getOne(Long aLong);

    long count();

    Notification saveAndFlush(Notification entity);

    List<Notification> findAll(Sort sort);

    void deleteInBatch(Iterable<Notification> entities);

    Page<Notification> findAll(Pageable pageable);

    void deleteAll();

    void delete(Long aLong);

    Notification save(Notification entity);

    Notification findOne(Long aLong);

    List<Notification> findAll(Iterable<Long> longs);

    void delete(Notification entity);

    void flush();

    boolean exists(Long aLong);
}
