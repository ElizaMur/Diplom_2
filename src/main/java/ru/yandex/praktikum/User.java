package ru.yandex.praktikum;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Locale;

public class User {
        private String email;
        private String password;
        private String name;

        public User(String email, String password, String name) {
            this.email = email;
            this.password = password;
            this.name = name;
        }
        public User() {

        }


        public static User getRandomUser(){
            String email = (RandomStringUtils.randomAlphabetic(10) + "@yandex.ru").toLowerCase();
            String password = RandomStringUtils.randomAlphabetic(10);
            String name = RandomStringUtils.randomAlphabetic(10);
            return new User(email, password, name);
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


    }

