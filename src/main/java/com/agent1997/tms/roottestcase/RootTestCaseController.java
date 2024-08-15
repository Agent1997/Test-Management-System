package com.agent1997.tms.roottestcase;

import com.agent1997.tms.exceptions.GenericErrorResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/testcase")
@AllArgsConstructor
public class RootTestCaseController {

    private final RootTestCaseRepository rootTestCaseRepository;
    private final RootTestCaseService rootTestCaseService;

    @PostMapping
    private ResponseEntity<RootTestCaseEntity> handleCreateATestCaseRequest(@RequestBody RootTestCaseRequestDTO rootTestCaseRequestDTO){
        RootTestCaseEntity createdTestCase = rootTestCaseService.createATestCase(rootTestCaseRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTestCase);
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> handleGetTestCaseRequest(@PathVariable String id){
        Optional<RootTestCaseEntity> testCaseQueryResult = rootTestCaseService.getTestCase(id);
        if (testCaseQueryResult.isPresent()) return ResponseEntity.ok(testCaseQueryResult.get());
        else return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GenericErrorResponse(HttpStatus.NOT_FOUND,
                String.format("Test case with id: %s is not found.",id),null));
    }

    @GetMapping
    private Page<RootTestCaseEntity> getPageableTestCases(Pageable pageable){
        return rootTestCaseRepository.findAll(pageable);
    }

    @PutMapping("/{id}")
    private ResponseEntity<RootTestCaseEntity> updateTestCase(@PathVariable String id, @RequestBody RootTestCaseRequestDTO rootTestCaseRequestDTO){
        RootTestCaseEntity rootTestCaseEntity = new RootTestCaseEntity();
        rootTestCaseEntity.setId(id);
        rootTestCaseEntity.setTitle(rootTestCaseRequestDTO.getTitle());
        rootTestCaseEntity.setDescription(rootTestCaseRequestDTO.getDescription());
        rootTestCaseEntity.setTestSteps(rootTestCaseRequestDTO.getTestSteps());
        rootTestCaseEntity.setExpectedBehavior(rootTestCaseRequestDTO.getExpectedBehavior());
        rootTestCaseEntity.setNotes(rootTestCaseRequestDTO.getNotes());
        RootTestCaseEntity updatedTestCase = rootTestCaseRepository.save(rootTestCaseEntity);
        return ResponseEntity.ok(updatedTestCase);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteTestCase(@PathVariable String id){
        if (rootTestCaseRepository.existsById(id)) {
            rootTestCaseRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    private ResponseEntity<?> bulkDeleteTestCases(@RequestBody List<String> ids){
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

        if (nonExistingIds.isEmpty()){
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }
    }
}
