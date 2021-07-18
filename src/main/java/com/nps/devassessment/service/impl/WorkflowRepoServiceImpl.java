package com.nps.devassessment.service.impl;

import com.nps.devassessment.entity.WorkflowEntity;
import com.nps.devassessment.entity.WorkflowIdTaskStatus;
import com.nps.devassessment.repo.WorkflowRepo;
import com.nps.devassessment.service.WorkflowRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;


@Service
public class WorkflowRepoServiceImpl implements WorkflowRepoService {

    private WorkflowRepo workflowRepo;

    @Autowired
    WorkflowRepoServiceImpl(WorkflowRepo workflowRepo) {
        this.workflowRepo = workflowRepo;
    }


    @Override
    public WorkflowEntity findWorkflowById(Long id) {
        return this.workflowRepo.findById(id).orElse(null);
    }

    @Override
    public List<WorkflowEntity> findByWorkflowState(String workflowState) {
        return this.workflowRepo.findByWorkflowState(workflowState).orElse(null);
    }

    @Override
    public List<WorkflowEntity> findByYjbYpIn(Collection<Long> yjbYps) {
        return this.workflowRepo.findByYjbYpIn(yjbYps).orElse(null);
    }

    @Override
    public List<WorkflowEntity> findByCreatedAfter(Timestamp timestamp) {
        return this.workflowRepo.findByCreatedAfter(timestamp).orElse(null);
    }

    @Override
    public List<WorkflowEntity> findByModifiedAfterAndModifiedBefore(Timestamp afterTimestamp, Timestamp beforeTimestamp) {
        return this.workflowRepo.findByModifiedAfterAndModifiedBefore(afterTimestamp, beforeTimestamp).orElse(null);
    }

    @Override
    public List<WorkflowEntity> findByProcessAndTaskStatusNot(String process, String notTaskStatus) {
        return this.workflowRepo.findByProcessAndTaskStatusNot(process, notTaskStatus).orElse(null);
    }

    @Override
    public List<WorkflowIdTaskStatus> findAllByCreatedBy(String createdBy) {
        return this.workflowRepo.findAllByCreatedBy(createdBy).orElse(null);
    }

    @Override
    public List<WorkflowEntity> findByProcessOrderByIdDesc(String process, Pageable pageable) {
        return this.workflowRepo.findByProcessOrderByIdDesc(process, pageable).getContent();
    }

    @Override
    public WorkflowEntity findTopByOrderByYjbYpAsc() {
        return this.workflowRepo.findTopByOrderByYjbYpAsc().orElse(null);
    }

    @Override
    public Page<WorkflowEntity> findAll(Pageable pageable) {
        return this.workflowRepo.findAll(pageable);
    }
}
