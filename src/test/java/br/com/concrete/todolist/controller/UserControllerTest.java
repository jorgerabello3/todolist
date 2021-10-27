package br.com.concrete.todolist.controller;

import br.com.concrete.todolist.models.User;
import br.com.concrete.todolist.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

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
    void GivenARequestToListAllUserThenShouldReturnAllUser() {
        when(userService.findAll()).thenReturn(List.of(people1, people2));

        List<User> listAll = userController.listAll().getBody();

        assertThat(listAll).isNotNull().hasSize(2);
        assertThat(listAll.get(1).getId()).isEqualTo(people2.getId());
        assertThat(listAll.get(1).getFirstName()).isEqualTo(people2.getFirstName());
        assertThat(listAll.get(0).getId()).isEqualTo(people1.getId());
        assertThat(listAll.get(0).getLastName()).isEqualTo(people1.getLastName());


    }

    @Test
    void GivenAnIdThatExistInTheDatabaseThenShouldReturnCorrectDataUser() {
        when(userService.findById(people1.getId())).thenReturn(people1);

        User user = userController.findById(new BigInteger("1")).getBody();

        assertThat(user).isNotNull().isEqualTo(people1);
        assertThat(user.getId()).isEqualTo(people1.getId());


    }

    @Test
    void GivenARequestForFindUserIsIfListEmptyThenShouldReturnLisEmpty() {
        when(userService.findAll()).thenReturn(Collections.emptyList());

        List<User> userList = userController.listAll().getBody();

        assertThat(userList).isEmpty();
    }

    @Test
    void GivenAUserForSaveThenShouldSaveAndReturnDataUser() {
        when(userService.save(people1)).thenReturn(people1);

        User userSaved = userController.save(people1).getBody();

        assertThat(userSaved).isNotNull();
        assertThat(userSaved.getId()).isEqualTo(people1.getId());
        assertThat(userSaved.getFirstName()).isEqualTo(people1.getFirstName());
        assertThat(userSaved.getLastName()).isEqualTo(people1.getLastName());
        assertThat(userSaved.getCpf()).isEqualTo(people1.getCpf());
        assertThat(userSaved.getPassword()).isEqualTo(people1.getPassword());
        assertThat(userSaved.getBirthDate()).isEqualTo(people1.getBirthDate());
    }

    @Test
    void GivenAnIdForUpdateThatExistDatabaseThenShouldUpdateDataUser() {
        when(userService.findById(people1.getId())).thenReturn(people1);

        User foundUser = userService.findById(people1.getId());
        foundUser.setPassword("11111");

        userController.update(foundUser);

        assertThat(foundUser.getPassword()).isEqualTo("11111");

    }

    @Test
    void GivenAnIdForDeleteThatExistThenShouldDeleteUserDatabase() {
        when(userService.save(people1)).thenReturn(people1);

        User savedUser = userService.save(people1);

        userController.delete(savedUser.getId());

        User userDeleted = userService.findById(savedUser.getId());

        verify(userService).deleteById(people1.getId());
        assertThat(userDeleted).isNull();


    }

    @Test
    void GivenARequestFindNameThatExistInTheDatabaseThenShouldReturnCorrectDataUser() {
        when(userService.findByFirstName(people1.getFirstName())).thenReturn(people1);

        User userByName = userController.findByFirstName(people1.getFirstName()).getBody();

        assertThat(userByName).isNotNull();
        assertThat(userByName.getId()).isEqualTo(people1.getId());
        assertThat(userByName.getFirstName()).isEqualTo(people1.getFirstName());
    }


}