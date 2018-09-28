package com.madbid.lazyLoaders;

import com.madbid.core.model.Identifiable;
import com.madbid.core.service.ServiceContract;
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
public abstract class LazyLoaderAdapter<T extends Identifiable> extends LazyDataModel<T> {
    private ServiceContract<T> service;

    public LazyLoaderAdapter(ServiceContract<T> service) {
        this.service = service;
    }

    @Override
    public Object getRowKey(T object) {
        return object.getId();
    }

    @Override
    public T getRowData(String rowKey) {
        return service.findOne(Long.parseLong(rowKey));
    }

    @Override
    @Transactional
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        this.setRowCount((int) service.count());
        this.setPageSize(pageSize);
        if (sortOrder == SortOrder.UNSORTED || StringUtils.isBlank(sortField)) {
            if (filters.size() > 0) {
                // ToDo must calculate by filter
                PageRequest request = new PageRequest(first / pageSize, pageSize);
                return service.findAll(request).getContent();
            } else {
                PageRequest request = new PageRequest(first / pageSize, pageSize);
                return service.findAll(request).getContent();
            }
        } else {
            if (filters.size() > 0) {
                Sort sort = new Sort(sortOrder == SortOrder.ASCENDING ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
                PageRequest request = new PageRequest(first / pageSize, pageSize, sort);
                return service.findAll(request).getContent();
            } else {
                Sort sort = new Sort(sortOrder == SortOrder.ASCENDING ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
                PageRequest request = new PageRequest(first / pageSize, pageSize, sort);
                return service.findAll(request).getContent();
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
