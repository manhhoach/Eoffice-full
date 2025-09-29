package com.manhhoach.EofficeFull.service.impl;

import com.manhhoach.EofficeFull.repository.UserRepository;
import com.manhhoach.EofficeFull.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


}
