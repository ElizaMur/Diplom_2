package ru.yandex.praktikum;
import org.apache.commons.lang3.RandomStringUtils;

public class UserCredentials {

        public String email;
        public String password;

        public UserCredentials(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public static UserCredentials getRandomCourierCredentials(){
            String email = RandomStringUtils.randomAlphabetic(10) + "yandex.ru";
            String password = RandomStringUtils.randomAlphabetic(10);
            return new UserCredentials(email, password);
        }

        public UserCredentials() {
        }
    }

