/*
 * @Author: cristianmarint
 * @Date: 17/12/20 11:05
 */

package com.biblioteca.demeter.repository;

import com.biblioteca.demeter.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);
}
