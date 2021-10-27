package br.com.concrete.todolist.service;

import br.com.concrete.todolist.errors.exception.UserBadRequestException;
import br.com.concrete.todolist.errors.exception.UserNotFoundException;
import br.com.concrete.todolist.models.User;
import br.com.concrete.todolist.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User people1;

    private User people2;
    private LocalDate birthDate;

    @BeforeEach
    void setup() {

        people1 = new User();

        people1.setId(new BigInteger("1"));
        people1.setFirstName("Rodrigo");
        people1.setLastName("Gonçalves");
        people1.setCpf("859.653.049-51");
        people1.setPassword("12345");
        people1.setBirthDate(birthDate);


        people2 = new User();

        people2.setId(new BigInteger("2"));
        people2.setFirstName("Daniela");
        people2.setLastName("Conceição");
        people2.setCpf("544.380.070-11");
        people2.setPassword("54321");
        people2.setBirthDate(birthDate);

    }

    @Test
    void GivenAUserWhenTryToSaveThenShouldSaveAndReturnUserData() {
        when(userRepository.save(people1)).thenReturn(people1);

        User savedUser = userService.save(people1);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isEqualTo(people1.getId());
        assertThat(savedUser.getFirstName()).isEqualTo(people1.getFirstName());
        assertThat(savedUser.getLastName()).isEqualTo(people1.getLastName());
        assertThat(savedUser.getCpf()).isEqualTo(people1.getCpf());
        assertThat(savedUser.getPassword()).isEqualTo(people1.getPassword());
        assertThat(savedUser.getBirthDate()).isEqualTo(people1.getBirthDate());

    }

    @Test
    void GivenARequestToFindAllUserWhenTryToRetrieveThenShouldReturnAllUserSavedDatabase() {
        when(userRepository.findAll()).thenReturn(List.of(people1, people2));

        List<User> userList = userService.findAll();

        assertThat(userList).isNotEmpty();
        assertThat(userList.size()).isEqualTo(2);
        assertThat(userList.get(1).getId()).isEqualTo(people2.getId());
        assertThat(userList.get(1).getFirstName()).isEqualTo(people2.getFirstName());


    }

    @Test
    void GivenAnIdWhenTryToRetrieveAUserThenShouldReturnCorrectDataOfUser() {
        when(userRepository.findById(people1.getId())).thenReturn(Optional.of(people1));

        User userById = userService.findById(new BigInteger("1"));

        assertThat(userById).isNotNull();
        assertThat(userById.getId()).isEqualTo(people1.getId());
        assertThat(userById.getCpf()).isEqualTo(people1.getCpf());
    }

    @Test
    void GivenAnIdThatNotInTheDatabaseThenShouldThrowUserNotFoundException() {
        when(userRepository.findById(new BigInteger("1000"))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(new BigInteger("1000")));
    }

    @Test
    void GivenANameThatExistThenShouldReturnCorrectDataOfUser() {
        when(userRepository.findByFirstName(people1.getFirstName())).thenReturn(List.of(people1));

        User userServiceByName = userService.findByFirstName("Rodrigo");

        assertThat(userServiceByName).isNotNull();
        assertThat(userServiceByName.getFirstName()).isEqualTo(people1.getFirstName());
        Assertions.assertThatCode(() -> userService.findByFirstName(people1.getFirstName())).doesNotThrowAnyException();
    }

    @Test
    void GivenANameThatNotExistInTheDatabaseThenShouldThrowNotFoundException() {
        when(userRepository.findByFirstName("Ryan")).thenReturn(Collections.emptyList());

        assertThrows(UserNotFoundException.class, () -> userService.findByFirstName("Ryan"));

    }

    @Test
    void GivenAnIdForUpdateThatExistInTheDatabaseThenShouldUpdateDataUser() {
        when(userRepository.findById(people1.getId())).thenReturn(Optional.of(people1));

        User foundId = userService.findById(people1.getId());
        foundId.setPassword("98765");

        userService.updateById(foundId);

        assertThat(people1.getPassword()).isEqualTo("98765");

    }

    @Test
    void GivenAnIdForUpdateThatNotExistInTheDatabaseThenShouldThrowUserNotFoundException() {
        when(userRepository.findById(new BigInteger("10000"))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(new BigInteger("10000")));

    }

    @Test
    void GivenAnIdThatForDeleteThatExistsInTheDatabaseThenShouldDeleteDataUser() {
        when(userRepository.findById(people1.getId())).thenReturn(Optional.of(people1));

        userService.deleteById(people1.getId());

        verify(userRepository).deleteById(people1.getId());


    }

    @Test
    void GivenAnIdForDeleteThatNotExistThenShouldThrowUserNotFoundException() {
        when(userRepository.findById(new BigInteger("3"))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(new BigInteger("3")));
    }

    @Test
    void GivenAnIdForSaveThatExistsThenShouldReturnUserBadRequestException() {
        when(userRepository.findById(people1.getId())).thenReturn(Optional.of(people1));

        assertThrows(UserBadRequestException.class, () -> userService.save(people1));
    }

    @Test
    void GivenAnIdForSaveThatItIsNullThenShouldReturnSaveDatabaseUser() {
        boolean validateId = userService.validIdForSave(null);

        assertThat(validateId).isTrue();
    }

    @Test
    void GivenAnCpfThatExistInTheDatabaseThenShouldReturnCpfExisted() {
        boolean existCpf = userService.existCpfInTheDatabaseSave(people1.getCpf());

        assertThat(existCpf).isTrue();


    }

    @Test
    void GivenAnCpfForSaveThatExistsThenShouldReturnUserBadRequestException() {
        when(userRepository.findByCpf(people1.getCpf())).thenReturn(List.of(people1));

        assertThrows(UserBadRequestException.class, () -> userService.save(people1));
    }


}


