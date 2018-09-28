package com.madbid.notification.service.impl;

import com.madbid.notification.model.Notification;
import com.madbid.notification.model.NotificationStatus;
import com.madbid.notification.model.NotificationType;
import com.madbid.notification.repository.NotificationRepository;
import com.madbid.notification.service.NotificationMessageGenerator;
import com.madbid.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    protected NotificationMessageGenerator notificationGenerator;

    @Autowired
    protected NotificationRepository notificationRepository;

    @Override
    public void submitNotification(String subject, String text, String sender, String receiver, NotificationType type){
        Notification notification = new Notification();
        notification.setType(type);
        notification.setSender(sender);
        notification.setReceiver(receiver);
        notification.setSubject(subject);
        notification.setText(text);

        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> findNotifications(NotificationStatus status, Pageable notificationsCount) {
        return notificationRepository.findNotifications(status, notificationsCount);
    }
    @Override
    public void delete(Iterable<? extends Notification> entities) {
        notificationRepository.delete(entities);
    }
    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }
    @Override
    public void deleteAllInBatch() {
        notificationRepository.deleteAllInBatch();
    }
    @Override
    public Notification getOne(Long aLong) {
        return notificationRepository.getOne(aLong);
    }
    @Override
    public long count() {
        return notificationRepository.count();
    }
    @Override
    public Notification saveAndFlush(Notification entity) {
        return notificationRepository.saveAndFlush(entity);
    }
    @Override
    public List<Notification> findAll(Sort sort) {
        return notificationRepository.findAll(sort);
    }
    @Override
    public void deleteInBatch(Iterable<Notification> entities) {
        notificationRepository.deleteInBatch(entities);
    }
    @Override
    public Page<Notification> findAll(Pageable pageable) {
        return notificationRepository.findAll(pageable);
    }
    @Override
    public void deleteAll() {
        notificationRepository.deleteAll();
    }
    @Override
    public void delete(Long aLong) {
        notificationRepository.delete(aLong);
    }
    @Override
    public Notification save(Notification entity) {
        return notificationRepository.save(entity);
    }
    @Override
    public Notification findOne(Long aLong) {
        return notificationRepository.findOne(aLong);
    }
    @Override
    public List<Notification> findAll(Iterable<Long> longs) {
        return notificationRepository.findAll(longs);
    }
    @Override
    public void delete(Notification entity) {
        notificationRepository.delete(entity);
    }
    @Override
    public void flush() {
        notificationRepository.flush();
    }
    @Override
    public boolean exists(Long aLong) {
        return notificationRepository.exists(aLong);
    }
}
