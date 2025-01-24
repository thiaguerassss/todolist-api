package com.thiago.todo_list.model;

import com.thiago.todo_list.validation.CreateGroup;
import com.thiago.todo_list.validation.UpdateGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 255, nullable = false)
    @NotBlank(message = "Descrição não pode ser vazia.", groups = {CreateGroup.class, UpdateGroup.class})
    @Size(min = 1, max = 255)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @NotBlank(message = "Usuário não pode ser nulo.", groups = CreateGroup.class)
    private User user;
}
