package com.workflow.workflow.repository;

import com.workflow.workflow.domain.WorkflowCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link WorkflowCategory} entity.
 */
@Repository
public interface WorkflowCategoryRepository extends JpaRepository<WorkflowCategory, Long> {
}
