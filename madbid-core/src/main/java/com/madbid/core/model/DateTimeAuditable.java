package com.madbid.core.model;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

/**
 * Created by dimer on 11/24/14.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class DateTimeAuditable {

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    @CreatedDate
    private LocalDateTime created;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    @LastModifiedDate
    private LocalDateTime modified;

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }
}
