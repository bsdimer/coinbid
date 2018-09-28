package com.madbid.notification.repository;

import com.madbid.notification.model.Notification;
import com.madbid.notification.model.NotificationStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	public static final String FIND_NOTIFICATIONS = "SELECT n FROM Notification n "
		    + "WHERE n.status = ?1 ";

	@Query(FIND_NOTIFICATIONS)
    List<Notification> findNotifications(NotificationStatus status, Pageable notificationsCount);
}
