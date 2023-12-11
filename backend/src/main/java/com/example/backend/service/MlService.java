package com.example.backend.service;

import com.example.backend.dao.VacancyDao;
import com.example.backend.dto.MlReturnDto;
import com.example.backend.dto.VacancyMlBody;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@Service
public class MlService {
    RestTemplate restTemplate =new RestTemplate();


    public HashMap<Integer,Float> getRange(VacancyMlBody vacancyMlBody){

        ResponseEntity<List<MlReturnDto>> responseEntity= restTemplate.exchange("http://127.0.0.1:5000/get_range/",HttpMethod.POST,new HttpEntity<>(vacancyMlBody),new ParameterizedTypeReference<List<MlReturnDto>>() {});
        List<MlReturnDto> list=responseEntity.getBody();
        HashMap<Integer,Float> map=new HashMap<>();
        for (int i = 0; i < 25; i++) {

            map.put(list.get(i).getId(),list.get(i).getSimilarity());
        }

        return map;

    }

}
