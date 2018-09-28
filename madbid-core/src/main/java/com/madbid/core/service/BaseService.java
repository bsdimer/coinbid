package com.madbid.core.service;

import com.madbid.core.repository.MadbidRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by nikolov.
 */
public abstract class BaseService<T extends Object> {

    public List<T> findAll() {
        return getRepository().findAll();
    }

    public List<T> findAll(Sort sort) {
        return getRepository().findAll(sort);
    }

    public boolean exists(Long aLong) {
        return getRepository().exists(aLong);
    }

    public T save(T entity) {
        return (T) getRepository().save(entity);
    }

    public void delete(T entity) {
        getRepository().delete(entity);
    }


    public Page<T> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    public long count() {
        return getRepository().count();
    }

    public void flush() {
        getRepository().flush();
    }

    public T findOne(Long aLong) {
        return (T) getRepository().findOne(aLong);
    }

    public abstract MadbidRepository getRepository();

    public void save(Iterable<T> iterable) {
        getRepository().save(iterable);
    }
}
