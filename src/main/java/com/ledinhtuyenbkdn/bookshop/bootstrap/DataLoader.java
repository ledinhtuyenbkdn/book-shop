package com.ledinhtuyenbkdn.bookshop.bootstrap;

import com.ledinhtuyenbkdn.bookshop.model.Author;
import com.ledinhtuyenbkdn.bookshop.model.Book;
import com.ledinhtuyenbkdn.bookshop.repository.AuthorRepository;
import com.ledinhtuyenbkdn.bookshop.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
public class DataLoader implements CommandLineRunner {
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;


    public DataLoader(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //Add authors
        Author author = new Author();
        author.setAuthorName("Tony Buổi Sáng");

        Author author1 = new Author();
        author1.setAuthorName("Paulo Coelho");

        authorRepository.save(author);
        authorRepository.save(author1);

        //Add books
        Book book = new Book();
        book.setTitle("Tony Buổi Sáng - Trên Đường Băng");
        book.setAuthor(author);
        book.setPublisher("Nhà Xuất Bản Trẻ");
        book.setImageId("pkrimnmpu1pbkq8kyu2b");
        book.setNumPage(308);

        Book book1 = new Book();
        book1.setTitle("Nhà Giả Kim");
        book1.setAuthor(author1);
        book1.setPublisher("Nhà Xuất Bản Văn Học");
        book1.setImageId("npci4p2nq8ooglqzhfls");
        book1.setNumPage(228);

        bookRepository.save(book);
        bookRepository.save(book1);
    }
}
