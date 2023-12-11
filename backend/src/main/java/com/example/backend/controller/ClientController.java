package com.example.backend.controller;

import com.example.backend.dto.ResumeDto;
import com.example.backend.dto.VacancyMlBody;
import com.example.backend.repos.UserRepo;
import com.example.backend.service.MlService;
import com.example.backend.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//todo: отдельно login controller; business controller and so on
//todo: view
@Controller
public class ClientController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    ResumeService resumeService;
    @Autowired
    MlService mlService;
//    @GetMapping("/user/registration")
//    public String showRegistrationForm(WebRequest request, Model model) {
//        UserDao userDao = new UserDao();
//        model.addAttribute("user", userDao);
//        return "registration";
//    }
    @RequestMapping("/hello")
    public String hello(){
        return "/index";
    }
    @GetMapping    ("/pg")
    public String pg(){
        return "smthng";
    }
    @GetMapping("/resumeByVacancy")
    public String resByVacancy(Model model){
        VacancyMlBody vacancyMlBody =new VacancyMlBody();
        model.addAttribute("vacancyMlBody", vacancyMlBody);
        return "htm.html";
    }
    @PostMapping(path = "/resumes",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public  String resumes(/*@RequestBody*/ VacancyMlBody vacancyMlBody , Model model) {
        System.out.println("start");
//        VacancyMlBody vacancyMlBody = new VacancyMlBody("Программист C# Middle/Senior",
//                "1. Разработка и поддержка корпоративных приложений на платформе .NET с использованием C#; 2. Взаимодействие с командой для создания качественного и масштабируемого кода; 3. Анализ требований к продукту и разработка технических решений; 4. Проведение код-ревью и оптимизация существующего кода; 5. Участие в проектировании архитектуры проектов.",
//                "Хорошие коммуникативные навыки и способность работать в команде; Владение английским языком на уровне не ниже Intermediate.",
//                "Опыт игры в футбол",
//                "1. Опыт работы с C# и .NET Framework .NET Core не менее 3 лет; 2. Знание принципов ООП, SOLID, DRY, KISS; 3. Опыт работы с базами данных (SQL и NoSQL); 4. Навыки работы с Git; Опыт разработки многопоточных приложений; 6. Умение писать юнит-тесты и понимание принципов TDD;");
//        {
//            "position": "Программист C# Middle/Senior",
//                "description": "1. Разработка и поддержка корпоративных приложений на платформе .NET с использованием C#; 2. Взаимодействие с командой для создания качественного и масштабируемого кода; 3. Анализ требований к продукту и разработка технических решений; 4. Проведение код-ревью и оптимизация существующего кода; 5. Участие в проектировании архитектуры проектов.",
//                "soft_skills": "Хорошие коммуникативные навыки и способность работать в команде; Владение английским языком на уровне не ниже Intermediate.",
//                "would_be_a_plus": "Опыт игры в футбол",
//                "key_skills": "1. Опыт работы с C# и .NET Framework .NET Core не менее 3 лет; 2. Знание принципов ООП, SOLID, DRY, KISS; 3. Опыт работы с базами данных (SQL и NoSQL); 4. Навыки работы с Git; Опыт разработки многопоточных приложений; 6. Умение писать юнит-тесты и понимание принципов TDD;"
//        }

        var ranges=mlService.getRange(vacancyMlBody);
        var res=resumeService.getResumeDtoWithSimilarityForAll(ranges);
        model.addAttribute("resumes", res.stream().limit(10).toList());
        return "resumes.html";
    }
    @GetMapping(value = "/get",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResumeDto>> get(@RequestBody VacancyMlBody vacancyMlBody){
        var ranges=mlService.getRange(vacancyMlBody);
        var res=resumeService.getResumeDtoWithSimilarityForAll(ranges);
        return new ResponseEntity<>(res.stream().limit(10).toList(),HttpStatus.OK);
    }

//    @GetMapping("/showViewPage")
//    public String passParametersWithModel(Model model) {
//        Map<String, String> map = new HashMap<>();
//        map.put("spring", "mvc");
//        model.addAttribute("message", "Baeldung");
//        model.mergeAttributes(map);
//        return "view/viewPage";
//    }
//    @GetMapping("/index")
//    public String home(){
//        return "index";
//    }
//    @GetMapping("/register")
//    public String showRegistrationForm(Model model){
//        // create model object to store form data
//        UserDao user = new UserDao();
//        model.addAttribute("user", user);
//        return "register";
//    }
//
//
//    // handler method to handle user registration form submit request
//    @PostMapping("/register/save")
//    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
//                               BindingResult result,
//                               Model model){
//        User existingUser = userService.findUserByEmail(userDto.getEmail());
//
//        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
//            result.rejectValue("email", null,
//                    "There is already an account registered with the same email");
//        }
//
//        if(result.hasErrors()){
//            model.addAttribute("user", userDto);
//            return "/register";
//        }
//
//        userService.saveUser(userDto);
//        return "redirect:/register?success";
//    }


    
}
