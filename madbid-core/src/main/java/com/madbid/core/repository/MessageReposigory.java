package com.madbid.core.repository;

import com.madbid.core.model.Message;
import com.madbid.core.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by alexn on 28.10.2014 г..
 */
public interface MessageReposigory extends MadbidRepository<Message> {
    public List<Message> findDistinctBySender(@Param("sender") User sender, Pageable page);
    public long countBySender(@Param("sender") User sender);
}
