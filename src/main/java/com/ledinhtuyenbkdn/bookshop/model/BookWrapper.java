package com.ledinhtuyenbkdn.bookshop.model;

import org.springframework.web.multipart.MultipartFile;

public class BookWrapper extends Book {
    private MultipartFile multipartFile;

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
