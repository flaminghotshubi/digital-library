package com.example.demo_jpa.repository;

import com.example.demo_jpa.model.Book;
import com.example.demo_jpa.model.Genre;
import com.example.demo_jpa.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    //Id
    Optional<Book> findByIdAndStudentIsNull(int id);

    //Genre
    List<Book> findByGenre(Genre genre);
    List<Book> findByGenreAndStudentIsNull(Genre genre);

    //Name
    List<Book> findByName(String name);
    List<Book> findByNameAndStudentIsNull(String name);
    List<Book> findByNameContaining(String name);
    List<Book> findByNameContainingAndStudentIsNull(String name);

    //Author
    @Query("select b from Book b INNER JOIN b.author a where a.name = ?1")
    List<Book> findByAuthor(String authorName);
    @Query("select b from Book b INNER JOIN b.author a where a.name = ?1 and b.student is null")
    List<Book> findByAuthorAndStudentIsNull(String authorName);
    @Query("select b from Book b INNER JOIN b.author a where a.name like %:authorName%")
    List<Book> findByAuthorContaining(String authorName);
    @Query("select b from Book b INNER JOIN b.author a where a.name like %:authorName% and b.student is null")
    List<Book> findByAuthorContainingAndStudentIsNull(String authorName);

    // Assign book to student
    @Modifying
    @Query("update Book b set b.student = :student where b.id = :bookId and b.student is null")
    int assignBookToStudent(Student student, int bookId);

    @Modifying
    @Query("update Book b set b.student = null where b.id = :bookId")
    int unassignBookFromStudent(int bookId);


    //List<Book> findByPages(String operator, int pages);
}
