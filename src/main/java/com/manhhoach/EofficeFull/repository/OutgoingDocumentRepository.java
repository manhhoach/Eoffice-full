package com.manhhoach.EofficeFull.repository;

import com.manhhoach.EofficeFull.dto.department.DepartmentPagingReq;
import com.manhhoach.EofficeFull.dto.outgoingDocument.OutgoingDocPagingReq;
import com.manhhoach.EofficeFull.entity.Department;
import com.manhhoach.EofficeFull.entity.OutgoingDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OutgoingDocumentRepository extends JpaRepository<OutgoingDocument, Long> {
    @Query("""
            SELECT o from OutgoingDocument o
            """)
    Page<OutgoingDocument> search(OutgoingDocPagingReq req, Pageable pageable);
}
