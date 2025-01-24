package com.thiago.todo_list.infra;

import com.thiago.todo_list.exception.TaskDeletionFailedException;
import com.thiago.todo_list.exception.TaskNotFoundException;
import com.thiago.todo_list.exception.UserDeletionFailedException;
import com.thiago.todo_list.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler  {

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<RestErrorMessage> userNotFoundHandler(UserNotFoundException ex){
        RestErrorMessage response = new RestErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    private ResponseEntity<RestErrorMessage> taskNotFoundHandler(TaskNotFoundException ex){
        RestErrorMessage response = new RestErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @ExceptionHandler(UserDeletionFailedException.class)
    private ResponseEntity<RestErrorMessage> userDeletionHandler(UserDeletionFailedException ex){
        RestErrorMessage response = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @ExceptionHandler(TaskDeletionFailedException.class)
    private ResponseEntity<RestErrorMessage> taskDeletionHandler(TaskDeletionFailedException ex){
        RestErrorMessage response = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}
