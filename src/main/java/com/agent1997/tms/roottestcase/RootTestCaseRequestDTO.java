package com.agent1997.tms.roottestcase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RootTestCaseRequestDTO {
    String title;
    String description;
    String testSteps;
    String expectedBehavior;
    String notes;
}
