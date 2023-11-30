package com.example.backend.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



//DAO класс отражающий таблицу в бд



@Entity
@Table(name = "vacancy", schema = "public", catalog = "hrBackDb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacancyDao {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "position", nullable = true, length = -1)
    private String position;
    @Basic
    @Column(name = "company_name", nullable = true, length = -1)
    private String companyName;
    @Basic
    @Column(name = "place", nullable = true, length = -1)
    private String place;
    @Basic
    @Column(name = "description", nullable = true, length = -1)
    private String description;
    @Basic
    @Column(name = "conditions", nullable = true, length = -1)
    private String conditions;
    @Basic
    @Column(name = "key_skills", nullable = true, length = -1)
    private String keySkills;
    @Basic
    @Column(name = "would_be_a_plus", nullable = true, length = -1)
    private String wouldBeAPlus;
    @Basic
    @Column(name = "address", nullable = true, length = -1)
    private String address;
    @Basic
    @Column(name = "salary", nullable = true, precision = 0)
    private Double salary;
    @Basic
    @Column(name = "creator_id", nullable = true)
    private Integer creatorId;
//    @ManyToOne
//    @JoinColumn(name = "creator_id", referencedColumnName = "id")
//    private UserDao clientByCreatorId;


}
