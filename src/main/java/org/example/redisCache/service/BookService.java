package org.example.redisCache.service;

import org.example.redisCache.model.Book;
import org.example.redisCache.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "bc") // аннотация конфигурирует все кэш-операции
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Cacheable // результат работы метода попадает в кэш и при след вызове берется оттуда
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Cacheable(key = "#id")
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @CachePut(key = "#book.id") // обновить запись в кэше
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @CacheEvict(key = "#id") // удалить запись из кэша
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
