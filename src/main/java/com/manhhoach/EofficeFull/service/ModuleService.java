package com.manhhoach.EofficeFull.service;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.module.*;

import java.util.List;

public interface ModuleService {
    ModuleDto create(CreateModuleReq req);

    ModuleDto update(Long id, CreateModuleReq req);

    void delete(Long id);

    ModuleDto getById(Long id);

    PagedResponse<ModuleDto> getPaged(ModulePagingReq request);

    List<ModuleSelectionDto> getSelectedModules(Long roleId);

    void setSelectedModules(Long roleId, SelectedModuleReq req);
}
