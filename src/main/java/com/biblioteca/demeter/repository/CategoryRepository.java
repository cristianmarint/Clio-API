/*
 * @Author: cristianmarint
 * @Date: 12/12/20 16:12
 */

package com.biblioteca.demeter.repository;

import com.biblioteca.demeter.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
