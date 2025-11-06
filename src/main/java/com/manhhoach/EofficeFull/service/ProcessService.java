package com.manhhoach.EofficeFull.service;

import com.manhhoach.EofficeFull.constant.ProcessProgressType;

public interface ProcessService {
    void initProcess(Long documentId, ProcessProgressType type, String flowCode);
}
