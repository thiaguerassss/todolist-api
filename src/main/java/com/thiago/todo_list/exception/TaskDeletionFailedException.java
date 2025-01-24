package com.thiago.todo_list.exception;

public class TaskDeletionFailedException extends RuntimeException {

    public TaskDeletionFailedException(Exception e) { super("Não foi possível deletar a tarefa: " + e.getMessage()); }
}
