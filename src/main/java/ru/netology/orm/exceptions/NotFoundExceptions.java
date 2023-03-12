package ru.netology.orm.exceptions;

public class NotFoundExceptions extends RuntimeException{
    public NotFoundExceptions(String msg) {
        super(msg);
    }
}
