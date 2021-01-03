/*
 * @Author: cristianmarint
 * @Date: 3/1/21 10:28
 */

/*
 * @Author: cristianmarint
 * @Date: 17/12/20 11:05
 */

package com.api.clio.repository;

import com.api.clio.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query(
            value = "select author.* from book_author inner join author on book_author.author_id=author.id WHERE book_author.book_id=:bookId",
            nativeQuery = true
    )
    List<Author> findAuthorsByBookId(@Param("bookId") Long bookId);
}
