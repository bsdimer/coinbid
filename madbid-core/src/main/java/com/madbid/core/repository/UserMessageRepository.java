package com.madbid.core.repository;

import com.madbid.core.model.User;
import com.madbid.core.model.UserMessage;
import org.hibernate.exception.DataException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by alexn on 28.10.2014 г..
 */
public interface UserMessageRepository extends MadbidRepository<UserMessage> {
    public List<UserMessage> findByUser(@Param("user") User user);
    public List<UserMessage> findByUser(@Param("user") User user, Pageable page);
    public long countByUser(@Param("user") User user);


    @Query(nativeQuery = true, value = "select count(m.id) from user_messages m where m.user_id = ?1 and m.is_read = false")
    public long countUnreadMessages(Long userId);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO user_messages (created, modified, is_read, message_id, user_id) SELECT DISTINCT ?2, ?2, 0, ?1, id FROM users")
    public void addMessageToAllUsers(Long messageId, Date date);
}
