package com.thiago.todo_list.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() { super("Usuário não encontrado."); }
}
