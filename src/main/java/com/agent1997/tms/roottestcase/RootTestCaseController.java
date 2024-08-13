package com.agent1997.tms.roottestcase;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/testcase")
@AllArgsConstructor
public class RootTestCaseController {

    private final RootTestCaseRepository rootTestCaseRepository;

    @PostMapping
    private ResponseEntity<RootTestCaseEntity> createATestCase(@RequestBody RootTestCaseRequestDTO rootTestCaseRequestDTO){
        RootTestCaseEntity rootTestCaseEntity = new RootTestCaseEntity();
        rootTestCaseEntity.setTitle(rootTestCaseRequestDTO.getTitle());
        rootTestCaseEntity.setDescription(rootTestCaseRequestDTO.getDescription());
        rootTestCaseEntity.setTestSteps(rootTestCaseRequestDTO.getTestSteps());
        rootTestCaseEntity.setExpectedBehavior(rootTestCaseRequestDTO.getExpectedBehavior());
        rootTestCaseEntity.setNotes(rootTestCaseRequestDTO.getNotes());
        RootTestCaseEntity saveRootTestCase = rootTestCaseRepository.save(rootTestCaseEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveRootTestCase);
    }

    @GetMapping("/{id}")
    private ResponseEntity<RootTestCaseEntity> getTestCase(@PathVariable String id){
        Optional<RootTestCaseEntity> testCaseQueryResult = rootTestCaseRepository.findById(id);
        return testCaseQueryResult.map(rootTestCaseEntity -> new ResponseEntity<>(rootTestCaseEntity,
                HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
