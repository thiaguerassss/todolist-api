package com.thiago.todo_list.service;

import com.thiago.todo_list.model.User;
import com.thiago.todo_list.repository.UserRepository;
import com.thiago.todo_list.service.exception.DataBindingViolationException;
import com.thiago.todo_list.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado. ID: " + id));
    }

    @Transactional
    public User create(User user){
        user.setId(null);
        user = userRepository.save(user);
        return user;
    }

    @Transactional
    public User update(User user){
        User newUser = findById(user.getId());
        newUser.setPassword(user.getPassword());
        return userRepository.save(newUser);
    }

    public void delete(Integer id){
        findById(id);
        try {
            userRepository.deleteById(id);
        } catch (Exception e){
            throw new DataBindingViolationException("Não é possível deletar pois há entidades relacionadas.");
        }
    }
}
