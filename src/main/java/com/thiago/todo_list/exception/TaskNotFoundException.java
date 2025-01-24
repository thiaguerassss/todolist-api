package com.thiago.todo_list.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException() { super("Tarefa não encontrado."); }
}
