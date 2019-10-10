package com.workflow.workflow.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A workflow category.
 */
@Entity
@Table(name = "workflow_category")
public class WorkflowCategory extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long idCategory;

    private String name;

    private String description;

    private String logo;

    @Min(1)
    @Max(5)
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private WorkflowCategory parentCategory;

    @OneToMany(mappedBy="ParentCategory", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    @Transient
    @JsonIgnore
    private List<WorkflowCategory> subWorkflowCategories = new ArrayList<>();;

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public WorkflowCategory idCategory(Long idCategory) {
        this.idCategory = idCategory;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WorkflowCategory name(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public WorkflowCategory Description(String description){
        this.description = description;
        return this;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public WorkflowCategory logo(String logo){
        this.logo = logo;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public WorkflowCategory status(Integer status){
        this.status = status;
        return this;
    }

    public WorkflowCategory getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(WorkflowCategory parentCategory) {
        this.parentCategory = parentCategory;
    }

    public List<WorkflowCategory> getSubWorkflowCategories() {
        return subWorkflowCategories;
    }

    public void setSubWorkflowCategories(List<WorkflowCategory> subWorkflowCategories) {
        this.subWorkflowCategories = subWorkflowCategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkflowCategory)) {
            return false;
        }
        return idCategory != null && idCategory.equals(((WorkflowCategory) o).idCategory);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "WorkflowCategory{" +
                "idCategory=" + idCategory +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", logo='" + logo + '\'' +
                ", status=" + status +
                ", parentCategory=" + parentCategory +
                ", subWorkflowCategories=" + subWorkflowCategories +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}
