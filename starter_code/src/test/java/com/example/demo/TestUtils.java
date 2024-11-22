package com.example.demo;

import java.lang.reflect.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestUtils {

    public static void injectObjects(Object target, String fieldName, Object toInject) {
        try {
            boolean wasPrivate = false;

            Field f = target.getClass().getDeclaredField(fieldName);
            if (!f.isAccessible()) {
                f.setAccessible(true);
                wasPrivate = true;
            }

            f.set(target, toInject);

            if (wasPrivate)
                f.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class LoginRequest {

        @JsonProperty
        private String username;

        @JsonProperty
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPassword() {
            return password;
        }
    }
}
