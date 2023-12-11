package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacancyMlBody {
    String position;
    String description;
    String soft_skills;
    String would_be_a_plus;
    String key_skills;

    @ModelAttribute("vacancyMlBody")
    public VacancyMlBody newVacancyMlBody() {
        return new VacancyMlBody();
    }
}
