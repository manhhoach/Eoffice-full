package com.manhhoach.EofficeFull.repository;

import com.manhhoach.EofficeFull.dto.processStatus.ProcessStatusPagingReq;
import com.manhhoach.EofficeFull.dto.processStep.ProcessStepPagingReq;
import com.manhhoach.EofficeFull.entity.ProcessStatus;
import com.manhhoach.EofficeFull.entity.ProcessStep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessStepRepository extends JpaRepository<ProcessStep, Long> {
    @Query("""
            SELECT f from ProcessStep f WHERE (
            :#{#req.search} IS NULL OR :#{#req.search} = '' OR LOWER(f.name) LIKE LOWER(CONCAT('%', :#{#req.search}, '%'))
            )
            """)
    Page<ProcessStep> search(ProcessStepPagingReq req, Pageable pageable);
}
