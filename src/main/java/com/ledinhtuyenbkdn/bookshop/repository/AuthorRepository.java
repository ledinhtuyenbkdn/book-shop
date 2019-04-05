package com.ledinhtuyenbkdn.bookshop.repository;

import com.ledinhtuyenbkdn.bookshop.model.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
}
