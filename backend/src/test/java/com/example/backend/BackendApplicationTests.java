package com.example.backend;

import com.example.backend.repos.UserRepo;
import com.example.backend.service.HrService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {
    @Autowired
    HrService service;
    @Autowired
    UserRepo repo;

    @Test
    void contextLoads() {
    }
    @Test
    void tst(){
//        UserDao clientDao = new UserDao();
//
//        clientDao.setEmail("exampleeasdawrwer@example.com");
//        clientDao.setPhone(12314111);
//        clientDao.setUsername("examasdapleewr");
//        clientDao.setPassword("passwasdaordwerw");
//        clientDao.setLogin("exampleasdloginwer");
//        service.saveClient(clientDao);

        System.out.println(repo.findByLogin("exampleasdloginwer"));

    }

}
