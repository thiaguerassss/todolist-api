package com.thiago.todo_list.service;

import com.thiago.todo_list.model.User;
import com.thiago.todo_list.model.enums.ProfileEnum;
import com.thiago.todo_list.repository.UserRepository;
import com.thiago.todo_list.security.UserSpringSecurity;
import com.thiago.todo_list.service.exception.AuthorizationException;
import com.thiago.todo_list.service.exception.DataBindingViolationException;
import com.thiago.todo_list.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    public User findById(Integer id) {
        UserSpringSecurity userSpringSecurity = authenticated();
        if(!Objects.nonNull(userSpringSecurity) || userSpringSecurity.hasRole(ProfileEnum.ADMIN) &&
                !id.equals(userSpringSecurity.getId())) {
            throw new AuthorizationException("Acesso negado!");
        }
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado. ID: " + id));
    }

    @Transactional
    public User create(User user){
        user.setId(null);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.addProfile(ProfileEnum.USER);
        user = userRepository.save(user);
        return user;
    }

    @Transactional
    public User update(User user){
        User newUser = findById(user.getId());
        newUser.setPassword(user.getPassword());
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
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

    public static UserSpringSecurity authenticated(){
        try {
            return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e){
            return null;
        }
    }
}
