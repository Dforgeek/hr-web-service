package com.example.backend.repos;

import com.example.backend.dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserDao, Integer> {
//    List<UserDao> findByName(String name);
    UserDao findByLogin(String login);
    UserDao findByUsername(String email);

}
