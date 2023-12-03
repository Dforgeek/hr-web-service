package com.example.backend.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeDto {

    private int id;

    private Integer dateOfCreation;

    private Integer creatorId;

    private Integer vacancyId;

    private String position;

    private String sex;

    private Integer age;

    private String city;

    private boolean isRelocationReady;

    private boolean isBusinessTripReady;

    private String specializations;

    private String employment;

    private String workSchedule;

    private Double desiredSalary;

    private Double totalExperience;

    private String previousPosition;

    private String skills;

    private String language;

    private String education;

    private String courses;

    private boolean hasCar;

    private String drivingCategories;

    private String aboutMe;

    private String jobSearchStatus;

    private String url;

    private String embedding;

    private float similarity;

    public ResumeDto setSimilarityAndReturnEntity(float similarity) {
        this.similarity = similarity;
        return this;
    }
}
