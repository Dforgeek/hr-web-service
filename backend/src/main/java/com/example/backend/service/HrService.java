package com.example.backend.service;

import com.example.backend.dao.UserDao;
import com.example.backend.dao.Roles;
import com.example.backend.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
//TOdo: он нужен вообще?

@Service
@Component
public class HrService {
    BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();
    @Autowired
    UserRepo userRepo;



//    public boolean saveClient(UserDao userDao) {
//        UserDao clientFromDb = userRepo.findByLogin(userDao.getLogin());
//
//        if (clientFromDb != null){
//            return false;
//        }
//        userDao.setRoles(Collections.singleton(new Roles(1,"ROLE_USER")));
//        userDao.setPassword(bCryptPasswordEncoder.encode(userDao.getPassword()));
//        userRepo.save(userDao);
//        return true;
//    }
}
