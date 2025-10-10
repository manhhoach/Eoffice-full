package com.manhhoach.EofficeFull.serviceImpl;

import com.manhhoach.EofficeFull.common.PagedResponse;
import com.manhhoach.EofficeFull.constant.SystemConstant;
import com.manhhoach.EofficeFull.dto.user.CreateUserReq;
import com.manhhoach.EofficeFull.dto.user.UserDto;
import com.manhhoach.EofficeFull.dto.user.UserPagingReq;
import com.manhhoach.EofficeFull.entity.User;
import com.manhhoach.EofficeFull.repository.RoleRepository;
import com.manhhoach.EofficeFull.repository.UserRepository;
import com.manhhoach.EofficeFull.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDto create(CreateUserReq req) {
        validate(null, req.getUsername());
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(SystemConstant.DEFAULT_PASSWORD);
        userRepository.save(user);
        return UserDto.map(user);
    }

    @Override
    @Transactional
    public UserDto update(Long id, CreateUserReq req) {
        validate(id, req.getUsername());
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(req.getUsername());
        userRepository.save(user);
        return UserDto.map(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        return UserDto.map(user);
    }

    @Override
    public PagedResponse<UserDto> getPaged(UserPagingReq request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by("id").descending());
        Page<User> userPage;
        if (request.getSearch() == null || request.getSearch().isBlank()) {
            userPage = userRepository.findAll(pageable);
        } else {
            userPage = userRepository.findByUsernameContainingIgnoreCase(
                    request.getSearch(), pageable);
        }
        var userDtos = userPage.getContent().stream().map(u -> {
            return UserDto.map(u);
        }).toList();

        return new PagedResponse<>(
                userDtos,
                userPage.getNumber() + 1,
                userPage.getTotalPages(),
                userPage.getTotalElements()
        );
    }

    private void validate(Long id, String username) {
        if (userRepository.checkExistUsername(id, username)) {
            throw new IllegalArgumentException("Role code already exist");
        }
    }

}
