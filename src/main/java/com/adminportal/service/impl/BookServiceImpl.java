package com.adminportal.service.impl;


import com.adminportal.domain.Book;
import com.adminportal.repository.BookRepository;
import com.adminportal.service.BookService;
import com.adminportal.service.UserAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    public static String uploadDirectory = System.getProperty("user.home") + "\\images\\";
    public static final Logger LOG = LoggerFactory.getLogger(UserAdminService.class);

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public void saveImage(MultipartFile imageFile, Long id) throws Exception {

        byte[] bytes = imageFile.getBytes();
        String name = id + ".png";

        LOG.info(uploadDirectory);

        Path path = Paths.get(uploadDirectory + name);

        Files.write(path, bytes);

    }

    @Override
    public Book findById(Long id) {
        return bookRepository.getOne(id);
    }

    @Override
    public void removeOne(Long id) {
        bookRepository.deleteById(id);
    }
}
