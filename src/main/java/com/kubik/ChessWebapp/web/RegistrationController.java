package com.kubik.ChessWebapp.web;

import com.kubik.ChessWebapp.dto.ChessUserDto;
import com.kubik.ChessWebapp.service.ChessUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final ChessUserService chessUserService;

    @PostMapping("/registration-result")
    public String registerUser(@Valid ChessUserDto chessUser, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!chessUser.getPassword().equals(chessUser.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.user", "Passwords do not match");
            return "registration";
        }
        chessUserService.createUser(chessUser);
        model.addAttribute("user", chessUser);
        return "registration-successful";
    }
}
