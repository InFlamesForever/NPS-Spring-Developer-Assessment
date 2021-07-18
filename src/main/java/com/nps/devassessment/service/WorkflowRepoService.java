package com.nps.devassessment.service;

import com.nps.devassessment.entity.WorkflowEntity;
import com.nps.devassessment.entity.WorkflowIdTaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

public interface WorkflowRepoService {

    // fetch an individual workflow by its 'id'
    WorkflowEntity findWorkflowById(Long id);

    List<WorkflowEntity> findByWorkflowState(String workflowState);
    List<WorkflowEntity> findByYjbYpIn(Collection<Long> yjbYps);
    List<WorkflowEntity> findByCreatedAfter(Timestamp timestamp);
    List<WorkflowEntity> findByModifiedAfterAndModifiedBefore(Timestamp afterTimestamp, Timestamp beforeTimestamp);
    List<WorkflowEntity> findByProcessAndTaskStatusNot(String process, String notTaskStatus);
    List<WorkflowIdTaskStatus> findAllByCreatedBy(String createdBy);
    List<WorkflowEntity> findByProcessOrderByIdDesc(String process, Pageable pageable);
    WorkflowEntity findTopByOrderByYjbYpAsc();
    Page<WorkflowEntity> findAll(Pageable pageable);

}
