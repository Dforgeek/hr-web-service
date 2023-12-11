package com.example.backend;

import com.example.backend.dto.VacancyMlBody;
import com.example.backend.repos.UserRepo;
import com.example.backend.service.MainService;
import com.example.backend.service.MlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {
    @Autowired
    MlService mlService;
    @Autowired
    MainService service;
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
//        VacancyMlBody vacancyMlBody=new VacancyMlBody()
//        System.out.println(mlService.getRange());

    }

}
