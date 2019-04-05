package com.ledinhtuyenbkdn.bookshop.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ledinhtuyenbkdn.bookshop.model.Book;
import com.ledinhtuyenbkdn.bookshop.repository.BookRepository;
import com.ledinhtuyenbkdn.bookshop.service.StorageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    private BookRepository bookRepository;
    private StorageService storageService;

    public IndexController(BookRepository bookRepository,
                           StorageService storageService) {
        this.bookRepository = bookRepository;
        this.storageService = storageService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(HttpServletRequest request, Model model) {
        List<Book> books = new ArrayList<>();
        bookRepository.findAll().forEach(book -> {
            book.setImageId(storageService.load(book.getImageId()));
            books.add(book);
        });
        model.addAttribute("books", books);
        return "index";
    }
}
