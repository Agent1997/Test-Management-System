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
    private Page<RootTestCaseEntity> handleGetPageableTestCasesRequest(Pageable pageable){
        return rootTestCaseService.getPageableTestCases(pageable);
    }

    @PutMapping("/{id}")
    private ResponseEntity<RootTestCaseEntity> handleUpdateTestCaseRequest(@PathVariable String id, @RequestBody RootTestCaseRequestDTO rootTestCaseRequestDTO){
        RootTestCaseEntity updatedTestCase = rootTestCaseService.updateTestCase(id, rootTestCaseRequestDTO);
        return ResponseEntity.ok(updatedTestCase);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> handleDeleteTestCaseRequest(@PathVariable String id){
        if (rootTestCaseService.deleteTestCase(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    private ResponseEntity<HashMap<String, List<String>>> handleBulkDeleteTestCasesRequest(@RequestBody List<String> ids){
        return ResponseEntity.ok(rootTestCaseService.bulkDeleteTestCases(ids));

    }
}
