package com.madbid.core.service;

import com.madbid.core.model.Message;
import com.madbid.core.model.User;
import com.madbid.core.repository.MadbidRepository;
import com.madbid.core.repository.MessageReposigory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by alexn on 28.10.2014 г..
 */
@Service
@Transactional
public class MessageService extends BaseService<Message> implements ServiceContract<Message> {
    @Autowired
    private MessageReposigory messageReposigory;

    public List<Message> findBySender(User sender, Integer page, Integer pageSize) {
        return messageReposigory.findDistinctBySender(sender, new PageRequest(page, pageSize,
                new Sort(new Sort.Order(Sort.Direction.DESC, "id"))));
    }

    public Long findTotalMessageCount(User user) {
        return messageReposigory.countBySender(user);
    }

    @Override
    public MadbidRepository getRepository() {
        return messageReposigory;
    }
}
