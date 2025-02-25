package com.thiago.todo_list.service;

import com.thiago.todo_list.model.Task;
import com.thiago.todo_list.model.User;
import com.thiago.todo_list.model.enums.ProfileEnum;
import com.thiago.todo_list.repository.TaskRepository;
import com.thiago.todo_list.security.UserSpringSecurity;
import com.thiago.todo_list.service.exception.AuthorizationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        Mockito.reset(taskRepository, userService);
    }

    @Test
    @DisplayName("Deve criar a task com sucesso caso o usuário esteja autenticado.")
    void createCase1() {
        try (var mockedStatic = mockStatic(UserService.class)) {
            UserSpringSecurity userSpringSecurity = new UserSpringSecurity(1, "thiago", "senha123",
                    Set.of(ProfileEnum.USER));
            mockedStatic.when(UserService::authenticated).thenReturn(userSpringSecurity);

            User user = new User(1, "thiago", "senha123");
            when(userService.findById(userSpringSecurity.getId())).thenReturn(user);

            Task task = new Task(null, "Testar", user);
            Task savedTask = new Task(1, "Testar", user);

            when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

            Task result = taskService.create(task);

            assertNotNull(result);
            assertEquals(1, result.getId());
            assertEquals("Testar", result.getDescription());
            assertEquals(user, result.getUser());

            verify(taskRepository, times(1)).save(any(Task.class));
        }
    }

    @Test
    @DisplayName("Deve retornar uma exceção caso o usuário não esteja autenticado.")
    void createCase2() {
        try (var mockedStatic = mockStatic(UserService.class)) {
            mockedStatic.when(UserService::authenticated).thenReturn(null);

            Task task = new Task(null, "Testar", null);

            AuthorizationException exception = assertThrows(AuthorizationException.class, () -> taskService.create(task));

            assertEquals("Acesso negado!", exception.getMessage());

            verify(taskRepository, never()).save(any(Task.class));
        }
    }
}
