package com.workflow.workflow.service;

import com.workflow.workflow.domain.Workflow;
import com.workflow.workflow.domain.WorkflowCategory;
import com.workflow.workflow.repository.WorkflowCategoryRepository;
import com.workflow.workflow.repository.WorkflowRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for managing workflow.
 */
@Service
@Transactional
public class WorkflowService {
    private final WorkflowRepository workflowRepository;
    private final WorkflowCategoryRepository workflowCategoryRepository;

    public WorkflowService(WorkflowRepository workflowRepository, WorkflowCategoryRepository workflowCategoryRepository) {
        this.workflowRepository = workflowRepository;
        this.workflowCategoryRepository = workflowCategoryRepository;
    }

    public List<WorkflowCategory> getAllWorkflowCategories(){
        return workflowCategoryRepository.findAll();
    }

    public Page<Workflow> getWorkflows(String name, List<Long> categoryIds, Integer status, Pageable pageable){
        return workflowRepository.findAllByNameAndWorkflowCategories_idCategoryInAndStatus(name, categoryIds, status, pageable);
    }

}
