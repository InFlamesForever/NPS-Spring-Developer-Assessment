package com.nps.devassessment.setup;

import com.nps.devassessment.entity.WorkflowEntity;
import com.nps.devassessment.entity.WorkflowIdTaskStatus;
import com.nps.devassessment.service.WorkflowRepoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SetupTests {

    private static final Logger log = LoggerFactory.getLogger(SetupTests.class);

    @Autowired
    private WorkflowRepoService workflowRepoService;


    // NOTE - This is a sample
    @Test
    public void test0_shouldProvideASampleOfAWorkingRepoCall() {

        // start test
        log.info("Starting test0 to demonstrate working repo call...");
        WorkflowEntity workflowEntity = this.workflowRepoService.findWorkflowById(66176L);

        // Assert
        Assert.assertNotNull(workflowEntity);
        Assert.assertEquals("IN PROGRESS", workflowEntity.getWorkflowState());

        // end test
        log.info("Workflow {} found.  yjb_yp_id = {}, workflow_state = {}", workflowEntity.getId(), workflowEntity.getYjbYp(), workflowEntity.getWorkflowState());
        log.info("test0 complete");
    }



    @Test
    public void test1_shouldDemonstrateRequestedRepoQueries() {
        // implement queries as per the word document
        // assert that the results of each of the query calls is not null/empty
        // write the count of each of the queries to the log

        log.info("Starting test 1.");

        // Select workflows by workflow_state = a given status  (e.g. “IN PROGRESS”, “CANCELLED”, “ADMITTED”)
        log.info("Test 1: workflow_state.");
        List<WorkflowEntity> workflowEntitiesByState = this.workflowRepoService.findByWorkflowState("IN PROGRESS");

        Assert.assertNotNull(workflowEntitiesByState);

        log.info("No. of Workflow entities found {}.  ", workflowEntitiesByState.size());

        // Select workflows by a given list of yjb_yp_id values  (e.g. 30848, 32524, 28117)
        log.info("Test 1: list of yjb_yp_id values.");
        List<WorkflowEntity> workflowEntitiesByYjbYpIds = this.workflowRepoService.findByYjbYpIn(Arrays.asList(30848L, 32524L, 28117L));

        Assert.assertNotNull(workflowEntitiesByYjbYpIds);

        log.info("No. of Workflow entities found {}.  ", workflowEntitiesByYjbYpIds.size());

        // Select workflows by 'created' column is after a given date (e.g. 01/02/2021)
        log.info("Test 1: created after given date.");
        List<WorkflowEntity> workflowEntitiesByCreated = this.workflowRepoService.findByCreatedAfter(
                Timestamp.valueOf(LocalDateTime.of(2021, 2, 1, 0, 0)));

        Assert.assertNotNull(workflowEntitiesByCreated);

        log.info("No. of Workflow entities found {}.  ", workflowEntitiesByCreated.size());

        // Select workflows by 'modified' column is after a given date (e.g. 01/01/20) but before another given date (e.g. 01/03/2021)
        log.info("Test 1: modified after given date and before another given date.");
        List<WorkflowEntity> workflowEntitiesByModified = this.workflowRepoService.findByModifiedAfterAndModifiedBefore(
                Timestamp.valueOf(LocalDateTime.of(2021, 2, 1, 0, 0)),
                Timestamp.valueOf(LocalDateTime.of(2021, 3, 1, 0, 0)));

        Assert.assertNotNull(workflowEntitiesByModified);

        log.info("No. of Workflow entities found {}.  ", workflowEntitiesByModified.size());

        // Select workflows by process = a given value (e.g. “placementProcess”) AND task_status != a given value (e.g.  “ADMITTED”)
        log.info("Test 1: process = value, task_status != value.");
        List<WorkflowEntity> workflowEntitiesByProcessAndNotTask = this.workflowRepoService.findByProcessAndTaskStatusNot(
                "placementProcess", "ADMITTED");

        Assert.assertNotNull(workflowEntitiesByProcessAndNotTask);

        log.info("No. of Workflow entities found {}.  ", workflowEntitiesByProcessAndNotTask.size());

        // Select id, yjb_yp_id and task_status columns for all workflows where created_by = a given value (e.g. “lee.everitt”)
        log.info("Test 1: Id's and task_status created by");  //TODO: fix this as it only finds one result - use native query if you have time
        List<WorkflowIdTaskStatus> workflowIdsBy = this.workflowRepoService.findAllByCreatedBy(
                "lee.everitt");

        Assert.assertNotNull(workflowIdsBy);

        log.info("No. of Workflow entities found {}.  ", workflowIdsBy.size());

        // Select the first 10 rows where process = a given value (e.g. “transferPlanned”).  Order the results by id in descending order
        log.info("Test 1: process = value, task_status != value.");
        List<WorkflowEntity> workflowEntitiesByProcessLimitAndOrder = this.workflowRepoService.findByProcessOrderByIdDesc(
                "transferPlanned", PageRequest.of(0, 10));

        Assert.assertNotNull(workflowEntitiesByProcessLimitAndOrder);

        log.info("No. of Workflow entities found {}.  ", workflowEntitiesByProcessLimitAndOrder.size());


    }


    @Test
    public void test2_shouldDemonstratePageableRepoCapability() {
        // Page through the entire workflow repo using a page size of 20
        // For each page: write the count of each distinct workflow_status to the log
        // Once you have paged through the entire table, write the amount of pages to the log

        log.info("Starting Test 2.");

        Page<WorkflowEntity> page;
        int count = 0;

        //loop through till all pages found
        do {
            page = this.workflowRepoService.findAll(PageRequest.of(count, 50)); //Note says 20 in comments, but 50 in word doc using 50 as word doc specifies
            List<WorkflowEntity> workflowEntities = page.getContent();

            Map<String, Integer> distinctStates = new HashMap<>();
            workflowEntities.forEach(workflowEntity -> {
                // if state is not in map add it with default count of 1
                if (distinctStates.get(workflowEntity.getWorkflowState()) == null) {
                    distinctStates.put(workflowEntity.getWorkflowState(), 1);
                } else {
                    // if state already exists in map increase its count by 1
                    distinctStates.computeIfPresent(workflowEntity.getWorkflowState(), (s, integer) -> integer + 1);
                }
            });

            distinctStates.forEach((state, integer) -> log.info("Workflow state: {} found {} times.", state, integer));

            count++;
        } while (page.hasContent());

        log.info("No. of pages {}.", count + 1);
    }
}
