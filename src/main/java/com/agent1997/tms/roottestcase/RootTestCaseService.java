package com.agent1997.tms.roottestcase;

import com.agent1997.tms.exceptions.DatabaseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class RootTestCaseService {

    RootTestCaseRepository rootTestCaseRepository;

    public RootTestCaseEntity createATestCase(RootTestCaseRequestDTO rootTestCaseRequestDTO) throws DatabaseException{
        RootTestCaseEntity rootTestCaseEntity = new RootTestCaseEntity();
        rootTestCaseEntity.setTitle(rootTestCaseRequestDTO.getTitle());
        rootTestCaseEntity.setDescription(rootTestCaseRequestDTO.getDescription());
        rootTestCaseEntity.setTestSteps(rootTestCaseRequestDTO.getTestSteps());
        rootTestCaseEntity.setExpectedBehavior(rootTestCaseRequestDTO.getExpectedBehavior());
        rootTestCaseEntity.setNotes(rootTestCaseRequestDTO.getNotes());

        try{
            return rootTestCaseRepository.save(rootTestCaseEntity);
        }catch (DataIntegrityViolationException e){
            log.error("Failed to create test case: {}", rootTestCaseEntity, e);
            throw new DatabaseException("Failed to create a test case.", e);
        }
    }
}
