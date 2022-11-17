package org.example.redisCache.controller;

import org.example.redisCache.model.Book;
import org.example.redisCache.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final static Logger logger = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> findAll() {
        long startTime = System.currentTimeMillis();
        List<Book> books = bookService.findAll();
        long endTime = System.currentTimeMillis() - startTime;

        logger.info("Duration = {}", endTime);
        return ResponseEntity.status(HttpStatus.OK)
                .body(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookService.findById(id).get());
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookService.save(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(bookService.save(book));
    }

    public ResponseEntity delete(@PathVariable Long id) {
        bookService.deleteById(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    //записать куки
    @GetMapping(value = "/set-cookie")
    public ResponseEntity<?> setCookie(HttpServletResponse response) throws IOException {
        Cookie cookie = new Cookie("data", "Come_to_the_dark_side");//создаем объект Cookie,
        //в конструкторе указываем значения для name и value
        cookie.setPath("/");//устанавливаем путь
        cookie.setMaxAge(86400);//здесь устанавливается время жизни куки
        response.addCookie(cookie);//добавляем Cookie в запрос
        response.setContentType("text/plain");//устанавливаем контекст
        return ResponseEntity.ok().body(HttpStatus.OK);//получилось как бы два раза статус ответа установили, выбирайте какой вариант лучше
    }

    //прочитать куки
    @GetMapping(value = "/get-cookie")
    public ResponseEntity<?> readCookie(@CookieValue(value = "data") String data) {
        return ResponseEntity.ok().body(data);
    }
}
