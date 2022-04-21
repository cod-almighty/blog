package com.website.blog.Exceptions;

public class CannotDeletePostException extends RuntimeException {

    public CannotDeletePostException(long id, Exception e) {
        super("Can't delete post id " + id + " due to exception:\n" + e);
    }
}
