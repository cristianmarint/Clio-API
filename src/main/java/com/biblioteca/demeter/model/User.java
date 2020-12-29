/*
 * @Author: cristianmarint
 * @Date: 8/12/20 22:27
 */

package com.biblioteca.demeter.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotBlank(message = "Username is required")
    private String username;

    @NotEmpty
    @NotBlank(message = "Password is required")
    private String password;

    @Email
    @NotEmpty(message = "Email is required")
    private String email;

    @Builder.Default
    private Timestamp createdAt= Timestamp.from(Instant.now());
    @Builder.Default
    private boolean enabled=false;

    @OneToMany(mappedBy = "user", fetch=FetchType.EAGER)
    private List<Book> bookList;
    public void addBookToList(Book book){
        if (bookList == null){
            bookList = new ArrayList<Book>(Collections.singleton(book));
        }else {
            bookList.add(book);
        }
    }
}
