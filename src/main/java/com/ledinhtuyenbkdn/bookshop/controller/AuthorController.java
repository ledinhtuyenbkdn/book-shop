package com.ledinhtuyenbkdn.bookshop.controller;

import com.ledinhtuyenbkdn.bookshop.model.Author;
import com.ledinhtuyenbkdn.bookshop.repository.AuthorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class AuthorController {
    private AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @RequestMapping(value = "/create-author", method = RequestMethod.GET)
    public String doGetCreateAuthor(Model model) {
        Author author = new Author();
        model.addAttribute("author", author);
        return "create-author";
    }

    @RequestMapping(value = "/create-author", method = RequestMethod.POST)
    public String doPostCreateAuthor(@Valid @ModelAttribute("author") Author author, BindingResult result) {
        if (result.hasErrors()) {
            return "create-author";
        }
        authorRepository.save(author);
        return "redirect:/manage-author";
    }

    @RequestMapping("/manage-author")
    public String readAllAuthors(Model model) {
        model.addAttribute("authors", authorRepository.findAll());
        return "manage-author";
    }
    @RequestMapping("/delete-author/{id}")
    public String deleteAuthor(@PathVariable("id") Long id) {
        authorRepository.deleteById(id);
        return "redirect:/manage-author";
    }

    @RequestMapping(value = "/update-author/{id}", method = RequestMethod.GET)
    public String doGetUpdateAuthor(@PathVariable("id") Long id, Model model) {
        Author author = authorRepository.findById(id).get();
        model.addAttribute("author", author);
        return "update-author";
    }

    @RequestMapping(value = "/update-author/{id}", method = RequestMethod.POST)
    public String doPostUpdateAuthor(@Valid Author author, BindingResult result) {
        if (result.hasErrors()) {
            return "update-author";
        }
        authorRepository.save(author);
        return "redirect:/manage-author";
    }
}
