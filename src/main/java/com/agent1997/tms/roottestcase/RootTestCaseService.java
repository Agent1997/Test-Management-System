package com.agent1997.tms.roottestcase;

import com.agent1997.tms.exceptions.DatabaseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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

    public Optional<RootTestCaseEntity> getTestCase(String id){
        return rootTestCaseRepository.findById(id);
    }

    public Page<RootTestCaseEntity> getPageableTestCases(Pageable pageable){
        return rootTestCaseRepository.findAll(pageable);
    }

    public RootTestCaseEntity updateTestCase(String id, RootTestCaseRequestDTO rootTestCaseRequestDTO){
        RootTestCaseEntity rootTestCaseEntity = new RootTestCaseEntity();
        rootTestCaseEntity.setId(id);
        rootTestCaseEntity.setTitle(rootTestCaseRequestDTO.getTitle());
        rootTestCaseEntity.setDescription(rootTestCaseRequestDTO.getDescription());
        rootTestCaseEntity.setTestSteps(rootTestCaseRequestDTO.getTestSteps());
        rootTestCaseEntity.setExpectedBehavior(rootTestCaseRequestDTO.getExpectedBehavior());
        rootTestCaseEntity.setNotes(rootTestCaseRequestDTO.getNotes());
        return rootTestCaseRepository.save(rootTestCaseEntity);
    }

    public Boolean deleteTestCase(String id){
        if (rootTestCaseRepository.existsById(id)) {
            rootTestCaseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public HashMap<String, List<String>> bulkDeleteTestCases(@RequestBody List<String> ids){
        List<String> existingIds = rootTestCaseRepository.findAllById(ids)
                .stream()
                .map(RootTestCaseEntity::getId)
                .toList();
        List<String> nonExistingIds = ids.stream()
                .filter(id -> !existingIds.contains(id))
                .toList();

        rootTestCaseRepository.deleteAllByIdInBatch(existingIds);

        HashMap<String, List<String>> responseBody = new HashMap<>();
        responseBody.put("successfullyDeletedIds", existingIds);
        responseBody.put("nonExistingIds", nonExistingIds);

        return responseBody;
    }
}
