package com.example.integration4.repositories;

import com.example.integration4.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyJpaRepo extends JpaRepository<Survey, Integer> {
}
