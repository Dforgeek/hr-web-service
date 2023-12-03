package com.example.backend.service;

import com.example.backend.dao.ResumeDao;
import com.example.backend.dao.VacancyDao;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

@Service
public class MLService {
    RestTemplate restTemplate =new RestTemplate();


    HashMap<Integer,Float> getRange(VacancyDao vacancyDao){

        ResponseEntity<HashMap<Integer,Float>> responseEntity= restTemplate.exchange("http://127.0.0.1:5000/get_range/",HttpMethod.POST,new HttpEntity<>(vacancyDao),new ParameterizedTypeReference<HashMap<Integer,Float>>() {});
        return responseEntity.getBody();

    }

}
