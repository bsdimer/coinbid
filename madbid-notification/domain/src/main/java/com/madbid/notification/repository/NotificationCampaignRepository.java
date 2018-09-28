package com.madbid.notification.repository;

import com.madbid.notification.model.NotificationCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface NotificationCampaignRepository extends JpaRepository<NotificationCampaign, Long> {
    NotificationCampaign findByName(@Param("name") String name);
}
