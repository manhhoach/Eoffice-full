package com.manhhoach.EofficeFull.service;

import com.manhhoach.EofficeFull.dto.outgoingDocument.CreateOutgoingDocReq;

import java.io.IOException;

public interface OutgoingDocumentService {
    void create(CreateOutgoingDocReq req) throws IOException;
}
