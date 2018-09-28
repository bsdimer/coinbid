package com.madbid.core.service;

import com.madbid.core.model.User;
import com.madbid.core.model.UserMessage;
import com.madbid.core.repository.MadbidRepository;
import com.madbid.core.repository.UserMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by alexn on 28.10.2014 г..
 */
@Service
@Transactional
public class UserMessageService extends BaseService<UserMessage> implements ServiceContract<UserMessage> {
    @Autowired
    private UserMessageRepository userMessageRepository;

    public List<UserMessage> findByUser(User user) {
        return userMessageRepository.findByUser(user);
    }

    public List<UserMessage> findByUserDesc(User user, Integer page, Integer pageSize) {
        return userMessageRepository.findByUser(user, new PageRequest(page, pageSize,
                new Sort(new Sort.Order(Sort.Direction.DESC, "id"))));
    }

    public List<UserMessage> findByUser(User user, PageRequest pageRequest) {
        return userMessageRepository.findByUser(user, pageRequest);
    }

    public Long findTotalMessageCount(User user) {
        return userMessageRepository.countByUser(user);
    }

    public Long findUnreadMessagesCount(User user) {
        return userMessageRepository.countUnreadMessages(user.getId());
    }

    /**
     *
     * @param messageId
     * @return creation date
     */
    public Date addMessageToAllUsers(long messageId) {
        Date now = new Date();
        userMessageRepository.addMessageToAllUsers(messageId, now);
        return now;
    }

    @Override
    public MadbidRepository getRepository() {
        return userMessageRepository;
    }
}
