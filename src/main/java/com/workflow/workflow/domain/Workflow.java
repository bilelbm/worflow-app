package com.workflow.workflow.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A workflow.
 */
@Entity
@Table(name = "workflow")
public class Workflow implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long idWorkflow;

    @Column(name = "name")
    private String name;

    private String description;

    @Min(1)
    @Max(5)
    private Integer status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="variants",
            joinColumns=@JoinColumn(name="IdWorkflow"),
            inverseJoinColumns=@JoinColumn(name="IdVariant")
    )
    private List<Workflow> variants = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="variants",
            joinColumns=@JoinColumn(name="IdVariant"),
            inverseJoinColumns=@JoinColumn(name="IdWorkflow")
    )
    @Transient
    @JsonIgnore
    private List<Workflow> variantOf = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<WorkflowCategory> workflowCategories;

    public Long getIdWorkflow() {
        return idWorkflow;
    }

    public void setIdWorkflow(Long idWorkflow) {
        this.idWorkflow = idWorkflow;
    }

    public Workflow idWorkflow(Long idWorkflow){
        this.idWorkflow = idWorkflow;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Workflow name(String name){
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Workflow Description(String description){
        this.description = description;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Workflow status(Integer status){
        this.status = status;
        return this;
    }

    public List<Workflow> getVariants() {
        return variants;
    }

    public void setVariants(List<Workflow> variants) {
        this.variants = variants;
    }

    public Workflow variants(List<Workflow> variants) {
        this.variants = variants;
        return this;
    }

    public List<Workflow> getVariantOf() {
        return variantOf;
    }

    public void setVariantOf(List<Workflow> variantOf) {
        this.variantOf = variantOf;
    }

    public List<WorkflowCategory> getWorkflowCategories() {
        return workflowCategories;
    }

    public void setWorkflowCategories(List<WorkflowCategory> workflowCategories) {
        this.workflowCategories = workflowCategories;
    }

    public Workflow workflowCategories(List<WorkflowCategory> workflowCategories){
        this.workflowCategories = workflowCategories;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Workflow)) {
            return false;
        }
        return idWorkflow != null && idWorkflow.equals(((Workflow) o).idWorkflow);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Workflow{" +
                "IdWorkflow=" + idWorkflow +
                ", Name='" + name + '\'' +
                ", Description='" + description + '\'' +
                ", status=" + status +
                ", variants=" + variants +
                ", variantOf=" + variantOf +
                ", workflowCategories=" + workflowCategories +
                '}';
    }
}
