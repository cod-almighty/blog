package com.website.blog.Exceptions;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(Long id) {
        super("Could not find post " + id);
    }
}
