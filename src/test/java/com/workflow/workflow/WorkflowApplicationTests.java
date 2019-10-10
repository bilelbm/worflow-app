package com.workflow.workflow;

import com.workflow.workflow.domain.Workflow;
import com.workflow.workflow.domain.WorkflowCategory;
import com.workflow.workflow.repository.WorkflowCategoryRepository;
import com.workflow.workflow.repository.WorkflowRepository;
import com.workflow.workflow.service.WorkflowService;
import com.workflow.workflow.web.rest.WorkflowResource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.MockitoAnnotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link WorkflowResource} REST controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class WorkflowApplicationTests {

    @Autowired
    private WorkflowRepository workflowRepository;

    @Autowired
    private WorkflowCategoryRepository workflowCategoryRepository;

    @Autowired
    private WorkflowService workflowService;

    private Workflow testWorkflow;

    private WorkflowCategory testWorkflowCategory;

    private static Integer DEFAULT_STATUS = 1;

    private static String WORKFLOW_DAFEAULT_NAME = "test-workflow";

    private MockMvc restWorkflowMockMvc;

    /**
     * Configure the mocked resource for this test.
     * <p>
     * this method prepares the mocked rest for the workflow resource.
     */
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        final WorkflowResource workflowMockResource = new WorkflowResource(workflowService);
        this.restWorkflowMockMvc = MockMvcBuilders.standaloneSetup(workflowMockResource)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    /**
     * Create initial entities.
     * <p>
     * this method creates entities used in the test methods.
     */
    @Before
    public void initTest() {
        testWorkflow = new Workflow().name(WORKFLOW_DAFEAULT_NAME).status(DEFAULT_STATUS);
        testWorkflowCategory = new WorkflowCategory().name("test-workflow-category").status(DEFAULT_STATUS);
    }


    @Test
    public void testGetCategories() throws Exception {
        // Initialize the database
        workflowCategoryRepository.saveAndFlush(testWorkflowCategory);
        workflowRepository.saveAndFlush(testWorkflow);

        // Get all the categories
        restWorkflowMockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].idCategory").value(hasItem(testWorkflowCategory.getIdCategory().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(testWorkflowCategory.getName())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(testWorkflowCategory.getDescription())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(testWorkflowCategory.getStatus())))
                .andExpect(jsonPath("$.[*].logo").value(hasItem(testWorkflowCategory.getLogo())))
                .andExpect(jsonPath("$.[*].parentCategory").value(hasItem(testWorkflowCategory.getParentCategory())));
    }

    @Test
    public void testGetWorkflowsWithNoFilter() throws Exception {
        // Initialize the database
        testWorkflowCategory = workflowCategoryRepository.saveAndFlush(testWorkflowCategory);

        // assigning the categories to the current workflow
        List<WorkflowCategory> categories = new ArrayList<>();
        categories.add(testWorkflowCategory);
        testWorkflow.setWorkflowCategories(categories);
        testWorkflow = workflowRepository.save(testWorkflow);

        // Get all the workflows with no filter
        restWorkflowMockMvc.perform(get("/api/workflows"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].idWorkflow").value(hasItem(testWorkflow.getIdWorkflow().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(testWorkflow.getName())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(testWorkflow.getDescription())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(testWorkflow.getStatus())))
                .andExpect(jsonPath("$.[*].variants").value(hasItem(testWorkflow.getVariants())))
                .andExpect(jsonPath("$.[*].workflowCategories.length()").value(hasSize(testWorkflow.getWorkflowCategories().size())));
    }

    @Test
    public void testGetWorkflowsWithNoResult() throws Exception {
        // Initialize the database
        testWorkflowCategory = workflowCategoryRepository.save(testWorkflowCategory);

        // assigning the categories to the current workflow
        List<WorkflowCategory> categories = new ArrayList<>();
        categories.add(testWorkflowCategory);
        testWorkflow.setWorkflowCategories(categories);
        testWorkflow = workflowRepository.save(testWorkflow);

        // Get all the workflows with worng filter
        restWorkflowMockMvc.perform(get("/api/workflows?name=test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string("[]"));
    }

    @Test
    public void testGetWorkflowsWithNameFilter() throws Exception {
        // Initialize the database
        testWorkflowCategory = workflowCategoryRepository.save(testWorkflowCategory);

        // assigning the categories to the current workflow
        List<WorkflowCategory> categories = new ArrayList<>();
        categories.add(testWorkflowCategory);
        testWorkflow.setWorkflowCategories(categories);
        testWorkflow.setName(WORKFLOW_DAFEAULT_NAME);
        testWorkflow = workflowRepository.save(testWorkflow);

        // Get all the workflows with name filter
        restWorkflowMockMvc.perform(get("/api/workflows?name=" + WORKFLOW_DAFEAULT_NAME))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].idWorkflow").value(hasItem(testWorkflow.getIdWorkflow().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(testWorkflow.getName())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(testWorkflow.getDescription())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(testWorkflow.getStatus())))
                .andExpect(jsonPath("$.[*].variants").value(hasItem(testWorkflow.getVariants())))
                .andExpect(jsonPath("$.[*].workflowCategories.length()").value(hasSize(testWorkflow.getWorkflowCategories().size())));
    }

    @Test
    public void testGetWorkflowsWithCategoryFilter() throws Exception {
        // Initialize the database
        testWorkflowCategory = workflowCategoryRepository.save(testWorkflowCategory);

        // assigning the categories to the current workflow
        List<WorkflowCategory> categories = new ArrayList<>();
        categories.add(testWorkflowCategory);
        testWorkflow.setWorkflowCategories(categories);
        testWorkflow.setName(WORKFLOW_DAFEAULT_NAME);
        testWorkflow = workflowRepository.save(testWorkflow);

        // Get all the workflows with categoryIds filter
        restWorkflowMockMvc.perform(get("/api/workflows?categoryIds=" + testWorkflowCategory.getIdCategory()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].idWorkflow").value(hasItem(testWorkflow.getIdWorkflow().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(testWorkflow.getName())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(testWorkflow.getDescription())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(testWorkflow.getStatus())))
                .andExpect(jsonPath("$.[*].variants").value(hasItem(testWorkflow.getVariants())))
                .andExpect(jsonPath("$.[*].workflowCategories.length()").value(hasSize(testWorkflow.getWorkflowCategories().size())));
    }

    @Test
    public void testGetWorkflowsWithStatusFilter() throws Exception {
        // Initialize the database
        testWorkflowCategory = workflowCategoryRepository.save(testWorkflowCategory);

        // assigning the categories to the current workflow
        List<WorkflowCategory> categories = new ArrayList<>();
        categories.add(testWorkflowCategory);
        testWorkflow.setWorkflowCategories(categories);
        testWorkflow.setName(WORKFLOW_DAFEAULT_NAME);
        testWorkflow = workflowRepository.save(testWorkflow);

        // Get all the workflows with status filter
        restWorkflowMockMvc.perform(get("/api/workflows?status=" + DEFAULT_STATUS))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].idWorkflow").value(hasItem(testWorkflow.getIdWorkflow().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(testWorkflow.getName())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(testWorkflow.getDescription())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(testWorkflow.getStatus())))
                .andExpect(jsonPath("$.[*].variants").value(hasItem(testWorkflow.getVariants())))
                .andExpect(jsonPath("$.[*].workflowCategories.length()").value(hasSize(testWorkflow.getWorkflowCategories().size())));
    }

}
