/*
 * @Author: cristianmarint
 * @Date: 5/11/20 11:24
 */

package com.biblioteca.demeter.user;

import com.biblioteca.demeter.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String confirmationToken;
    private LocalDate createdDate;

    @OneToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    ConfirmationToken(User user){
        this.user=user;
        this.createdDate=LocalDate.now();
        this.confirmationToken= UUID.randomUUID().toString();
    }
}
