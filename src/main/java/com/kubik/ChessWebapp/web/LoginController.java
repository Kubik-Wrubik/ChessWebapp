package com.kubik.ChessWebapp.web;

import com.kubik.ChessWebapp.dto.ChessUserDto;
import com.kubik.ChessWebapp.model.ChessUser;
import com.kubik.ChessWebapp.service.ChessUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class LoginController {
    private final ChessUserService chessUserService;
    private final ModelMapper modelMapper;

    @GetMapping("/login")
    public String showWelcomePage(Model model) {
        return "login";
    }

    @GetMapping("/registration")
    public String showRegistration(ChessUserDto chessUser, Model model) {
        return "registration";
    }

    @PostMapping("/main-page")
    public String logIn(ChessUserDto chessUser, BindingResult bindingResult, Model model) {
        ChessUser userFromDB = chessUserService.getUserByNickname(chessUser.getNickname());
        if(userFromDB == null || !userFromDB.getPassword().equals(chessUser.getPassword())){
            bindingResult.rejectValue("password", "error.password", "Invalid password");
            return "login-failed";
        }
        model.addAttribute("user", userFromDB);
        return "main-page";
    }
}