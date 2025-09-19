package com.manhhoach.EofficeFull.service.impl;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.dto.module.CreateModuleReq;
import com.manhhoach.EofficeFull.dto.module.ModuleDto;
import com.manhhoach.EofficeFull.entity.Module;
import com.manhhoach.EofficeFull.repository.ModuleRepository;
import com.manhhoach.EofficeFull.repository.ModuleRepository;
import com.manhhoach.EofficeFull.service.ModuleService;
import com.manhhoach.EofficeFull.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    public PagedResponse<ModuleDto> getPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
        Page<Module> data = moduleRepository.findAll(pageable);
        var res = data.getContent().stream().map(d->{
            return ModuleDto.map(d);
        }).toList();

        return new PagedResponse<>(
                res,
                data.getNumber() + 1,
                data.getTotalPages(),
                data.getTotalElements()
        );
    }

    private void validate(Long id, String code){
        if(moduleRepository.existCode(id, code)){
            throw new IllegalArgumentException("Module code already exist");
        }
    }
}
