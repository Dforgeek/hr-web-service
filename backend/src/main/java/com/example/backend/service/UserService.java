package com.example.backend.service;

import com.example.backend.dao.Roles;
import com.example.backend.dao.UserDao;
import com.example.backend.dto.ResumeDto;
import com.example.backend.mappers.MyMapper;
import com.example.backend.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    MyMapper mapper;





    //TODO: доделай
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("loading");

        UserDao user = userRepo.findByUsername(email);
        System.out.println(user);
        if (user == null){
            throw new UsernameNotFoundException(String.format("User with email '%s' not found",email));
        }
        var res = new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
        System.out.println(res);
        return res;
    }
    private Collection < ? extends GrantedAuthority> mapRolesToAuthorities(Collection<Roles> roles) {
        Collection < ? extends GrantedAuthority> mapRoles = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return mapRoles;
    }




}
