package br.com.concrete.todolist.service;

import br.com.concrete.todolist.errors.exception.UserBadRequestException;
import br.com.concrete.todolist.errors.exception.UserNotFoundException;
import br.com.concrete.todolist.models.User;
import br.com.concrete.todolist.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class UserService {


    private final UserRepository userRepository;

    public User save(User user) {
        if (existCpfInTheDatabaseSave(user.getCpf())) {
            if (validIdForSave(user.getId())) {
                PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                return userRepository.save(user);
            }
            throw new UserBadRequestException("The Todo id " + user.getId() + " Id informed exists save in the database, increment id automatic");
        }
        throw new UserBadRequestException("Cpf " + user.getCpf() + " exist save in the database");
    }

    public boolean validIdForSave(BigInteger id) {
        if (id == null) {
            return true;
        }
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.isEmpty();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(BigInteger id) {
        return existIdInTheDatabase(id);

    }

    private User existIdInTheDatabase(BigInteger id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElseThrow(() -> new UserNotFoundException("User not found for id " + id));
    }

    public User findByFirstName(String name) {
        return existNameInTheDatabase(name);
    }

    private User existNameInTheDatabase(String name) {
        List<User> userRepositoryByName = userRepository.findByFirstName(name);
        return userRepositoryByName.stream().findFirst().orElseThrow(() -> new UserNotFoundException("Name not found in the database " + name));
    }

    public void updateById(User user) {
        existIdInTheDatabase(user.getId());
        userRepository.save(user);
    }

    public void deleteById(BigInteger id) {
        existIdInTheDatabase(id);
        userRepository.deleteById(id);
    }

    public boolean existCpfInTheDatabaseSave(String cpf) {
        List<User> userRepositoryByCpf = userRepository.findByCpf(cpf);
        return userRepositoryByCpf.isEmpty();
    }

}
