package com.wordrace.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 4845892487153272010L;

    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id",columnDefinition = "char(64)")
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}