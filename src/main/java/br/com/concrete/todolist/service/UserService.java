package br.com.concrete.todolist.service;

import br.com.concrete.todolist.errors.exception.UserNotFoundException;
import br.com.concrete.todolist.models.User;
import br.com.concrete.todolist.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {


    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(BigInteger id) {
        return existIdInTheDatabase(id);

    }

    private User existIdInTheDatabase(BigInteger id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElseThrow(() ->new UserNotFoundException("User not found for id " + id));
    }

    public User findByName(String name) {
     return  existNameInTheDatabase(name);
    }

    private User existNameInTheDatabase(String name) {
        List<User> userRepositoryByName = userRepository.findByName(name);
        return userRepositoryByName.stream().findFirst().orElseThrow(()->new UserNotFoundException("Name not found in the database "+ name));
    }

    public void updateById(User user) {
        existIdInTheDatabase(user.getId());
        userRepository.save(user);
    }

    public void deleteById(BigInteger id) {
        existIdInTheDatabase(id);
        userRepository.deleteById(id);
    }
}
