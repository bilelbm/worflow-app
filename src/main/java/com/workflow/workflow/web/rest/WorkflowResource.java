package com.workflow.workflow.web.rest;

import com.workflow.workflow.domain.Workflow;
import com.workflow.workflow.domain.WorkflowCategory;
import com.workflow.workflow.service.WorkflowService;
import com.workflow.workflow.service.util.PaginationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * REST controller for managing workflow.
 */
@RestController
@RequestMapping("/api")
public class WorkflowResource {
    private final WorkflowService workflowService;

    public WorkflowResource(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    /**
     * {@code GET  /categories} : get the categories.
     *
     * @return the list of categories.
     */
    @GetMapping("/categories")
    public List<WorkflowCategory> getAllCategories() {
        return workflowService.getAllWorkflowCategories();
    }

    /**
     * {@code GET  /workflows} : get the list of workflows.
     *
     * @param name
     * @param categoryIds
     * @param status
     * @param pageable
     * @return the list of workflows.
     */
    @GetMapping("/workflows")
    public ResponseEntity<List<Workflow>> getAllWorkflows(@RequestParam(required = false) String name, @RequestParam(required = false) List<Long> categoryIds, @RequestParam(required = false) @Min(1) @Max(5) Integer status, Pageable pageable) {
        final Page<Workflow> page = workflowService.getWorkflows(name, categoryIds, status, pageable);
        HttpHeaders headers = PaginationUtils.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
