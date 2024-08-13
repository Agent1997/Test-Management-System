package com.agent1997.tms.roottestcase;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "root_test_cases", schema = "tms")
public class RootTestCaseEntity {

    @Id
    @UuidGenerator
    private String id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String title;

    private String description;

    @NotNull
    @NotEmpty
    @NotBlank
    private String testSteps;

    @NotNull
    @NotEmpty
    @NotBlank
    private String expectedBehavior;

    private String notes;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;
}
