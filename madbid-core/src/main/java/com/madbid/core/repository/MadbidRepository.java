package com.madbid.core.repository;

import com.madbid.core.model.Identifiable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Created by peter on 7/26/14.
 */
@NoRepositoryBean
public interface MadbidRepository<T extends Identifiable> extends CrudRepository<T, Long>, JpaSpecificationExecutor {
    List<T> findAll();

    List<T> findAll(Sort sort);

    Page<T> findAll(Pageable pageable);

    void flush();

    void saveAndFlush(T entity);
}
