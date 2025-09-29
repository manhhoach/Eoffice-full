package com.manhhoach.EofficeFull.service.impl;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.module.CreateModuleReq;
import com.manhhoach.EofficeFull.dto.module.ModuleDto;
import com.manhhoach.EofficeFull.dto.module.ModulePagingReq;
import com.manhhoach.EofficeFull.dto.permission.PermissionDto;
import com.manhhoach.EofficeFull.entity.Module;
import com.manhhoach.EofficeFull.repository.ModuleRepository;
import com.manhhoach.EofficeFull.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {
    private final ModuleRepository moduleRepository;

    @Override
    public ModuleDto create(CreateModuleReq req) {
        validate(null, req.getCode());
        Module data = new Module();
        data.setName(req.getName());
        data.setCode(req.getCode());
        data.setIsDisplayed(req.getIsDisplayed());
        moduleRepository.save(data);
        return ModuleDto.map(data);
    }

    @Override
    public ModuleDto update(Long id, CreateModuleReq req) {
        validate(id, req.getCode());
        Module data = moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module not found"));
        data.setName(req.getName());
        data.setCode(req.getCode());
        data.setIsDisplayed(req.getIsDisplayed());
        moduleRepository.save(data);
        return ModuleDto.map(data);
    }

    @Override
    public void delete(Long id) {
        moduleRepository.deleteById(id);
    }

    @Override
    public ModuleDto getById(Long id) {
        Module data = moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module not found"));
        return ModuleDto.map(data);
    }

    @Override
    public PagedResponse<ModuleDto> getPaged(ModulePagingReq request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by("id").descending());
        Page<Module> data = moduleRepository.searchModules(request, pageable);

        var res = data.getContent().stream().map(d -> {
            return ModuleDto.map(d);
        }).toList();

        return new PagedResponse<>(
                res,
                data.getNumber() + 1,
                data.getTotalPages(),
                data.getTotalElements()
        );
    }

    @Override
    public List<ModuleDto> getDetailModules() {
        List<Module> modules = moduleRepository.findAllWithPermissions();
        List<ModuleDto> data = modules.stream().map(module -> {
            ModuleDto mDto = ModuleDto.map(module);
            List<PermissionDto> pDtos = module.getPermissions().stream().map(p -> PermissionDto.map(p)).toList();
            mDto.setPermissions(pDtos);
            return mDto;
        }).toList();
        return data;
    }


    private void validate(Long id, String code) {
        if (moduleRepository.existCode(id, code)) {
            throw new IllegalArgumentException("Module code already exist");
        }
    }
}
