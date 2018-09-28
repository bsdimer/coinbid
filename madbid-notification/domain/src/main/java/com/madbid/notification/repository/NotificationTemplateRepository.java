package com.madbid.notification.repository;

import com.madbid.notification.model.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by nikolov.
 */
public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long> {
    public NotificationTemplate findByName(@Param("name") String name);
}
