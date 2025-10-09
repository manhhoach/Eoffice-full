package com.manhhoach.EofficeFull.repository;

import com.manhhoach.EofficeFull.dto.processFlow.ProcessFlowPagingReq;
import com.manhhoach.EofficeFull.entity.ProcessFlow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessFlowRepository extends JpaRepository<ProcessFlow, Long> {

    @Query("""
            SELECT f from ProcessFlow f WHERE (
            :#{#req.search} IS NULL OR :#{#req.search} = '' OR LOWER(f.name) LIKE LOWER(CONCAT('%', :#{#req.search}, '%'))
            )
            """)
    Page<ProcessFlow> searchFlow(ProcessFlowPagingReq req, Pageable pageable);
}
