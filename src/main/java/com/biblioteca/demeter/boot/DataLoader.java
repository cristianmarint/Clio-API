/*
 * @Author: cristianmarint
 * @Date: 6/11/20 11:33
 */

package com.biblioteca.demeter.boot;

import com.biblioteca.demeter.entity.User;
import com.biblioteca.demeter.entity.UserRole;
import com.biblioteca.demeter.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Creating users");
        User u1 = new User();
        u1.setName("Cristian");
        u1.setSurname("Marin");
        u1.setEmail("cristianmarint@cotecnova.edu.co");
//        123456798
        u1.setPassword("$2a$10$6xyzXHHFcYvK9FqXXaP28OPpyH.E8oMhaHkzy27aSUQ1YxTqnOsDO");
        u1.setEnabled(true);
        u1.setUserRole(UserRole.ADMIN);
        userRepository.save(u1);
        System.out.println(u1.getEmail()+" Created");
    }
}
