package com.thiago.todo_list.exception;

public class UserDeletionFailedException extends RuntimeException {

    public UserDeletionFailedException(Exception e) { super("Não foi possível deletar o usuário: " + e.getMessage()); }
}
