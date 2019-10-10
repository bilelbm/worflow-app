package com.workflow.workflow.repository;

import com.workflow.workflow.domain.Workflow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the {@link Workflow} entity.
 */
@Repository
public interface WorkflowRepository extends JpaRepository<Workflow, Long> {
    @Query("select wf from Workflow wf where " +
            "(:name is null or wf.name = :name) and " +
            "(:idCategories is null or :idCategories in (select idCategory from wf.workflowCategories)) and " +
            "(:status is null or wf.status = :status)"
    )
    Page<Workflow> findAllByNameAndWorkflowCategories_idCategoryInAndStatus(String name, List<Long> idCategories, Integer status, Pageable pageable);
}
