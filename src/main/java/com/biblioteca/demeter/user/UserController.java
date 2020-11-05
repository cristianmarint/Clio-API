/*
 * @Author: cristianmarint
 * @Date: 5/11/20 11:44
 */

package com.biblioteca.demeter.user;

import com.biblioteca.demeter.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    private final ConfirmationTokenService confirmationTokenService;

    @GetMapping("/sign-in")
    String signIn(){
        return "sign-in";
    }

    @GetMapping("/sign-up")
    String signUpPage(User user){
        return "sign-up";
    }

    @GetMapping("/sign-up/confirm")
    String confirmMail(@RequestParam("token") String token){
        Optional<ConfirmationToken> optionalConfirmationToke = confirmationTokenService.findConfirmationTokenByToken(token);
        optionalConfirmationToke.ifPresent(userService::confirmuser);
        return "redirect:/sign-in";
    }
}
