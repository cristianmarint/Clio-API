/*
 * @Author: cristianmarint
 * @Date: 3/1/21 10:28
 */

/*
 * @Author: cristianmarint
 * @Date: 12/12/20 16:12
 */

package com.api.clio.repository;

import com.api.clio.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findCategoryById(Long id);
    @Query(
            value = "select category.* from book_category inner join category on book_category.category_id=category.id WHERE book_category.book_id=:bookId",
            nativeQuery = true
    )
    List<Category> findCategoryByBookId(Long bookId);
}
