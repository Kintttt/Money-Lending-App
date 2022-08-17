package com.moneylendingapp.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean deleted;

    @CreationTimestamp
    private Timestamp createdOn;

    @UpdateTimestamp
    private Timestamp lastModifiedOn;



}
/** TODO: check how to use the annotations
 * how to make base entity a super class for all my entities
 * edit migration to include the fields from base entity
 * rename the migration
 *
 * ---
 * create a workflow
 *      - anytime there's a pull request to review, run all tests
 *
 */
