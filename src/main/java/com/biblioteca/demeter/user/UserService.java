/*
 * @Author: cristianmarint
 * @Date: 5/11/20 9:57
 */

package com.biblioteca.demeter.user;

import com.biblioteca.demeter.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSenderService emailSenderService;

    void sendConfirmationMail(String userMail, String token){
//        HAY QUE CREAR EL SERVICE DE TOKENS PARA EMAIL
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail);
        mailMessage.setSubject("CONFIRMATION URL! WOOO");
        mailMessage.setFrom("<MAIL>");
        mailMessage.setText(
                    "Click below to activate your account."+"http://localhost:8080/sign-up/confirm?token="+token
        );
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.orElseThrow(()->new UsernameNotFoundException(MessageFormat.format("User with email {0} cannot be found",email)));
    }

    public void signUpUser(User user){
        final String encrytedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encrytedPassword);
        final User createdUser = userRepository.save(user);
        final ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        sendConfirmationMail(user.getEmail(),confirmationToken.getConfirmationToken());
    }

    void confirmuser(ConfirmationToken confirmationToken){
        final User user = confirmationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());
    }
}
