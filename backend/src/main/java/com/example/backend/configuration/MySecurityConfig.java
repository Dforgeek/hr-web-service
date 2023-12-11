package com.example.backend.configuration;

import com.example.backend.service.UserService;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;


//todo: доделай + адаптер
@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfiguration {





    @Bean
    public PasswordEncoder myPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Autowired
    UserService userService;





    @Bean
    public SecurityFilterChain customSecFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin()
                .usernameParameter("name")
                .passwordParameter("password")
        ;
        http.authorizeHttpRequests()

//                .requestMatchers("/login").permitAll()
                .requestMatchers("/hello")/*.permitAll()*/.hasAnyRole("ADMIN","USER")
                .requestMatchers("/get").hasRole("USER")
                .requestMatchers("/resumeByVacancy").hasRole("USER")
                .requestMatchers("/resumes").hasRole("USER").anyRequest().permitAll()
                .and().httpBasic()
                .and().sessionManagement();








































































        return http.build();
    }






}
