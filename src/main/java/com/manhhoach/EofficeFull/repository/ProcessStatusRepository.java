package com.manhhoach.EofficeFull.repository;

import com.manhhoach.EofficeFull.dto.processStatus.ProcessStatusDto;
import com.manhhoach.EofficeFull.dto.processStatus.ProcessStatusPagingReq;
import com.manhhoach.EofficeFull.entity.ProcessStatus;
import com.manhhoach.EofficeFull.entity.ProcessStep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessStatusRepository extends JpaRepository<ProcessStatus, Long> {
    @Query("""
            SELECT f from ProcessStatus f WHERE (
            :#{#req.search} IS NULL OR :#{#req.search} = '' OR LOWER(f.name) LIKE LOWER(CONCAT('%', :#{#req.search}, '%'))
            ) AND :#{#req.processFlowId} = f.processFlow.id
            """)
    Page<ProcessStatus> search(ProcessStatusPagingReq req, Pageable pageable);


    @Query("""
            SELECT new com.manhhoach.EofficeFull.dto.processStatus.ProcessStatusDto(
            p.id, p.name)
            FROM ProcessStatus p 
            WHERE p.processFlow.id = :flowId 
            """)
    List<ProcessStatusDto> getByFlowId(Long flowId);


    @Query("""
            SELECT status FROM ProcessStatus status
            JOIN status.processFlow flow
            WHERE flow.code = :flowCode
            AND status.isStart = true         
            """)
    Optional<ProcessStatus> getStartStatusByFlowCode(String flowCode);
}
