package com.example.backend.controller;

import com.example.backend.dao.UserDao;
import com.example.backend.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

//todo: отдельно login controller; business controller and so on
//todo: view
@Controller
public class ClientController {
    @Autowired
    UserRepo userRepo;
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
    @GetMapping("/get/{email}")
    public ResponseEntity<UserDao> get(@PathVariable(name = "email")String email){
        return new ResponseEntity<>(userRepo.findByUsername(email),HttpStatus.OK);
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
