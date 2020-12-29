/*
 * @Author: cristianmarint
 * @Date: 23/12/20 17:22
 */

package com.biblioteca.demeter.repository;

import com.biblioteca.demeter.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(
            value="select * from book_category inner join book on book_category.book_id=book.id WHERE book_category.category_id=:categoryId",
            nativeQuery = true
    )
    List<Book> findBooksByCategoryId(@Param("categoryId") Long categoryId);

    @Query(
            value="select * from book_category inner join book on book_category.book_id=book.id WHERE book_category.category_id=:categoryId AND book_category.book_id=:bookId LIMIT 1",
            nativeQuery = true
    )
    Book findBookByCategoryIdAndBookId(@Param("categoryId") Long categoryId, @Param("bookId") Long bookId);
}