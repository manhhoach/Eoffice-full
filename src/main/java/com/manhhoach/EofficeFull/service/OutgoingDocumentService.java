package com.manhhoach.EofficeFull.service;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.outgoingDocument.CreateOutgoingDocReq;
import com.manhhoach.EofficeFull.dto.outgoingDocument.OutgoingDocDto;
import com.manhhoach.EofficeFull.dto.outgoingDocument.OutgoingDocPagingReq;

import java.io.IOException;

public interface OutgoingDocumentService {
    void create(CreateOutgoingDocReq req) throws IOException;
    void update(CreateOutgoingDocReq req, Long id) throws IOException;
    void delete(Long id);
    PagedResponse<OutgoingDocDto> getAll(OutgoingDocPagingReq req);
}
