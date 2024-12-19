package com.parkingapp.userservice.domain.user;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserTest {

    private final String nameUpperCase = "NAME";
    private final String nameLowerCase = "name";
    private final String lastnameUpperCase = "LASTNAME";
    private final String lastnameLowerCase = "lastname";
    private final String emailUpperCase = "TEST@MAIL.COM";
    private final String emailLowerCase = "test@mail.com";

    @Test
    void shouldCreateAUserWithNameInLowercase() {
        User newUser = new User(
            UUID.randomUUID(),
            nameUpperCase,
            lastnameLowerCase,
            emailLowerCase,
            "123456789",
            Roles.USER
        );

        assertThat(newUser.getName()).isEqualTo(nameLowerCase);
    }

    @Test
    void shouldSaveNameInLowercaseWhenUseSetter() {
        User newUser = new User(
            UUID.randomUUID(),
            nameLowerCase,
            lastnameLowerCase,
            emailLowerCase,
            "123456789",
            Roles.USER
        );
        newUser.setName(nameUpperCase);
        assertThat(newUser.getName()).isEqualTo(nameLowerCase);
    }

    @Test
    void shouldCreateAUserWithLastnameInLowercase() {
        User newUser = new User(
            UUID.randomUUID(),
            nameLowerCase,
            lastnameUpperCase,
            emailLowerCase,
            "123456789",
            Roles.USER
        );

        assertThat(newUser.getLastname()).isEqualTo(lastnameLowerCase);
    }

    @Test
    void shouldSaveLastnameInLowercaseWhenUseSetter() {
        User newUser = new User(
            UUID.randomUUID(),
            nameLowerCase,
            lastnameLowerCase,
            emailLowerCase,
            "123456789",
            Roles.USER
        );

        newUser.setLastname(lastnameUpperCase);
        assertThat(newUser.getLastname()).isEqualTo(lastnameLowerCase);
    }

    @Test
    void shouldCreateAUserWithEmailInLowercase() {
        User newUser = new User(
            UUID.randomUUID(),
            nameLowerCase,
            lastnameLowerCase,
            emailUpperCase,
            "123456789",
            Roles.USER
        );

        assertThat(newUser.getEmail()).isEqualTo(emailLowerCase);
    }

    @Test
    void shouldSaveEmailInLowercaseWhenUseSetter() {
        User newUser = new User(
            UUID.randomUUID(),
            nameLowerCase,
            lastnameLowerCase,
            emailLowerCase,
            "123456789",
            Roles.USER
        );

        newUser.setEmail(emailUpperCase);
        assertThat(newUser.getEmail()).isEqualTo(emailLowerCase);
    }
}