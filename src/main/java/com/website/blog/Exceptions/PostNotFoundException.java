package com.website.blog.Exceptions;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(Long id) {
        super("Post id " + id + " could not be found");
    }
}
