package com.example.backend.service;

import com.example.backend.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
//TOdo: он нужен вообще?

@Service
@Component
public class MainService {
    BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();
    @Autowired
    UserRepo userRepo;
    @Autowired
    MLService mlService;
    @Autowired
    ResumeService resumeService;

    




}
