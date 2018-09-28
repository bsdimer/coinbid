package com.madbid.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by dimer on 10/3/14.
 */
public interface ServiceContract<T> {
    public List<T> findAll();

    public List<T> findAll(Sort sort);

    public Page<T> findAll(Pageable pageable);

    public boolean exists(Long aLong);

    public T save(T entity);

    public void delete(T entity);

    public long count();

    public void flush();

    public T findOne(Long aLong);
}
