package com.wordrace.model;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}
