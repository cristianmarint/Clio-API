/*
 * @Author: cristianmarint
 * @Date: 11/12/20 18:19
 */

package com.biblioteca.demeter.boot;

import com.biblioteca.demeter.model.User;
import com.biblioteca.demeter.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Iterator;

@Component
@Order(1)
@Slf4j
public class UserDataLoader implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private void setUserRepository(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("----------USER DATALOADER----------");
        userRepository.deleteAllInBatch();
        User user1=new User();
        user1.setCreatedDate(Instant.now());
        user1.setEmail("cristianmarint@gmail.com");
        user1.setEnabled(true);
        user1.setPassword("$2a$10$iralKLjgFstqxQ6J2yIdV.QM3zwATgGZx0l7QAvka52MfhZGbO0bG");// 123456789
        user1.setUsername("cristianmarint");
        userRepository.save(user1);

        Iterator<User> iterator = userRepository.findAll().iterator();
        while(iterator.hasNext()){
            log.info("{}",iterator.next().toString());
        }
    }
}
