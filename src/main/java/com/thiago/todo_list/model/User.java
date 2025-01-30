package com.thiago.todo_list.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thiago.todo_list.model.enums.ProfileEnum;
import com.thiago.todo_list.validation.CreateGroup;
import com.thiago.todo_list.validation.UpdateGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false, unique = true)
    @NotBlank(message = "Usuário não pode ser nulo/vazio.", groups = CreateGroup.class)
    @Size(min = 3, max = 100, message = "Usuário deve ter no mínimo 3 caracteres.", groups = CreateGroup.class)
    private String username;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "Senha não pode ser nula/vazia.", groups = {CreateGroup.class, UpdateGroup.class})
    @Size(min = 5, max = 100, message = "Senha deve ter no mínimo 5 caracteres.",
            groups = {CreateGroup.class, UpdateGroup.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    @EqualsAndHashCode.Exclude private List<Task> tasks = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @CollectionTable(name = "user_profile")
    @Column(name = "profile", nullable = false)
    private Set<Integer> profiles = new HashSet<>();

    public Set<ProfileEnum> getProfiles(){
        return profiles.stream().map(ProfileEnum::toEnum).collect(Collectors.toSet());
    }

    public void addProfile(ProfileEnum profileEnum){
        profiles.add(profileEnum.getCode());
    }

    public User(Integer id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
