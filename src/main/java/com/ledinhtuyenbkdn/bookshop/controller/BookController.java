package com.ledinhtuyenbkdn.bookshop.controller;

import com.ledinhtuyenbkdn.bookshop.model.Book;
import com.ledinhtuyenbkdn.bookshop.model.BookWrapper;
import com.ledinhtuyenbkdn.bookshop.repository.AuthorRepository;
import com.ledinhtuyenbkdn.bookshop.repository.BookRepository;
import com.ledinhtuyenbkdn.bookshop.service.StorageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BookController {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private StorageService storageService;

    public BookController(BookRepository bookRepository,
                          AuthorRepository authorRepository,
                          StorageService storageService) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.storageService = storageService;
    }

    @RequestMapping(value = "/create-book", method = RequestMethod.GET)
    public String doGetCreateBook(Model model) {
        BookWrapper bookWrapper = new BookWrapper();
        model.addAttribute("book", bookWrapper);
        model.addAttribute("authors", authorRepository.findAll());
        return "create-book";
    }

    @RequestMapping(value = "/create-book", method = RequestMethod.POST)
    public String doPostCreateBook(@Valid @ModelAttribute("book") BookWrapper bookWrapper, BindingResult result) {
        if (result.hasErrors() || bookWrapper.getMultipartFile().isEmpty()) {
            System.out.println(result.getAllErrors());
            return "create-book";
        }
        String imageId = storageService.store(bookWrapper.getMultipartFile());

        Book book = new Book();
        book.setTitle(bookWrapper.getTitle());
        book.setPublisher(bookWrapper.getPublisher());
        book.setNumPage(bookWrapper.getNumPage());
        book.setAuthor(bookWrapper.getAuthor());
        book.setImageId(imageId);

        bookRepository.save(book);
        return "redirect:/manage-book";
    }

    @RequestMapping("/manage-book")
    public String readAllBooks(Model model) {
        List<Book> books = new ArrayList<>();
        bookRepository.findAll().forEach(book -> {
            book.setImageId(storageService.load(book.getImageId()));
            books.add(book);
        });
        model.addAttribute("books", books);
        return "manage-book";
    }

    @RequestMapping("/delete-book/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            storageService.delete(book.getImageId());
            bookRepository.delete(book);
        }
        return "redirect:/manage-book";
    }

    @RequestMapping(value = "/update-book/{id}", method = RequestMethod.GET)
    public String doGetUpdateBook(@PathVariable("id") Long id, Model model) {
        Book book = bookRepository.findById(id).get();
        BookWrapper bookWrapper = new BookWrapper();

        bookWrapper.setId(book.getId());
        bookWrapper.setTitle(book.getTitle());
        bookWrapper.setPublisher(book.getPublisher());
        bookWrapper.setImageId(book.getImageId());
        bookWrapper.setAuthor(book.getAuthor());
        bookWrapper.setNumPage(book.getNumPage());

        model.addAttribute("bookWrapper", bookWrapper);
        model.addAttribute("authors", authorRepository.findAll());
        return "update-book";
    }

    @RequestMapping(value = "/update-book/{id}", method = RequestMethod.POST)
    public String doPostUpdateBook(@PathVariable("id") Long id, @Valid BookWrapper bookWrapper,
                                   BindingResult result, Model model) {
        if (result.hasErrors() || bookWrapper.getMultipartFile().isEmpty()) {
            model.addAttribute("authors", authorRepository.findAll());
            return "update-book";
        }
        Book book = bookRepository.findById(id).get();
        storageService.delete(book.getImageId());
        String imageId = storageService.store(bookWrapper.getMultipartFile());

        book.setId(bookWrapper.getId());
        book.setTitle(bookWrapper.getTitle());
        book.setPublisher(bookWrapper.getPublisher());
        book.setNumPage(bookWrapper.getNumPage());
        book.setAuthor(bookWrapper.getAuthor());
        book.setImageId(imageId);

        bookRepository.save(book);
        return "redirect:/manage-book";
    }

    @RequestMapping("/books/{id}")
    public String readBook(@PathVariable("id") Long id, Model model) {
        Book book = bookRepository.findById(id).get();
        book.setImageId(storageService.load(book.getImageId()));
        model.addAttribute("book", book);
        return "view-book";
    }
}
