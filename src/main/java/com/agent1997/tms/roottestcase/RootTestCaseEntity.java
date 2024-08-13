package com.agent1997.tms.roottestcase;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Data
@Entity
@Table(name = "root_test_cases", schema = "tms")
public class RootTestCaseEntity {

    @Id
    @UuidGenerator
    private String id;

    private String title;

    private String description;

    private String testSteps;

    private String expectedBehavior;

    private String notes;

    private Date createdAt;

    private Date updatedAt;
}
