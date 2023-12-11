package com.example.backend.service;

import com.example.backend.dto.ResumeDto;
import com.example.backend.mappers.MyMapper;
import com.example.backend.repos.ResumeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ResumeService {
    @Autowired
    MyMapper mapper;
    @Autowired
    ResumeRepo resumeRepo;

    public List<ResumeDto>  getResumeDtoWithSimilarityForAll(HashMap<Integer, Float> similMap) {
        List<ResumeDto> resList = new ArrayList<>();

        similMap.forEach((k, v) -> resList.add(mapper.resumeDaoToDto(resumeRepo.findById(k).get()).setSimilarityAndReturnEntity(v)));



        return resList;
    }

    public List<ResumeDto> getAllResumeFromListWhereCityIs(List<ResumeDto> fullResumeDtos, String city) {
        List<ResumeDto> resultList = new ArrayList<>();
        for (ResumeDto fullResumeDto : fullResumeDtos) {
            if (fullResumeDto.getCity().equals(city)) {
                resultList.add(fullResumeDto);
            }
        }
        return resultList;

    }
}
