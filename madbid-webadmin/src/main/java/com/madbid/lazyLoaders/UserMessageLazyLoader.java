package com.madbid.lazyLoaders;

import com.madbid.core.model.User;
import com.madbid.core.model.UserMessage;
import com.madbid.core.service.UserMessageService;
import org.apache.commons.lang.StringUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by dimer on 8/17/14.
 */
public class UserMessageLazyLoader extends LazyDataModel<UserMessage> {

    private UserMessageService service;
    private User user;

    public UserMessageLazyLoader(UserMessageService service, User user) {
        this.service = service;
        this.user = user;
    }

    @Override
    public Object getRowKey(UserMessage object) {
        return object.getId();
    }

    @Override
    public UserMessage getRowData(String rowKey) {
        return service.findOne(Long.parseLong(rowKey));
    }

    @Override
    @Transactional
    public List<UserMessage> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        this.setRowCount((int) service.count());
        this.setPageSize(pageSize);
        if (sortOrder == SortOrder.UNSORTED || StringUtils.isBlank(sortField)) {
            if (filters.size() > 0) {
                // ToDo must calculate by filter
                return service.findByUserDesc(user, first / pageSize, pageSize);
            } else {
                return service.findByUserDesc(user, first / pageSize, pageSize);
            }
        } else {
            if (filters.size() > 0) {
                Sort sort = new Sort(sortOrder == SortOrder.ASCENDING ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
                PageRequest request = new PageRequest(first / pageSize, pageSize, sort);
                return service.findByUser(user, request);
            } else {
                Sort sort = new Sort(sortOrder == SortOrder.ASCENDING ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
                PageRequest request = new PageRequest(first / pageSize, pageSize, sort);
                return service.findByUser(user, request);
            }
        }
    }

    @Override
    public void setRowIndex(int rowIndex) {
        if (rowIndex == -1 || getPageSize() == 0) {
            super.setRowIndex(-1);
        } else
            super.setRowIndex(rowIndex % getPageSize());
    }
}
