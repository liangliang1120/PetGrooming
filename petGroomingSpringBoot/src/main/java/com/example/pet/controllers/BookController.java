package com.example.pet.controllers;
import java.util.List;

import com.example.pet.dto.MessageDetails;
// import com.example.pet.utils.TimeDeltaAdd;
import com.example.pet.models.Book;
// import com.example.pet.models.Schedule;
import com.example.pet.dto.BookInfo;
import com.example.pet.repositories.BookRepository;
import com.example.pet.repositories.ScheduleRepository;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    private final BookRepository bookRepository;
    private final ScheduleRepository scheduleRepository;

    public BookController(BookRepository bookRepository, ScheduleRepository scheduleRepository) {
        this.bookRepository = bookRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @GetMapping("/books")
    public List<BookInfo> getBooks() {
        return bookRepository.getBooks();
    }

    @GetMapping("/books/bookid/{bookId}")
    public List<BookInfo> getBooksByBookId(@PathVariable("bookId") Integer bookId) {
        return bookRepository.getBooksByBookId(bookId);
    }

    @GetMapping("/books/{cusId}")
    public List<BookInfo> getBooksByCusId(@PathVariable("cusId") Integer cusId) {
        return bookRepository.getBooksByCusId(cusId);
    }

    @PostMapping("/books")
    public ResponseEntity<MessageDetails> addBook(@RequestBody Book book) {        
        bookRepository.addBook(book);
        scheduleRepository.updateScheduleAvailableFalseBySId(book.getsId());
        MessageDetails msg = new MessageDetails("The book was added successfully.", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);

    }

    @PutMapping("/books")
    public ResponseEntity<MessageDetails> updateBook(@RequestBody Book book) {
        bookRepository.updateBook(book);
        MessageDetails msg = new MessageDetails("The book was updated successfully.", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }

    @DeleteMapping("/books")
    public ResponseEntity<MessageDetails> deleteBook(@RequestBody  Book book) {
        bookRepository.deleteBook(book.getBookId());
        scheduleRepository.updateScheduleAvailableTrueBySId(book.getsId());
        MessageDetails msg = new MessageDetails("The book was cancelled successfully.", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
       
    }

}
