package com.adminportal.service;

import com.adminportal.domain.Book;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {


    Book save(Book book);

    void saveImage(MultipartFile imageFile, Long id) throws Exception;

    List<Book> findAll();

    Book findById(Long id);

    void removeOne(Long id);

}
