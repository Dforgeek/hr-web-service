package com.example.backend.repos;

import com.example.backend.dao.ResumeDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepo extends JpaRepository<ResumeDao,Integer> {

}
