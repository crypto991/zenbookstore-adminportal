package com.adminportal.controller;

import com.adminportal.domain.Book;
import com.adminportal.service.AWSS3Service;
import com.adminportal.service.BookService;
import com.adminportal.service.UserAdminService;
import com.adminportal.utility.BASE64DecodedMultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    public static final Logger LOG = LoggerFactory.getLogger(UserAdminService.class);

    private BookService bookService;
    private AWSS3Service awss3Service;

    public BookController(BookService bookService, AWSS3Service awss3Service) {
        this.bookService = bookService;
        this.awss3Service = awss3Service;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addBook(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);

        return "addBook";
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addBookPost(
            @ModelAttribute("book") Book book,
            HttpServletRequest request
    ) {

        bookService.save(book);
        awss3Service.uploadFile(book.getBookImage(), book.getId());

        return "redirect:bookList";
    }

    @RequestMapping(value = "/updateBook")
    public String updateBookModel(
            @RequestParam("id") Long id,
            Model model
    ) {
        Book book = bookService.findById(id);

        model.addAttribute("book", book);

        return "updateBook";
    }

    @RequestMapping(value = "/updateBook", method = RequestMethod.POST)
    public String updateBookPost(
            @ModelAttribute("book") Book book,
            HttpServletRequest request
    ) {

        bookService.save(book);

        MultipartFile bookImage = book.getBookImage();

        if (!bookImage.isEmpty()) {
            awss3Service.uploadFile(book.getBookImage(), book.getId());
        }

        return "redirect:/book/bookInfo?id=" + book.getId();
    }


    @RequestMapping(value = "/bookInfo")
    public String bookInfo(@RequestParam("id") Long id,
                           Model model) {
        Book book = bookService.findById(id);

        model.addAttribute("book", book);

        return "bookInfo";

    }


    @RequestMapping(value = "/bookList")
    public String bookList(Model model) {
        List<Book> bookList = bookService.findAll();
        model.addAttribute("bookList", bookList);


        return "bookList";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(
            @ModelAttribute("id") String id, Model model
    ) {
        Long idLong = Long.parseLong(id.substring(8));
        bookService.removeOne(idLong);


        List<Book> bookList = bookService.findAll();
        model.addAttribute("bookList", bookList);

        return "redirect:/book/bookList";
    }


}
