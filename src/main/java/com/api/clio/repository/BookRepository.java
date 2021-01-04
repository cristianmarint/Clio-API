/*
 * @Author: cristianmarint
 * @Date: 3/1/21 10:28
 */

/*
 * @Author: cristianmarint
 * @Date: 23/12/20 17:22
 */

package com.api.clio.repository;

import com.api.clio.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    /**
     * finds a given book through their relation with category base on Category Id
     * @param categoryId
     * @return
     */
    @Query(
            value="select book.* from book_category inner join book on book_category.book_id=book.id WHERE book_category.category_id=:categoryId",
            nativeQuery = true
    )
    List<Book> findBooksByCategoryId(@Param("categoryId") Long categoryId);

    @Query(
            value="select * from book_category inner join book on book_category.book_id=book.id WHERE book_category.category_id=:categoryId AND book_category.book_id=:bookId LIMIT 1",
            nativeQuery = true
    )
    Optional<Book> findBookByCategoryIdAndBookId(@Param("categoryId") Long categoryId, @Param("bookId") Long bookId);

    Optional<Book> findBookById(Long bookId);

    @Transactional
    @Modifying
    @Query(
            value="delete from book_category WHERE book_category.category_id=:categoryId AND book_category.book_id=:bookId LIMIT 1",
            nativeQuery = true
    )
    void deleteCategoryBookRelationByCategoryIdAndBookId(Long categoryId, Long bookId);

    @Query(
            value="select book.* from book_author inner join book on book_author.book_id=book.id WHERE book_author.author_id=:authorId AND book_author.book_id=:bookId LIMIT 1",
            nativeQuery = true
    )
    Optional<Book> findBookByAuthorIdAndBookId(Long authorId, Long bookId);

    @Query(
            value = "select book.* from book_author inner join book on book_author.author_id=author.id WHERE book_author.author_id=:authorId",
            nativeQuery = true
    )
    List<Book> findBooksByAuthorId(Long authorId);
}