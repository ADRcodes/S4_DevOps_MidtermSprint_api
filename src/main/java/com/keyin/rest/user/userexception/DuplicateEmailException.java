package com.keyin.rest.user.userexception;

public class DuplicateEmailException extends RuntimeException {

        public DuplicateEmailException(String email) {
            super("User with email '" + email + "' already exists");
        }
    }
