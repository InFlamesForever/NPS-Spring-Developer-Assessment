package com.nps.devassessment.repo;

import com.nps.devassessment.entity.WorkflowEntity;
import com.nps.devassessment.entity.WorkflowIdTaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkflowRepo extends PagingAndSortingRepository<WorkflowEntity, Long> {

    Optional<List<WorkflowEntity>> findByWorkflowState(String workflowState);
    Optional<List<WorkflowEntity>> findByYjbYpIn(Collection<Long> yjbYps);
    Optional<List<WorkflowEntity>> findByCreatedAfter(Timestamp timestamp);
    Optional<List<WorkflowEntity>> findByModifiedAfterAndModifiedBefore(Timestamp afterTimestamp, Timestamp beforeTimestamp);
    Optional<List<WorkflowEntity>> findByProcessAndTaskStatusNot(String process, String notTaskStatus);
    Optional<List<WorkflowIdTaskStatus>> findAllByCreatedBy(String createdBy);
    Page<WorkflowEntity> findByProcessOrderByIdDesc(String process, Pageable pageable);
    Optional<WorkflowEntity> findTopByOrderByYjbYpAsc();
}
