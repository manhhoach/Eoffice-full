package com.manhhoach.EofficeFull.repository;

import com.manhhoach.EofficeFull.entity.OutgoingDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutgoingDocumentRepository extends JpaRepository<OutgoingDocument, Long> {
}
