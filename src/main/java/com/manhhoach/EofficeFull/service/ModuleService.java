package com.manhhoach.EofficeFull.service;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.module.CreateModuleReq;
import com.manhhoach.EofficeFull.dto.module.ModuleDto;
import com.manhhoach.EofficeFull.dto.module.ModulePagingReq;

import java.util.List;

public interface ModuleService {
    ModuleDto create(CreateModuleReq req);

    ModuleDto update(Long id, CreateModuleReq req);

    void delete(Long id);

    ModuleDto getById(Long id);

    PagedResponse<ModuleDto> getPaged(ModulePagingReq request);

    List<ModuleDto> getDetailModules();
}
