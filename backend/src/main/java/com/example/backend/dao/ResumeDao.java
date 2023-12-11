package com.example.backend.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//DAO класс отражающий таблицу в бд


@Entity
@Table(name = "resume", schema = "public", catalog = "hrBackDb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeDao {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
//    @Basic
//    @Column(name = "date_of_creation", nullable = true)
//    private Integer dateOfCreation;
//    @Basic
//    @Column(name = "creator_id", nullable = true)
//    private Integer creatorId;
//    @Basic
//    @Column(name = "vacancy_id", nullable = true)
//    private Integer vacancyId;
    @Basic
    @Column(name = "position", nullable = true, length = -1)
    private String position;
    @Basic
    @Column(name = "sex", nullable = true, length = -1)
    private String sex;
    @Basic
    @Column(name = "age", nullable = true)
    private Integer age;
    @Basic
    @Column(name = "city", nullable = true, length = -1)
    private String city;
    @Basic
    @Column(name = "is_relocation_ready", nullable = true)
    private boolean isRelocationReady;
    @Basic
    @Column(name = "is_business_trip_ready", nullable = true)
    private boolean isBusinessTripReady;
    @Basic
    @Column(name = "specializations", nullable = true, length = -1)
    private String specializations;
    @Basic
    @Column(name = "employment", nullable = true, length = -1)
    private String employment;
    @Basic
    @Column(name = "work_schedule", nullable = true, length = -1)
    private String workSchedule;
    @Basic
    @Column(name = "desired_salary", nullable = true, precision = 0)
    private Double desiredSalary;
    @Basic
    @Column(name = "total_experience", nullable = true, precision = 0)
    private Double totalExperience;
    @Basic
    @Column(name = "previous_position", nullable = true, length = -1)
    private String previousPosition;
    @Basic
    @Column(name = "skills", nullable = true, length = -1)
    private String skills;
    @Basic
    @Column(name = "language", nullable = true, length = -1)
    private String language;
    @Basic
    @Column(name = "education", nullable = true, length = -1)
    private String education;
    @Basic
    @Column(name = "courses", nullable = true, length = -1)
    private String courses;
    @Basic
    @Column(name = "has_car", nullable = true)
    private boolean hasCar;
    @Basic
    @Column(name = "driving_categories", nullable = true, length = -1)
    private String drivingCategories;
    @Basic
    @Column(name = "about_me", nullable = true, length = -1)
    private String aboutMe;
    @Basic
    @Column(name = "job_search_status", nullable = true, length = -1)
    private String jobSearchStatus;
    @Basic
    @Column(name = "url", nullable = true, length = -1)
    private String url;
    @Basic
    @Column(name = "embedding", nullable = true, length = -1)
    private String embedding;
//    @ManyToOne
//    @JoinColumn(name = "creator_id", referencedColumnName = "id")
//    private UserDao clientByCreatorId;





  }
